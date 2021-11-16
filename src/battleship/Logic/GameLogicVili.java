// én is elkezdem megírni ezt az osztályt, más megoldással, hátha előrébb jutunk
package battleship.Logic;

import battleship.DataPackage.DataConverter;
import java.util.List;
import java.util.Vector;


public class GameLogicVili {
    public List<String> messageQueue = new Vector<>();
    
    public void processMessage(String message) {
        List<String> decodedMsg = DataConverter.decode(message);
        
        
        String messageToBeSent;

        int senderID = Integer.parseInt(decodedMsg.get(0));
        String type = decodedMsg.get(1);
        String data = decodedMsg.get(2);
        int recipientID = Integer.parseInt(decodedMsg.get(3));
        
        switch(type)
        {
            case "Chat":
                messageToBeSent = chat(senderID, type, data, recipientID);
                break;
            default:
                messageToBeSent = "UNRECOGNIZED DATA TYPE";
                break;
        }
        
        addMessageToQueue(messageToBeSent);
    }
    
    private void placeShips()
    {
        
    }
    
    private String chat(int senderID, String type, String data, int recipientID)
    {
        return DataConverter.encode(senderID, type, data, recipientID);
    }
    
    private void addMessageToQueue(String message)
    {
        messageQueue.add(message);
    }
}
