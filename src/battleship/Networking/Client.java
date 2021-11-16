package battleship.Networking;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Vector;
import battleship.Events.ClientEvent;
import java.util.ArrayList;

public class Client implements Runnable {
    private final List<String> messageQueue = new Vector<>();
    private String ip;
    private int port;
    private boolean close = false;
    private List<ClientEvent> listeners = new ArrayList<>();
    private Integer ID;
    
    public Client(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }
    
    public void sendMessage(String message)
    {       
        messageQueue.add(message);
    }
    
    public void close()
    {
        close = true;
    }
    
    public void addMessageEventListener(ClientEvent cEvent)
    {
        listeners.add(cEvent);
    }
    
    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket(ip, port);
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            Thread thread = new Thread(() -> {
                try 
                {
                    while(!close)
                    {
                        String inMsg = bfr.readLine();
                        for (ClientEvent listener : listeners) 
                        {
                            listener.onMessageReceived(inMsg);
                        }
                        System.out.println(inMsg);
                    }
                }
                catch (IOException ex) 
                {
                    System.out.println(ex.getMessage());
                }
            });
            thread.start();
            
            while(!close)
            {
                while (!messageQueue.isEmpty())
                {
                    String message = messageQueue.get(0);
                    messageQueue.remove(0);
                    bfw.write(message);
                    bfw.newLine();
                    bfw.flush();
                }
            }
            try
            {
                socket.close();
                bfr.close();
                bfw.close();
            } 
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
