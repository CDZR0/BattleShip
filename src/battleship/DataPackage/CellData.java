//Csaba
package battleship.DataPackage;

import battleship.Logic.CellStatus;

/**
 *
 * @author Csaba
 */
public class CellData extends Data {

    int i;
    int j;
    CellStatus status;

    public CellData(int clientID, int i, int j) {
        super(clientID);
        this.i = i;
        this.j = j;
    }

    public CellData(int clientID, int i, int j, CellStatus status) {
        super(clientID);
        this.i = i;
        this.j = j;
        this.status = status;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public CellStatus getStatus() {
        return status;
    }

}
