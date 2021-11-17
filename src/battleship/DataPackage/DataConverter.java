package battleship.DataPackage;

import battleship.Logic.Board;
import java.util.List;
import java.util.Arrays;

public class DataConverter {

//    public static List<String> decode(String message) {
//        return Arrays.asList(message.split("\\$"));
//    }
    public static Data decode(String message) {
        Data data;
        String[] tordelt = (message.split("\\$"));

        System.out.println("Üzi hossza: " + tordelt.length);
        for (String string : tordelt) {
            System.out.println(string);
        }
        System.out.println("Vége.");

        int id = Integer.parseInt(tordelt[0]);
        String dataType = tordelt[1];
        String dataMessage = tordelt[2];

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
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a DataConverterben az alábbi osztály: " + dataType);
                throw new AssertionError();
        }
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
        String encoded = new String();
        switch (data.getClass().getSimpleName()) {
            case "ChatData":
                System.out.println("Converting ChatData");
                encoded += data.clientID + "$ChatData$" + ((ChatData) data).getMessage();
                break;
            case "PlaceShipsData":
                System.out.println("Converting PlaceShipsData");
                encoded += data.clientID + "$PlaceShipsData$" + ((PlaceShipsData) data).getBoard().convertToString();
                break;
            case "ConnectionData":
                System.out.println("Converting ConnectionData");
                break;
            case "ShotData":
                System.out.println("Converting ShotData");
                encoded += data.clientID + "$ShotData$" + ((ShotData)data).getI() + ";" + ((ShotData)data).getJ();
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a DataConverterben az alábbi osztály: " + data.getClass().getSimpleName());
                break;
        }
        System.out.println(encoded);
        return encoded;
    }
}
