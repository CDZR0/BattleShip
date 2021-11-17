//Csaba
package battleship.DataPackage;

/**
 *
 * @author Csaba
 */
public class GameEndedData extends Data{
    
    GameEndedStatus status;
    
    public GameEndedData(int clientID, GameEndedStatus status) {
        super(clientID);
        this.status = status;
    }
    
}
