//Csaba
package battleship.Logic;

/**
 *
 * @author Csaba
 */
public class Board {

    CellStatus[][] cellstatus = new CellStatus[10][10];

    public Board() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cellstatus[i][j] = CellStatus.Empty;
            }
        }
    }

    public void setCell(int i, int j, CellStatus status) {
        cellstatus[i][j] = status;
    }

    public int getNLength() {
        return cellstatus.length;
    }

    public CellStatus[][] getCellstatus() {
        return cellstatus;
    }

    @Override
    public String toString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                a += cellstatus[i][j] + " ";
            }
            a += "\n";
        }
        return "Board:\n" + a;
    }

}
