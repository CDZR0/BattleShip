package battleship.Events;

import battleship.DataPackage.GameEndedStatus;
import battleship.Logic.CellStatus;

public interface ClientEvent {

    void onMessageReceived(int senderID, String message);

    void onYourTurn();

    void onGameEnded(GameEndedStatus status);

    void onEnemyHitMe(int i, int j);

    void onMyHit(int i, int j, CellStatus status);

    void onJoinedEnemy();

}
