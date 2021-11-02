//Vilmos

package battleship;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    public Data serverData;
    public GameLogic gameLogic;
    
    public ServerSocket sSocket;
    private String msgBuffer;
    private PrintWriter out;
    private BufferedReader bf;
    
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
            Socket socket;
            try {
                socket = sSocket.accept();
                System.out.println("client Connected");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                socket = new Socket();
            }
            
            try{
                bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                System.out.println("Reading socket");
                
                while (/*(msgBuffer = bf.readLine()) != null*/true){
                    out = new PrintWriter(socket.getOutputStream());
                    msgBuffer = bf.readLine();
                    msgBuffer = "fasz";
                    System.out.println(msgBuffer);
                    out.write("ServerToClient");
                    out.flush();
                    out.close();
                }
                
//                out = new PrintWriter(socket.getOutputStream());
//                out.write("ServerToClient");
//                out.flush();
                //out.close();
                
            } catch (IOException ex){
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
        ServeClient();
        
        while(!BattleShip.quit){
            //Server logic goes here
            System.out.print("");
        }
        System.out.println("Thread finished");
    }
}
