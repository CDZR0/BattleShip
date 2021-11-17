package battleship.Events;

import battleship.Logic.CellStatus;

public interface ClientEvent {

    void onMessageReceived(String message);

    void onYourTurn();

    void onGameEnded(boolean win);

    void onEnemyHitMe(int i, int j);

    void onMyHit(int i, int j, CellStatus status);
}
