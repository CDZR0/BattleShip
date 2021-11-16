//Csaba
package battleship.DataPackage;

import battleship.Logic.Board;

/**
 *
 * @author nycs0
 */
public class PlaceShipsData extends Data {

    Board board;

    public PlaceShipsData(int clientID, Board board) {
        super(clientID);
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

}
