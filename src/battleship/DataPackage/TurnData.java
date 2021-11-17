//Csaba
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class TurnData extends Data {

    public TurnData(int recipientID) {
        super(-1);
        System.out.println("FA" + recipientID);
        super.recipientID = recipientID;
        System.out.println("ClientID: " + super.clientID + " recipientID: " + super.recipientID);
    }

}
