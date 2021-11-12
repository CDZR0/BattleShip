package battleship.Networking;

import battleship.Settings;
import battleship.*;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Vector;

public class Client implements Runnable {
    private final List<String> messageQueue = new Vector<>();
    
    public void sendMessage(String message)
    {       
        messageQueue.add(message);
    }
    
    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket(Settings.getIP(), Settings.getPort());
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            Thread thread = new Thread(() -> {
                try 
                {
                    while(!BattleShip.quit)
                    {
                        String inMsg = bfr.readLine();
                        System.out.println(inMsg);
                    }
                }
                catch (IOException ex) 
                {
                    System.out.println(ex.getMessage());
                }
            });
            thread.start();
            
            while(!BattleShip.quit)
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
