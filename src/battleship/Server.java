package battleship;

public class Server implements Runnable {
    public Data serverData;
    public GameLogic gameLogic;
    
    public static byte clientID;
    
    Server(NetworkBridge host){
        gameLogic = new GameLogic();
        host.ID = clientID++;
    }
    
    public void SendDataToClient(Data serverData){
        this.serverData = serverData;
    }
    
    public void ProcessData(Data clientData){
        this.serverData = clientData;
    }
    
    @Override
    public void run(){
        while(!BattleShip.quit){
            //Server logic goes here
            System.out.print("");
        }
        System.out.println("Thread finished");
        
    }
}
