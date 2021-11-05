//Csaba
package battleship.Events;

/**
 *
 * @author Csaba
 */
public interface ShipSelectorEvent {

    void onSelect(int shipSize, boolean shipPlaceHorizontal);
    void onRanOutOfShips(boolean ranOutOf);
}
