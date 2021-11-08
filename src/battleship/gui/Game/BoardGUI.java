//Csaba
package battleship.gui.Game;

import battleship.Logic.Board;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class BoardGUI extends JPanel {

    protected Board board;
    protected CellGUI[][] cells;

    public BoardGUI(Board board) {
        setLayout(null);
        this.board = board;
        int widtheight = 300;
        this.setSize(widtheight, widtheight);
        this.setBackground(Color.GRAY);
    }
    
    public Board getBoard(){
        return this.board;
    }
}
