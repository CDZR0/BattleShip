//Csaba
package battleship.Events;

import battleship.Networking.ServerAddress;

/**
 *
 * @author nycs0
 */
public interface JoinGUIEvent {
        void onConnect(ServerAddress serverAddress);
}
