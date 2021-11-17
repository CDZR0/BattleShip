package battleship.DataPackage;

import battleship.Logic.Board;
import java.util.List;

public class DataConverter {

//    public static List<String> decode(String message) {
//        return Arrays.asList(message.split("\\$"));
//    }
    public static Data decode(String message) {
        if (message == null)
                return new ChatData(-1, "NULL MESSAGE IN DECODE METHOD; THIS IS AN EXCEPTION");
        Data data;
        String[] tordelt = (message.split("\\$"));
//        System.out.println("#####################\nDECODE");
////        System.out.println("Üzi hossza: " + tordelt.length);
//        for (String string : tordelt) {
//            System.out.println(string);
//        }
//        System.out.println("Vége.");

        int id = Integer.parseInt(tordelt[0]);
        String dataType = tordelt[1];
        String dataMessage = tordelt[2];
        int recipientID = Integer.parseInt(tordelt[3]);

        switch (dataType) {
            case "ChatData":
                System.out.println("Decoding ChatData");
                data = new ChatData(id, dataMessage);
                break;
            case "PlaceShipsData":
                System.out.println("Decoding PlaceShipsData");
                data = new PlaceShipsData(id, new Board(dataMessage));
                break;
            case "ConnectionData":
                System.out.println("Decoding ConnectionData");
                data = new ConnectionData(id);
                break;
            case "ShotData":
                System.out.println("Decoding ShotData");
                int i = Integer.parseInt(dataMessage.split(";")[0]);
                int j = Integer.parseInt(dataMessage.split(";")[1]);
                data = new ShotData(id, i, j);
                break;
            case "TurnData":
                System.out.println("Decoding TurnData");
                data = new TurnData(recipientID);
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a DataConverterben az alábbi osztály: " + dataType);
                throw new AssertionError();
        }
        data.recipientID = recipientID;
        return data;
    }

    public static String encode(List<String> message) {
        String encoded = "";

        int size = message.size();
        for (int i = 0; i < size; ++i) {
            encoded += message.get(i);
            if (i != size - 1) {
                encoded += "$";
            }
        }
        return encoded;
    }

    public static String encode(int senderID, String type, String data, int recipientID) {
        return senderID + "$" + type + "$" + data + "$" + recipientID;
    }

    public static String encode(Data data) {
        System.out.println("#####################\nENCODE");
        String encoded = new String();
        switch (data.getClass().getSimpleName()) {
            case "ChatData":
                System.out.println("Encoding ChatData");
                encoded += data.clientID + "$ChatData$" + ((ChatData) data).getMessage();
                break;
            case "PlaceShipsData":
                System.out.println("Encoding PlaceShipsData");
                encoded += data.clientID + "$PlaceShipsData$" + ((PlaceShipsData) data).getBoard().convertToString();
                break;
            case "ConnectionData":
                System.out.println("Encoding ConnectionData");
                break;
            case "ShotData":
                System.out.println("Encoding ShotData");
                encoded += data.clientID + "$ShotData$" + ((ShotData) data).getI() + ";" + ((ShotData) data).getJ();
                break;
            case "TurnData":
                System.out.println("Encoding TurnData");
                encoded += data.clientID + "$TurnData$$" + data.recipientID;
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a DataConverterben az alábbi osztály: " + data.getClass().getSimpleName());
                break;
        }
        encoded += "$" + data.recipientID;
        System.out.println(encoded);
        return encoded;
    }
}
