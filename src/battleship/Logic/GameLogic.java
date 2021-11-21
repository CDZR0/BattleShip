package battleship.Logic;

import battleship.DataPackage.CellData;
import battleship.DataPackage.ChatData;
import battleship.DataPackage.Data;
import battleship.DataPackage.DataConverter;
import battleship.DataPackage.GameEndedData;
import battleship.DataPackage.GameEndedStatus;
import battleship.DataPackage.PlaceShipsData;
import battleship.DataPackage.ShotData;
import battleship.DataPackage.TurnData;
import battleship.Logic.Player;
import java.awt.Point;
import java.util.ArrayList;
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
                break;
            case "PlaceShipsData":
                setPlayerBoard((PlaceShipsData) data);
                break;
            case "ConnectionData":
                break;
            case "ShotData":
                calcShot2((ShotData) data);
                break;
            case "":
                break;
            default:
                System.out.println("########## ISMERETLEN OSZTÁLY #########");
                System.out.println("Nincs implementálva a GameLogicban az alábbi osztály: " + data.getClass().getSimpleName());
                throw new AssertionError();
        }
    }

    private void calcShot2(ShotData data) {

        int egyik = data.getClientID();
        int masik = egyik == 1 ? 0 : 1;

        ShotData sd = new ShotData(data.getClientID(), data.getI(), data.getJ());
        sd.setRecipientID(masik);
        messageQueue.add(DataConverter.encode(sd));

        CellData cd = new CellData(-1, data.getI(), data.getJ(), players[masik].board.cellstatus[data.getI()][data.getJ()]);
        cd.setRecipientID(egyik);
        messageQueue.add(DataConverter.encode(cd));

        if (players[masik].board.cellstatus[data.getI()][data.getJ()] == CellStatus.Ship) {
            players[masik].board.cellstatus[data.getI()][data.getJ()] = CellStatus.ShipHit;
            if (players[masik].board.isSunk(data.getI(), data.getJ())) {
                hitNear(egyik, masik, data.getI(), data.getJ());
            }
            if (isWin(players[masik])) {
                messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Win, egyik)));
                messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Defeat, masik)));
            } else {
                messageQueue.add(DataConverter.encode(new TurnData(egyik)));
            }
        } else {
            messageQueue.add(DataConverter.encode(new TurnData(masik)));
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
                player2.board.cellstatus[data.getI()][data.getJ()] = CellStatus.ShipHit;
                System.out.println("Tts a hit!");
                if (isWin(player2)) {
                    messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Win, 0)));
                    messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Defeat, 1)));
                } else {
                    messageQueue.add(DataConverter.encode(new TurnData(0)));
                }
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
                player1.board.cellstatus[data.getI()][data.getJ()] = CellStatus.ShipHit;
                System.out.println("Tts a hit!");
                if (isWin(player1)) {
                    messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Win, 1)));
                    messageQueue.add(DataConverter.encode(new GameEndedData(GameEndedStatus.Defeat, 0)));
                } else {
                    messageQueue.add(DataConverter.encode(new TurnData(1)));
                }
            } else {
                System.out.println("Its not a hit!");
                messageQueue.add(DataConverter.encode(new TurnData(0)));
            }
        }
    }

    private boolean isWin(Player player) {
        if (player.board.hasCellStatus(CellStatus.Ship)) {
            return false;
        }
        return true;
    }

    private void hitNear(int egyik, int masik, int i, int j) {
        for (Point nearShipPoint : players[masik].board.nearShipPoints(i, j)) {
            CellData cd = new CellData(-1, nearShipPoint.x, nearShipPoint.y, players[masik].board.cellstatus[nearShipPoint.x][nearShipPoint.y]);
            cd.setRecipientID(egyik);
            messageQueue.add(DataConverter.encode(cd));
        }
    }

    private void setPlayerBoard(PlaceShipsData data) {
        if (data.getClientID() == 0) {
            player1.identifier = data.getClientID();
            player1.board = data.getBoard();
            player1.ready = true;
            players[0].identifier = data.getClientID();
            players[0].ready = true;
            players[0].board = data.getBoard();
        } else {
            player2.identifier = data.getClientID();
            player2.board = data.getBoard();
            player2.ready = true;
            players[1].identifier = data.getClientID();
            players[1].ready = true;
            players[1].board = data.getBoard();
        }

        if (player1.ready == true && player2.ready == true) {
            messageQueue.add(DataConverter.encode(new TurnData(rnd.nextInt(2))));
        }
    }
}
