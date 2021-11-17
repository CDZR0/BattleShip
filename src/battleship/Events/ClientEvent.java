package battleship.Events;


public interface ClientEvent {
    void onMessageReceived(String message);
    
    void onYourTurn();
    
    void onGameEnded(boolean win);
    
    void onEnemyHitMe(int i, int j);
}
