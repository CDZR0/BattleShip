//Csaba
package battleship.gui.Game;

import battleship.Logic.CellStatus;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class CellGUI extends JPanel {

    private final Color backgroundColor = Color.WHITE;
    private final Color selectedColor = Color.LIGHT_GRAY;
    private final Color shipColor = Color.GRAY;
    private final int i;
    private final int j;
    private CellStatus cellStatus;

    public CellGUI(int i, int j) {
        this.i = i;
        this.j = j;
        cellStatus = CellStatus.Empty;
        setBackground(backgroundColor);
        setName("Button: " + i + ":" + j);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setCell(CellStatus cellStatus) {
        switch (cellStatus) {
            case Empty:
                this.cellStatus = cellStatus;
                setBackground(backgroundColor);
                break;
            case EmptyHit:
                this.cellStatus = cellStatus;
                break;
            case NearShip:
                this.cellStatus = cellStatus;
                break;
            case Ship:
                placeShip();
                break;
            case ShipHit:
                this.cellStatus = cellStatus;
                break;
            case ShipSunk:
                this.cellStatus = cellStatus;
                break;
            default:
                throw new AssertionError();
        }
    }

    private void placeShip() {
        if (cellStatus == CellStatus.Empty) {
            cellStatus = CellStatus.Ship;
            setBackground(shipColor);
        }
    }

    public void select() {
        if (cellStatus == CellStatus.Empty) {
            setBackground(selectedColor);
        }
    }

    public void unSelect() {
        if (cellStatus == CellStatus.Empty) {
            setBackground(backgroundColor);
        }
    }

    public CellStatus getStatus(){
        return cellStatus;
    }
    
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isIJ(int i, int j) {
        return this.i == i && this.j == j;
    }

}
