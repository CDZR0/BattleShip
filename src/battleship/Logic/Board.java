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

    public int getN() {
        return cellstatus.length;
    }
}