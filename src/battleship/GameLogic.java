package battleship;

public class GameLogic
{
    private int senderID;
    
    public String processMessage(int senderID, String message)
    {
        String decoded = decodeMessage(senderID, message);
        String output = CalculateLogic(decoded);
        String encoded = encodeMessage(output);
        
        return encoded;
    }
    
    private String decodeMessage(int senderID, String message)
    {
        //Szervertől kapott üzenetet alakítja vissza "CalculateLogic" által értelmezhető adattá, majd megoldom, ne aggódj miatta
        String decodedMessage = senderID + message;
        
        this.senderID = senderID;
        
        
        return decodedMessage;
    }
    
    private String encodeMessage(String message)
    {
        //"CalculateLogic"-tól kapott üzenetet alakítja vissza a szerver által visszaküldött kóddá, majd megoldom, ne aggódj miatta
        String encodedMessage = message;
        
        
        return encodedMessage;
    }
    
    private String CalculateLogic(String message)
    {
        //Csaba ez (várhatóan) a tiéd
        return message;
    }
}
