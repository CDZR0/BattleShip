package battleship.Logic;

import battleship.DataPackage.Data;
import battleship.DataPackage.DataConverter;
import battleship.DataPackage.PlaceShipsData;
import battleship.DataPackage.Shot;
import battleship.Logic.Player;
import java.util.List;
import java.util.Vector;

public class GameLogic {

    public List<String> messageQueue = new Vector<>();

    private Player player1;
    private Player player2;

    public GameLogic() {
        player1 = new Player();
        player2 = new Player();
    }

    public void setBoard(PlaceShipsData data) {

    }

    public void processMessage(String message) {
        List<String> asd = DataConverter.decode(message);
        System.out.println("Kapott üzenet hossza: " + asd.size());
        for (String string : asd) {
            System.out.println(string);
        }
        System.out.println("Üzenet vége.");
        int id = Integer.parseInt(asd.get(0));
        String dataType = asd.get(1);
        String data = asd.get(2);
        Class<PlaceShipsData> a;
        switch (dataType) {
            case "Chat":
                messageQueue.add(DataConverter.encode(id, "Chat", data, 2));
                break;
            case "PlaceShipsData":

                break;
            case "ConnectionData":
                break;
            case "Tip":
                int i = Integer.parseInt(data.split(";")[0]);
                int j = Integer.parseInt(data.split(";")[1]);
                calcShot(new Shot(id, i, j));
                break;
            case "0":
                break;
            default:
                System.out.println("Ismeretlen");
                throw new AssertionError();
        }
    }

    private String CalculateLogic(String message) {
        //Csaba ez (várhatóan) a tiéd
        return message;
    }

    private void calcShot(Shot data) {
        if (player1.identifier.equals(data.getClientID())) {
            //Ez majd itt switch lesz
            if (player2.board.cellstatus[data.getI()][data.getJ()] == CellStatus.Ship) {
                System.out.println("Tts a hit!");
                messageQueue.add("Turn$0");
            } else {
                System.out.println("Its not a hit!");
                messageQueue.add("Turn$1");
            }
        } else {
            //Ez majd itt switch lesz
            if (player1.board.cellstatus[data.getI()][data.getJ()] == CellStatus.Ship) {
                System.out.println("Tts a hit!");
                messageQueue.add("Turn$1");
            } else {
                System.out.println("Its not a hit!");
                messageQueue.add("Turn$0");
            }
        }
    }
}
