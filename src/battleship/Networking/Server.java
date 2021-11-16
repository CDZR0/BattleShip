package battleship.Networking;

import battleship.DataPackage.DataConverter;
import battleship.Logic.GameLogic;
import battleship.Logic.GameLogicVili;
import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{
    private ServerSocket sSocket = null;
    private GameLogic gameLogic;
    private GameLogicVili gameLogicVili;
    
    private int clientID = 0;   
    private List<String>[] queueArray;
    private boolean close = false;
    
    public void addMessageToQueue(String message, int index)
    {       
        queueArray[index].add(message);
    }
    
    public void close() throws IOException
    {
        close = true;
        sSocket.close();
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
            while(!close)
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
                    
                    
                    Thread threadReader = new Thread(() -> {
                        while (!close) 
                        {
                            try
                            {
                                String inMsg = bfr.readLine();
                                gameLogic.processMessage(ID + "$" + inMsg);
                            }
                            catch (IOException ex) 
                            {
                                System.out.println(ex.getMessage());
                                break;
                            }
                        }
                        
                    });
                    threadReader.start();
                    
                    while(!close)
                    {                      
                        while (!queueArray[ownQueueID].isEmpty())
                        {
                            
                            String message = queueArray[ownQueueID].get(0);
                            queueArray[ownQueueID].remove(0);
                            bfw.write(message);
                            bfw.newLine();
                            bfw.flush();
                        }
                        
                        while (!gameLogic.messageQueue.isEmpty()) 
                        {
                            String BroadcastMessage = gameLogic.messageQueue.get(0);
                            gameLogic.messageQueue.remove(0);
                            List<String> decoded = DataConverter.decode(BroadcastMessage);
                            for (String string : decoded) {
                                System.out.println(string);
                                System.out.println("hahdasd");
                            }
                            int recipient = Integer.parseInt(decoded.get(3));
                            if (recipient == 2)
                                addMessageToQueue(BroadcastMessage, recipient);
                            else {
                                addMessageToQueue(BroadcastMessage, 0);
                                addMessageToQueue(BroadcastMessage, 1);
                            }
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
    
    private String getLocalIP()
    {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress)ee.nextElement();                  
                    if (i.getHostAddress().split("\\.")[0].equals("192"))
                        return i.getHostAddress();               
                }
            }
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "NO IP FOUND";
    }
    
    @Override
    public void run() 
    {
        ServeClient();
        ServeClient();
        
        System.out.println(getLocalIP());
    }
}
