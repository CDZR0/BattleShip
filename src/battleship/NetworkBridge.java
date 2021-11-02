//Vilmos 

package battleship;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkBridge implements Runnable{
    public Server server;
    public byte ID;
    
    private Thread serverThread;   
    private Socket socket;
    private String msgBuffer;
    private PrintWriter out;
    private BufferedReader bf;
    
    public void ConnectServer(String IP, int port){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Connection timed out at " + IP + ":" + port);
                Thread.currentThread().interrupt();
            }
        };
        
        Timer timer = new Timer("Timer");
        timer.schedule(task, 5000);
        
        
        Thread thread = new Thread(() -> {
            if (Server.clientID < 2){
                ID = Server.clientID++;
                try { socket = new Socket(IP, port); } 
                catch (IOException ex) { System.out.println(ex.getMessage()); }
                timer.cancel();
            }
            else {
                System.out.println("Server is full at " + IP + ":" + port);
                timer.cancel();
            }
                    
            
            try {                                    
//                out.write("ClientToServer");
//                out.flush();
//                out.close();
                bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));    
                               
                while (/*(msgBuffer = bf.readLine()) != null*/true){
                    out = new PrintWriter(socket.getOutputStream());
                    out.write("ClientToServer");
                    out.flush();
                    out.close();
                    
                    msgBuffer = bf.readLine();
                    System.out.println(msgBuffer);
                }
                //out.close();
                
                //NOT FINISHED
                
            }
            catch (IOException ex){
                System.out.println(ex.getMessage());
                System.out.println("fa");
            }
        });
        thread.start();
        
        
    }
    
    public void DisconnectServer() throws IOException{
        socket.close();
    }
    
    public void CreateServer() throws IOException{
        server = new Server(this);
        serverThread = new Thread(server);
        serverThread.start();
        ConnectServer(Settings.ip, Integer.parseInt(Settings.port));
        //Create TCP/IP server
    }
    
    public void CloseServer(){
        try{
            serverThread.join();
            server.sSocket.close();
        }
        catch(IOException | InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void SendData(){
        //Send the data received from a client to the server
    }
    
    public void ReceiveData(){
        //Send the data received from the server to a client
    }

    @Override
    public void run() {
        try {
            socket = new Socket(Settings.ip, Integer.parseInt(Settings.port));
            System.out.println("client " + ID + " Connected");
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        while(!BattleShip.quit){
            System.out.print("");
        }
        if (ID == 0){
            CloseServer();
        }
    }
}
