package battleship.DataPackage;

import java.util.Arrays;
import java.util.List;


public class DataConverter {
    public static void decode(String message)
    {
        List<String> collection = Arrays.asList(message.split("\\$"));
        
        switch(collection.get(1))
        {
            case "CHAT":
                //Invoke chat event
                System.out.println("CHAT VAN GECI");
                break;
            case "PLACE":
                //Invoke place event
                break;
            case "MARK":
                //Invoke mark event
                break;
            case "TURN_END":
                //Invoke turn end event
                break;
            case "END_GAME":
                //Invoke end game event
                break;
            default:
                throw new RuntimeException("ISMERETLEN UTASÍTÁS");
        }
        
        System.out.println(collection.getClass().getSimpleName());
    }
}
