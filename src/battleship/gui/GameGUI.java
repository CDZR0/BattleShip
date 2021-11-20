//Csaba
package battleship.gui;

import battleship.DataPackage.GameEndedStatus;
import battleship.DataPackage.PlaceShipsData;
import battleship.DataPackage.ShotData;
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
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

    public GameGUI() {
        this(Settings.getIP(), Settings.getPort());
        server = new Server(Settings.getPort());
        serverThread = new Thread(server);
        serverThread.start();
        System.out.println("szerver itt");
        title.setText("Game IP: " + Server.getLocalIP() + ":" + Settings.getPort());
    }

    public GameGUI(String ip, int port) {
        setLayout(null);
        this.setSize(800, 600);
        setBackground(Resources.BackgroundColor);

        ownBoard = new Board();
        enemyBoard = new Board();
        PlayerBoardGUI ownBoardGUI = new PlayerBoardGUI(ownBoard);
        EnemyBoardGUI enemyBoardGUI = new EnemyBoardGUI(enemyBoard);
        selecter = new ShipSelecterGUI();

        infoPanel = new InfoPanelGUI();
        infoPanel.setSize(size().width, 190);
        infoPanel.setLocation(0, 50);
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
            public void onMessageReceived(String message) {
                System.out.println("Chat nincs kész" + message);
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
                switch (status) {
                    case Win:
                        System.out.println("NYERTÉL");
                        infoPanel.setGameEndedText(status);
                        break;
                    case Defeat:
                        System.out.println("VESZTETTÉL");
                        infoPanel.setGameEndedText(status);
                        break;
                    case Unknown:
                        System.out.println("Unknown game ended status");
                        infoPanel.setGameEndedText(status);
                        break;
                    default:
                        break;
                }
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

        ownBoardGUI.setLocation(50, 250);
        this.add(ownBoardGUI);

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

    private void sendReady(Board board) {
        client.sendMessage(new PlaceShipsData(client.ID, board));
    }

}
