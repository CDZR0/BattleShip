//Csaba
package battleship.Logic;

import battleship.DataPackage.PlaceShipsData;

/**
 *
 * @author Csaba
 */
public class Player {

    String identifier;
    Board board;

    public Player() {
        board = new Board();
    }

    public void setBoard(PlaceShipsData ships) {

    }

    public String getIdentifier() {
        return identifier;
    }

    public Board getBoard() {
        return board;
    }
}
