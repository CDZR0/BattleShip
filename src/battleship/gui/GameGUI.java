//Csaba
package battleship.gui;

import battleship.DataPackage.ChatData;
import battleship.DataPackage.GameEndedStatus;
import battleship.DataPackage.PlaceShipsData;
import battleship.DataPackage.ShotData;
import battleship.Events.ChatGUIEvent;
import battleship.Events.ClientEvent;
import battleship.Events.ShipPlaceEvent;
import battleship.Events.ShipSelectorEvent;
import battleship.Events.ShotEvent;
import battleship.gui.Game.ShipSelecterGUI;
import battleship.Logic.Board;
import battleship.Logic.CellStatus;
import battleship.Networking.Client;
import battleship.Networking.Server;
import battleship.Resources.Resources;
import battleship.Utils.Settings;
import battleship.gui.Game.EnemyBoardGUI;
import battleship.gui.Game.InfoPanelGUI;
import battleship.gui.Game.PlayerBoardGUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class GameGUI extends JPanel {

    private Board ownBoard;
    private Board enemyBoard;
    private ShipSelecterGUI selecter;
    private Client client;
    private Thread clientThread, serverThread;
    private Server server;
    private InfoPanelGUI infoPanel;
    private JLabel title;
    private ChatGUI chatGUI;
    private JLabel waitingTitle;

    public GameGUI() {
        this(Settings.getIP(), Settings.getPort());
        server = new Server(Settings.getPort());
        serverThread = new Thread(server);
        serverThread.start();
        System.out.println("szerver itt");
        title.setText("Game IP: " + Server.getLocalIP() + ":" + Settings.getPort() + " Click to copy IP");
        title.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                StringSelection selection = new StringSelection(Server.getLocalIP() + ":" + Settings.getPort());
                Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                c.setContents(selection, selection);
            }
        });
    }

    public GameGUI(String ip, int port) {
        setLayout(null);
        this.setSize(800, 600 - MenuGUI.WindowInsets);
        setBackground(Resources.BackgroundColor);

        waitingTitle = new JLabel();
        waitingTitle.setText("Waiting for opponent...");
        waitingTitle.setFont(new Font("Dialog", Font.BOLD, 30));
        waitingTitle.setHorizontalAlignment(JLabel.CENTER);
        waitingTitle.setVerticalTextPosition(JLabel.CENTER);
        waitingTitle.setHorizontalTextPosition(JLabel.CENTER);
        waitingTitle.setSize(800, 100);
        waitingTitle.setLocation(0, (size().height - waitingTitle.size().height) / 2);
        this.add(waitingTitle);

        ownBoard = new Board();
        enemyBoard = new Board();
        PlayerBoardGUI ownBoardGUI = new PlayerBoardGUI(ownBoard);
        EnemyBoardGUI enemyBoardGUI = new EnemyBoardGUI(enemyBoard);
        selecter = new ShipSelecterGUI();

        chatGUI = new ChatGUI();
        chatGUI.setSize((size().width - 100), 185);
        chatGUI.setLocation(50, 50);
        chatGUI.setVisible(false);
        chatGUI.addSendMessageListener(new ChatGUIEvent() {
            @Override
            public void onSendMessage(String message) {
                client.sendMessage(new ChatData(client.ID, message));
            }
        });
        this.add(chatGUI);

        infoPanel = new InfoPanelGUI();
        infoPanel.setSize(60, 60);
        infoPanel.setLocation((size().width - infoPanel.size().width) / 2, 250 + ((300 - infoPanel.size().height) / 2));
        infoPanel.setVisible(false);
        this.add(infoPanel);

        title = new JLabel();
        title.setText("Game IP: " + ip + ":" + port);
        title.setSize(300, 35);
        title.setLocation((this.size().width - title.size().width) / 2, 10);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        this.add(title);

        client = new Client(ip, port);
        client.addClientEventListener(new ClientEvent() {
            @Override
            public void onMessageReceived(int senderID, String message) {
                if (senderID == -1) {
                    chatGUI.addMessage("System", message);
                } else if (senderID == client.ID) {
                    chatGUI.addMessage("Me", message);
                } else {
                    chatGUI.addMessage("Opponent", message);
                }
            }

            @Override
            public void onYourTurn() {
                //System.out.println("Its me turn.");
                enemyBoardGUI.setTurnEnabled(true);
                infoPanel.setTurnText(true);
            }

            @Override
            public void onGameEnded(GameEndedStatus status) {
                enemyBoardGUI.setTurnEnabled(false);
                System.out.println("Ki kéne írni hogy nyert or vesztett");
                String endMessage = "";
                switch (status) {
                    case Win:
                        endMessage = "You win!";
                        break;
                    case Defeat:
                        endMessage = "Defeat!";
                        break;
                    case Unknown:
                        System.out.println("Unknown game ended status");
                        endMessage = "Unknown game ended status";
                        break;
                    default:
                        break;
                }
                infoPanel.setGameEnded();
                chatGUI.addMessage("System", endMessage);

            }

            @Override
            public void onEnemyHitMe(int i, int j) {
                ownBoardGUI.Hit(i, j);
            }

            @Override
            public void onMyHit(int i, int j, CellStatus status) {
                enemyBoardGUI.Hit(i, j, status);
            }

            @Override
            public void onJoinedEnemy() {
                waitingTitle.setVisible(false);
                enemyBoardGUI.setVisible(true);
                ownBoardGUI.setVisible(true);
                selecter.setVisible(true);
            }
        });
        clientThread = new Thread(client);
        clientThread.start();

        JButton exitButton = new JButton();
        exitButton.setText("Exit game");
        exitButton.setSize(100, 35);
        exitButton.setLocation(10, 10);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Warning", 0) == JOptionPane.YES_OPTION) {
                    client.close();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (server != null) {
                        try {
                            server.close();
                        } catch (Exception e) {
                            System.out.println("Sikertelen server close():\n" + e.getMessage());
                        }
                    }
                    setVisible(false);
                }
            }
        });
        this.add(exitButton);

        ownBoardGUI.setVisible(false);
        ownBoardGUI.setLocation(50, 250);
        this.add(ownBoardGUI);

        enemyBoardGUI.setVisible(false);
        enemyBoardGUI.setLocation(450, 250);
        enemyBoardGUI.setEnabled(false);
        enemyBoardGUI.addShotListener(new ShotEvent() {
            @Override
            public void onShot(int i, int j) {
                infoPanel.setTurnText(false);
                client.sendMessage(new ShotData(client.ID, i, j));
            }
        });
        this.add(enemyBoardGUI);

        selecter.setVisible(false);
        selecter.setLocation(50, 100);
        selecter.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                infoPanel.setVisible(true);
            }
        });
        selecter.addShipSelectorListener(new ShipSelectorEvent() {

            @Override
            public void onRanOutOfShips() {
                ownBoardGUI.canPlace = false;
                selecter.setCanDoneButton(true);
            }

            @Override
            public void onSelectShip(int shipSize) {
                ownBoardGUI.selectedShipSize = shipSize;
            }

            @Override
            public void onSelectDirection(boolean shipPlaceHorizontal) {
                ownBoardGUI.shipPlaceHorizontal = shipPlaceHorizontal;
            }

            @Override
            public void onClearBoard() {
                ownBoardGUI.ClearBoard();
                ownBoardGUI.canPlace = true;
                selecter.setCanDoneButton(false);
            }

            @Override
            public void onPlaceRandomShips() {
                ownBoardGUI.canPlace = false;
                ownBoardGUI.RandomPlace();
                selecter.setCanDoneButton(true);
            }

            @Override
            public void onDone() {
                ownBoardGUI.setEnabled(false);
                chatGUI.setVisible(true);
                //System.out.println(ownBoardGUI.getBoard().toString());
                client.sendMessage(new PlaceShipsData(client.ID, ownBoardGUI.getBoard()));

                //TESZT
                //enemyBoardGUI.setEnabled(true);
            }
        });
        this.add(selecter);

        ownBoardGUI.addPlaceOrPickUpListener(new ShipPlaceEvent() {
            @Override
            public void onPlace(int shipSize, boolean shipPlaceHorizontal) {
                selecter.LerakTablara(shipSize);
            }

            @Override
            public void onPickUp(int shipSize, boolean shipPlacehorizontal) {
                selecter.FelveszTablarol(shipSize);
                ownBoardGUI.canPlace = true;
                selecter.setCanDoneButton(false);
            }
        });
        repaint();
    }
}
