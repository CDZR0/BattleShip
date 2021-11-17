//Csaba
package battleship.gui.Game;

import battleship.Logic.Board;
import battleship.Logic.CellStatus;
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

    public Board getBoard() {
        return this.board;
    }

    public void Hit(int i, int j) {
        switch (cells[i][j].getStatus()) {
            case Empty:
                cells[i][j].setCell(CellStatus.EmptyHit);
                break;
            case EmptyHit:
                break;
            case NearShip:
                break;
            case Ship:
                cells[i][j].setCell(CellStatus.ShipHit);
                break;
            case ShipHit:
                break;
            case ShipSunk:
                break;
            default:
                break;
        }
    }

    public void Hit(int i, int j, CellStatus status) {
        switch (status) {
            case Empty:
                cells[i][j].setCell(CellStatus.EmptyHit);
                break;
            case EmptyHit:
                break;
            case NearShip:
                break;
            case Ship:
                cells[i][j].setCell(CellStatus.ShipHit);
                break;
            case ShipHit:
                break;
            case ShipSunk:
                break;
            default:
                break;
        }
    }
}
