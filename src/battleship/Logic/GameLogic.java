package battleship.Logic;

import battleship.DataPackage.CellData;
import battleship.DataPackage.ChatData;
import battleship.DataPackage.Data;
import battleship.DataPackage.DataConverter;
import battleship.DataPackage.PlaceShipsData;
import battleship.DataPackage.ShotData;
import battleship.DataPackage.TurnData;
import battleship.Logic.Player;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GameLogic {

    public List<String> messageQueue = new Vector<>();
    Random rnd = new Random();
    Player player1;
    Player player2;
    private Player[] players;

    public GameLogic() {
        player1 = new Player();
        player2 = new Player();
        players = new Player[2];
        players[0] = new Player();
        players[1] = new Player();
    }

    public void processMessage(Data data) {
        switch (data.getClass().getSimpleName()) {
            case "ChatData":
                data.setRecipientID(0);
                messageQueue.add(DataConverter.encode((ChatData) data));
                data.setRecipientID(1);
                messageQueue.add(DataConverter.encode((ChatData) data));
//                messageQueue.add(DataConverter.encode(id, "Chat", dataMessage, 0));
//                messageQueue.add(DataConverter.encode(id, "Chat", dataMessage, 1));
                break;
            case "PlaceShipsData":
                setPlayerBoard((PlaceShipsData) data);
                break;
            case "ConnectionData":
                break;
            case "ShotData":
                calcShot((ShotData) data);
                break;
            case "":
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a GameLogicban az alábbi osztály: " + data.getClass().getSimpleName());
                throw new AssertionError();
        }
    }

    private void calcShot(ShotData data) {
//        players[data.getClientID()]

        if (player1.identifier == data.getClientID()) {
            ShotData sd = new ShotData(data.getClientID(), data.getI(), data.getJ());
            sd.setRecipientID(1);
            messageQueue.add(DataConverter.encode(sd));

            CellData cd = new CellData(-1, data.getI(), data.getJ(), player2.board.cellstatus[data.getI()][data.getJ()]);
            cd.setRecipientID(0);
            messageQueue.add(DataConverter.encode(cd));

            if (player2.board.cellstatus[data.getI()][data.getJ()] == CellStatus.Ship) {
                System.out.println("Tts a hit!");
                messageQueue.add(DataConverter.encode(new TurnData(0)));
            } else {
                System.out.println("Its not a hit!");
                messageQueue.add(DataConverter.encode(new TurnData(1)));
            }
        } else {
            ShotData sd = new ShotData(data.getClientID(), data.getI(), data.getJ());
            sd.setRecipientID(0);
            messageQueue.add(DataConverter.encode(sd));

            CellData cd = new CellData(-1, data.getI(), data.getJ(), player1.board.cellstatus[data.getI()][data.getJ()]);
            cd.setRecipientID(1);
            messageQueue.add(DataConverter.encode(cd));

            if (player1.board.cellstatus[data.getI()][data.getJ()] == CellStatus.Ship) {
                System.out.println("Tts a hit!");
                messageQueue.add(DataConverter.encode(new TurnData(1)));
            } else {
                System.out.println("Its not a hit!");
                messageQueue.add(DataConverter.encode(new TurnData(0)));
            }
        }
    }

    private void setPlayerBoard(PlaceShipsData data) {
        if (data.getClientID() == 0) {
            player1.identifier = data.getClientID();
            player1.board = data.getBoard();
            player1.ready = true;
        } else {
            player2.identifier = data.getClientID();
            player2.board = data.getBoard();
            player2.ready = true;
        }

        if (player1.ready == true && player2.ready == true) {
            System.out.println("Most dölt el:");
            messageQueue.add(DataConverter.encode(new TurnData(rnd.nextInt(2))));
        }
    }
}
