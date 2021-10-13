//Vilmos

package battleship;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
    public Data serverData;
    public GameLogic gameLogic;
    
    public ServerSocket sSocket;
    public Socket cSocket1, cSocket2;
    private String msgBuffer;
    private PrintWriter out;
    private BufferedReader in;
    
    public static byte clientID;
    
    Server(NetworkBridge host){
        gameLogic = new GameLogic();
    }
    
    public void SendDataToClient(Data serverData){
        this.serverData = serverData;
    }
    
    public void ProcessData(Data clientData){
        this.serverData = clientData;
    }
    
    public void ServeClient(){
        Thread thread = new Thread(() -> {
            try {
                Socket client = sSocket.accept();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
            while(!BattleShip.quit){
                System.out.print("");
            }
        });
        thread.start();
    }
    
    @Override
    public void run(){
        try{
            sSocket = new ServerSocket(Integer.parseInt(Settings.port));
            System.out.println("Server created at " + Settings.port);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
        ServeClient();
        
        while(!BattleShip.quit){
            //Server logic goes here
            System.out.print("");
        }
        System.out.println("Thread finished");
    }
}
