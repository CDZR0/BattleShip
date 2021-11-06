package Networking;

import java.net.*;
import java.io.*;
import battleship.*;

public class Server implements Runnable{
    ServerSocket sSocket = null;
    GameLogic gameLogic;
    
    private static int clientID = 0;
    
    public Server()
    {
        try 
        {
            sSocket = new ServerSocket(Settings.getPort());
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
        gameLogic = new GameLogic();
    } 
    
    private void ServeClient()
    {
        Thread thread = new Thread(() -> {
            BufferedReader bfr;
            BufferedWriter bfw;
            Socket socket;
            
            
            while(!BattleShip.quit)
            {
                try 
                {
                    socket = sSocket.accept();
                    Integer ID = clientID++;
                    bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    
                    while(!BattleShip.quit)
                    {
                        String inMsg = bfr.readLine();
                        
                        String BroadcastMessage = gameLogic.processMessage(ID, inMsg);
                        
                        bfw.write(BroadcastMessage);
                        bfw.newLine();
                        bfw.flush();
                        
                        if (BroadcastMessage != null && BroadcastMessage.equals("QUIT")) 
                        {
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
    public void run() 
    {
        ServeClient();
        ServeClient();
    }
}
