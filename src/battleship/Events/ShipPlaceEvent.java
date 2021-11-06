//Csaba
package battleship.Events;

/**
 *
 * @author Csaba
 */
public interface ShipPlaceEvent {
    
    void onPlace(int shipSize, boolean shipPlaceHorizontal);
    void onPickUp(int shipSize, boolean shipPlacehorizontal);
}
