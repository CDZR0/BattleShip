package battleship.Logic;

import battleship.DataPackage.Data;
import battleship.DataPackage.PlaceShipsData;
import battleship.Logic.Player;

public class GameLogic {

    private Player player1;
    private Player player2;

    public GameLogic() {
        player1 = new Player();
        player2 = new Player();
    }

    public void setBoard(PlaceShipsData data) {

    }

    public void processMessage(Data data) {
        switch (data.getClass().getSimpleName()) {
            case "PlaceShipsData":
                
                break;
            case "ConnectionData":

                break;
            case "":

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
}
