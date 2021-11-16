package battleship.Events;


public interface ClientGameEndedEvent {
    void onGameEnded(boolean win);
}
