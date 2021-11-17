//Csaba
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class ShotData extends Data {

    int i;
    int j;

    public ShotData(int clientID, int i, int j) {
        super(clientID);
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

}
