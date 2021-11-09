package battleship.Networking;

import java.net.*;
import java.io.*;
import battleship.*;
import java.util.ArrayList;

public class Server implements Runnable{
    ServerSocket sSocket = null;
    GameLogic gameLogic;
    
    private static int clientID = 0;
    
    private ArrayList<String>[] queueArray;
    
    public void addMessageToQueue(String message, int index)
    {       
        queueArray[index].add(message);
    }
    
    public Server()
    {
        try 
        {
            queueArray = new ArrayList[2];
            for (int i = 0; i < 2; i++) {
                queueArray[i] = new ArrayList<>();
            }
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
                    int otherQueueID = (ID == 0) ? 1 : 0;
                    int ownQueueID = (ID == 0) ? 0 : 1;
                    bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    
                    while(!BattleShip.quit)
                    {
                        String inMsg = bfr.readLine();
                        
                        String BroadcastMessage = gameLogic.processMessage(ID, inMsg);
                        
                        addMessageToQueue(BroadcastMessage, otherQueueID);
                        
                        while (queueArray[ownQueueID].size() > 0)
                        {
                            String message = queueArray[ownQueueID].get(0);
                            queueArray[ownQueueID].remove(0);
                            bfw.write(message);
                            bfw.newLine();
                            bfw.flush();
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
