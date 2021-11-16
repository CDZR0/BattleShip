package battleship.DataPackage;

import java.util.List;
import java.util.Arrays;

public class DataConverter {
    public static List<String> decode(String message)
    {
        return Arrays.asList(message.split("\\$"));
    }
    
    public static String encode(List<String> message)
    {
        String encoded = "";
        
        int size = message.size();
        for (int i = 0; i < size; ++i) 
        {
            encoded += message.get(i);
            if (i != size-1)
            {
                encoded += "$";
            }
        }
        return encoded;
    }
    
    public static String encode(int senderID, String type, String data, int recipientID)
    {
        return senderID + "$" + type + "$" + data + "$" + recipientID;
    }
}
