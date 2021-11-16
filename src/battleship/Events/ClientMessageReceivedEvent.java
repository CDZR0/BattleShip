package battleship.Events;


public interface ClientMessageReceivedEvent {
    void onMessageReceived(String message);
    
//    void onYourTurn();
//    
//    void onGameEnded(boolean win);
//    
//    void onEnemyHitMe(int x, int y);
}
