package battleship.DataPackage;

import battleship.Logic.Board;
import battleship.Logic.CellStatus;
import java.util.List;

public class DataConverter {

//    public static List<String> decode(String message) {
//        return Arrays.asList(message.split("\\$"));
//    }
    public static Data decode(String message) {
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

        int i, j;

        switch (dataType) {
            case "ChatData":
                //System.out.println("Decoding ChatData");
                data = new ChatData(id, dataMessage);
                break;
            case "PlaceShipsData":
                //System.out.println("Decoding PlaceShipsData");
                data = new PlaceShipsData(id, new Board(dataMessage));
                break;
            case "ConnectionData":
                //System.out.println("Decoding ConnectionData");
                data = new ConnectionData(id);
                break;
            case "ShotData":
                //System.out.println("Decoding ShotData");
                i = Integer.parseInt(dataMessage.split(";")[0]);
                j = Integer.parseInt(dataMessage.split(";")[1]);
                data = new ShotData(id, i, j);
                break;
            case "TurnData":
                //System.out.println("Decoding TurnData");
                data = new TurnData(recipientID);
                break;
            case "CellData":
                //System.out.println("Decoding CellData");
                i = Integer.parseInt(dataMessage.split(";")[0]);
                j = Integer.parseInt(dataMessage.split(";")[1]);
                CellStatus status = CellStatus.valueOf(dataMessage.split(";")[2]);
                data = new CellData(id, i, j, status);
                break;
            case "GameEndedData":
                data = new GameEndedData(GameEndedStatus.valueOf(dataMessage), recipientID);
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
        //System.out.println("#####################\nENCODE");
        String encoded = new String();
        switch (data.getClass().getSimpleName()) {
            case "ChatData":
                //System.out.println("Encoding ChatData");
                encoded += data.clientID + "$ChatData$" + ((ChatData) data).getMessage();
                break;
            case "PlaceShipsData":
                //System.out.println("Encoding PlaceShipsData");
                encoded += data.clientID + "$PlaceShipsData$" + ((PlaceShipsData) data).getBoard().convertToString();
                break;
            case "ConnectionData":
                //System.out.println("Encoding ConnectionData");
                break;
            case "ShotData":
                //System.out.println("Encoding ShotData");
                encoded += data.clientID + "$ShotData$" + ((ShotData) data).getI() + ";" + ((ShotData) data).getJ();
                break;
            case "TurnData":
                //System.out.println("Encoding TurnData");
                encoded += data.clientID + "$TurnData$$" + data.recipientID;
                break;
            case "CellData":
                //System.out.println("Encoding CellData");
                encoded += data.clientID + "$CellData$" + ((CellData) data).getI() + ";" + ((CellData) data).getJ() + ";" + ((CellData) data).getStatus();
                break;
            case "GameEndedData":
                encoded += data.clientID + "$GameEndedData$" + ((GameEndedData) data).status + "$" + ((GameEndedData) data).recipientID;
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a DataConverterben az alábbi osztály: " + data.getClass().getSimpleName());
                break;
        }
        encoded += "$" + data.recipientID;
        //System.out.println(encoded);
        return encoded;
    }
}
