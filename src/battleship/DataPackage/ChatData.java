//Csaba
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class ChatData extends Data {

    String message;

    public ChatData(int clientID, String message) {
        super(clientID);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
