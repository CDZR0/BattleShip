package battleship;

public class Server extends Thread {
    public Data serverData;
    public GameLogic gameLogic;
    
    public static byte clientID;
    
    Server(NetworkBridge client){
        gameLogic = new GameLogic();
        client.ID = clientID++;
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
        }
    }
}
