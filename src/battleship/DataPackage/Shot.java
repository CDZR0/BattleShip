//Csaba
package battleship.DataPackage;

import java.awt.Point;

/**
 *
 * @author Csaba
 */
public class Shot extends Data {

    int i;
    int j;

    public Shot(int clientID, int i, int j) {
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
