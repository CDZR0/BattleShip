//Csaba
package battleship.Events;

/**
 *
 * @author Csaba
 */
public interface ShipSelectorEvent {

    void onSelectShip(int shipSize);

    void onSelectDirection(boolean shipPlaceHorizontal);

    void onRanOutOfShips(boolean ranOutOf);

    void onClearBoard();

    void onPlaceRandomShips();

    void onDone();
}
