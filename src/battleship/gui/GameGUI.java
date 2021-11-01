//Csaba
package battleship.gui;

import battleship.gui.Game.BoardGUI;
import battleship.gui.Game.ShipSelecterGUI;
import battleship.Logic.Board;
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
        title.setText("Game");
        title.setBackground(Color.red);
        title.setBounds(10, 10, 100, 35);
        this.add(title);

        JButton backButton = new JButton();
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

        selecter = new ShipSelecterGUI();
        selecter.setLocation(50, 100);
        this.add(selecter);

        ownBoard = new Board();
        enemyBoard = new Board();
        BoardGUI boardGUI = new BoardGUI(ownBoard);
        boardGUI.setLocation(50, 250);
        this.add(boardGUI);

        BoardGUI enemyBoardGUI = new BoardGUI(enemyBoard);
        enemyBoardGUI.setLocation(450, 250);
        enemyBoardGUI.setEnabled(false);
        this.add(enemyBoardGUI);

        JButton shipHorizontal = new JButton();
        shipHorizontal.setText("Horizontal");
        shipHorizontal.setBounds((size().width - 100) / 2, 65, 100, 35);
        shipHorizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (shipHorizontal.getText() == "Horizontal") {
                    shipHorizontal.setText("Vertical");
                    boardGUI.shipPlaceHorizontal = false;
                } else {
                    shipHorizontal.setText("Horizontal");
                    boardGUI.shipPlaceHorizontal = true;
                }
            }
        });
        this.add(shipHorizontal);
    }

    public GameGUI(String ip) {

    }
}
