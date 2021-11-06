//Csaba
package battleship.gui;

import battleship.Events.ShipPlaceEvent;
import battleship.Events.ShipSelectorEvent;
import battleship.gui.Game.ShipSelecterGUI;
import battleship.Logic.Board;
import battleship.gui.Game.EnemyBoardGUI;
import battleship.gui.Game.PlayerBoardGUI;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class GameGUI extends JPanel {

    private Board ownBoard;
    private Board enemyBoard;
    private ShipSelecterGUI selecter;

    public GameGUI() {
        setLayout(null);
        this.setSize(800, 600);
        setBackground(Color.yellow);

        JLabel title = new JLabel();
        JButton backButton = new JButton();
        ownBoard = Board.TesztBoard();
        enemyBoard = new Board();
        PlayerBoardGUI ownBoardGUI = new PlayerBoardGUI(ownBoard);
        EnemyBoardGUI enemyBoardGUI = new EnemyBoardGUI(enemyBoard);
        selecter = new ShipSelecterGUI();
        System.out.println(ownBoard.toString());

        title.setText("Game");
        title.setBackground(Color.red);
        title.setBounds(10, 10, 100, 35);
        this.add(title);

        backButton.setText("Back");
        backButton.setSize(100, 35);
        backButton.setLocation((size().width - 100) / 2, 222);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("back");
                setVisible(false);
            }
        });
        this.add(backButton);

        ownBoardGUI.setLocation(50, 250);
        this.add(ownBoardGUI);

        enemyBoardGUI.setLocation(450, 250);
        enemyBoardGUI.setEnabled(false);
        this.add(enemyBoardGUI);

        selecter.setLocation(50, 100);
        selecter.addShipSelectorListener(new ShipSelectorEvent() {

            @Override
            public void onRanOutOfShips(boolean ranOutOf) {
                ownBoardGUI.canPlace = !ranOutOf;
            }

            @Override
            public void onSelectShip(int shipSize) {
                System.out.println("szam: " + shipSize);
                ownBoardGUI.selectedShipSize = shipSize;
            }

            @Override
            public void onSelectDirection(boolean shipPlaceHorizontal) {
                System.out.println("Horizontal: " + shipPlaceHorizontal);
                ownBoardGUI.shipPlaceHorizontal = shipPlaceHorizontal;
            }

            @Override
            public void onClearBoard() {
                ownBoardGUI.ClearBoard();
                ownBoardGUI.canPlace = true;
            }

            @Override
            public void onPlaceRandomShips() {
                ownBoardGUI.canPlace = false;
                ownBoardGUI.RandomPlace();
            }

            @Override
            public void onDone() {
                //ownBoardGUI.setEnabled(false);
                System.out.println(ownBoard.toString());
            }
        });
        this.add(selecter);

        ownBoardGUI.addPlaceOrPickUpListener(new ShipPlaceEvent() {
            @Override
            public void onPlace(int shipSize, boolean shipPlaceHorizontal) {
                System.out.println("Placed ship size: " + shipSize + " horizontal: " + shipPlaceHorizontal);
                selecter.LerakTablara(shipSize);
            }

            @Override
            public void onPickUp(int shipSize, boolean shipPlacehorizontal) {
                System.out.println("Picked up ship size: " + shipSize + " horizontal: " + shipPlacehorizontal);
                selecter.FelveszTablarol(shipSize);
                ownBoardGUI.canPlace = true;
            }
        });
        repaint();
    }

    public GameGUI(String ip) {

    }
    
    private void sendReady(Board board){
        
    }
    
}
