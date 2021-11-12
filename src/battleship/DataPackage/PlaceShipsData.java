//Csaba
package battleship.DataPackage;

import battleship.Logic.Board;

/**
 *
 * @author nycs0
 */
public class PlaceShipsData {

    byte clientID;
    Board board;

    public PlaceShipsData(byte clientID, Board board) {
        this.clientID = clientID;
        this.board = board;
    }

    public byte getClientID() {
        return clientID;
    }

    public Board getBoard() {
        return board;
    }

}
