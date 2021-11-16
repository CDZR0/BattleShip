//Csaba
package battleship.DataPackage;

import battleship.Logic.Board;

/**
 *
 * @author nycs0
 */
public class PlaceShipsData {

    int clientID;
    Board board;

    public PlaceShipsData(int clientID, Board board) {
        this.clientID = clientID;
        this.board = board;
    }

    public int getClientID() {
        return clientID;
    }

    public Board getBoard() {
        return board;
    }

}
