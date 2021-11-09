package battleship.Networking;

import battleship.Settings;
import battleship.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable {
    private final ArrayList<String> messageQueue = new ArrayList<>();
    
    public void sendMessage(String message)
    {       
        messageQueue.add(message);
    }
    
    @Override
    public void run()
    {
        BufferedReader bfr = null;
        BufferedWriter bfw = null;
        Socket socket = null;
        
        try
        {
            socket = new Socket(Settings.getIP(), Settings.getPort());
            
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bfw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            while(!BattleShip.quit)
            {
                while (messageQueue.size() > 0)
                {
                    String message = messageQueue.get(0);
                    messageQueue.remove(0);
                    bfw.write(message);
                    bfw.newLine();
                    bfw.flush();
                }
                
                String inMsg = bfr.readLine();
                System.out.println(inMsg);
                
                if (inMsg != null && inMsg.contains("QUIT")) 
                {
                    break;
                }
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        finally{
            try
            {
                if (socket != null)
                    socket.close();
                if (bfr != null)
                    bfr.close();
                if (bfw != null)
                    bfw.close();
            } 
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
