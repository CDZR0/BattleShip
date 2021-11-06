package Networking;

import java.net.*;
import java.io.*;
import battleship.*;

public class Server implements Runnable{
    String BroadcastMessage = "ÓÓÓÓÓÓÓÓÓ";
    ServerSocket sSocket = null;
    GameLogic logicHandler;
    
    public Server(){
        try {
            sSocket = new ServerSocket(Settings.getPort());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        logicHandler = new GameLogic();
    } 
    
    private void ServeClient(){
        Thread thread = new Thread(() -> {
            BufferedReader bfr = null;
            BufferedWriter bfw = null;
            Socket socket = null;
        
            
            while(!BattleShip.quit)
            {
                try 
                {
                    socket = sSocket.accept();
                    bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    
                    while(!BattleShip.quit)
                    {
                        String inMsg = bfr.readLine();
                        BroadcastMessage = inMsg;
                        
                        String outMsg = BroadcastMessage;
                        bfw.write(outMsg);
                        bfw.newLine();
                        bfw.flush();
                        
                        if (inMsg != null && inMsg.equals("QUIT")) {
                            break;
                        }
                        if (outMsg != null && outMsg.equals("QUIT")) {
                            break;
                        }
                    }
                } 
                catch (IOException ex) 
                {
                    System.out.println(ex.getMessage());                   
                }
            }
        });
        thread.start();
    }
    
    @Override
    public void run() {
        ServeClient();
        ServeClient();
    }
    
}
