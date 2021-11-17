//Csaba
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class GameEndedData extends Data {

    GameEndedStatus status;

    public GameEndedData(GameEndedStatus status, int recipientID) {
        super(-1);
        this.status = status;
        super.recipientID = recipientID;
    }

    public GameEndedStatus getStatus() {
        return status;
    }

}
