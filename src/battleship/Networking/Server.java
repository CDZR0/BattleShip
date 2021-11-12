package battleship.Networking;

import java.net.*;
import java.io.*;
import battleship.*;
import java.util.Vector;
import java.util.List;

public class Server implements Runnable{
    ServerSocket sSocket = null;
    GameLogic gameLogic;
    
    private static int clientID = 0;
    
    private List<String>[] queueArray;
    
    public void addMessageToQueue(String message, int index)
    {       
        queueArray[index].add(message);
    }
    
    public Server(int port)
    {
        try 
        {
            queueArray = new Vector[2];
            for (int i = 0; i < 2; ++i) 
            {
                queueArray[i] = new Vector<>();
            }
            sSocket = new ServerSocket(port);
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
            while(!BattleShip.quit)
            {
                try 
                {
                    Socket socket = sSocket.accept();
                    
                    Integer ID = clientID++;
                    System.out.println("Someone joined the server with ID: " + ID);
                    int otherQueueID = (ID == 0) ? 1 : 0;
                    int ownQueueID = (ID == 0) ? 0 : 1;
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    
                    Thread thread2 = new Thread(() -> {
                        while (!BattleShip.quit) 
                        {
                            try 
                            {
                                String inMsg = bfr.readLine();
                                String BroadcastMessage = gameLogic.processMessage(ID, inMsg);
                                addMessageToQueue(BroadcastMessage, otherQueueID);
                            } 
                            catch (IOException ex) 
                            {
                                System.out.println(ex.getMessage());
                                break;
                            }
                        }
                        
                    });
                    thread2.start();
                    
                    while(!BattleShip.quit)
                    {                      
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
                    System.out.println("Secondary player disconnected.");
                    --clientID;
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
