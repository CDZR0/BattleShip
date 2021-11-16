package battleship.Logic;

import battleship.DataPackage.Data;
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
            }
        } else {

        }
    }
}
