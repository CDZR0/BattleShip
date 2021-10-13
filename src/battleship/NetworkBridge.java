//Vilmos

package battleship;

import java.net.*;
import java.io.*;

public class NetworkBridge implements Runnable{
    public Server server;
    public byte ID;
    
    private Thread serverThread;   
    private Socket socket;   
    private PrintWriter out;
    private BufferedReader in;
    
    public void ConnectServer(String IP, int port) throws IOException{
        if (Server.clientID < 2){
            socket = new Socket("localhost", Integer.parseInt(Settings.port));
            ID = Server.clientID++;
            System.out.println("client " + ID + " Connected");
        }
        else {
            System.out.println("Server full at " + IP + ":" + Settings.port);
        }
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
