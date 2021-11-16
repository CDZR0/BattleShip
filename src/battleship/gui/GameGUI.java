//Csaba
package battleship.gui;

import battleship.DataPackage.PlaceShipsData;
import battleship.Events.ShipPlaceEvent;
import battleship.Events.ShipSelectorEvent;
import battleship.gui.Game.ShipSelecterGUI;
import battleship.Logic.Board;
import battleship.Networking.Client;
import battleship.Networking.Server;
import battleship.Resources.Resources;
import battleship.Utils.Settings;
import battleship.gui.Game.EnemyBoardGUI;
import battleship.gui.Game.PlayerBoardGUI;
import java.awt.event.ActionListener;
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

    public GameGUI() {
        this(Settings.getIP(), Settings.getPort());
        server = new Server(Settings.getPort());
        serverThread = new Thread(server);
        serverThread.start();
        System.out.println("szerver itt");
    }

    public GameGUI(String ip, int port) {
        setLayout(null);
        this.setSize(800, 600);
        setBackground(Resources.BackgroundColor);

        client = new Client(ip, port);
        clientThread = new Thread(client);
        clientThread.start();

        JLabel title = new JLabel();
        JButton exitButton = new JButton();
        ownBoard = new Board();
        enemyBoard = new Board();
        PlayerBoardGUI ownBoardGUI = new PlayerBoardGUI(ownBoard);
        EnemyBoardGUI enemyBoardGUI = new EnemyBoardGUI(enemyBoard);
        selecter = new ShipSelecterGUI();

        title.setText("Game infos" + ip + ":" + port);
        title.setSize(300, 35);
        title.setLocation((this.size().width - title.size().width) / 2, 10);
        this.add(title);

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
        this.add(enemyBoardGUI);

        selecter.setLocation(50, 100);
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
                System.out.println(ownBoardGUI.getBoard().toString());

                enemyBoardGUI.setEnabled(true);
                //#### TESZT ####
                client.sendMessage("Done");
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
        client.sendMessage(new PlaceShipsData(0, board));
    }

}
