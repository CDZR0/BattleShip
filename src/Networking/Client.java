package Networking;

import battleship.Settings;
import battleship.*;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

    private String outMsg = null;
    private boolean canSendMessage = false;
    
    public void sendMessage(String message){
        outMsg = message;
        canSendMessage = true;
    }
    
    @Override
    public void run() {
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
                if (canSendMessage){
                    bfw.write(outMsg);
                    bfw.newLine();
                    bfw.flush();
                    canSendMessage = false;
                    continue;
                }
                
                String inMsg = bfr.readLine();
                System.out.println(inMsg);
                
                if (outMsg != null && outMsg.equals("QUIT")) {
                    break;
                }
                if (inMsg != null && inMsg.equals("QUIT")) {
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
                
                if (socket != null) {
                    socket.close();
                }
                if (bfr != null) {
                    bfr.close();
                }
                if (bfw != null) {
                    bfw.close();
                }
            } 
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        
    }
    
}
