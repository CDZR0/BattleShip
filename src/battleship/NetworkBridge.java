package battleship;

import java.net.*;
import java.io.*;

public class NetworkBridge{
    public Server server;
    public byte ID;
    public Thread serverThread;
    
    void ConnectServer(String IP){
        if (Server.clientID < 2){
            //Connect to the server of the host
            ID = Server.clientID++;
        }
        else {
            System.out.println("Server full " + IP);
        }
    }
    
    void CreateServer(){
        server = new Server(this);
        serverThread = new Thread(server);
        serverThread.start();
        //Create TCP/IP server
    }
    
    void CloseServer(){
        if (ID == 0) {
            try{
                serverThread.join();
            }
            catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    void SendData(){
        //Send the data received from a client to the server
    }
    
    void ReceiveData(){
        //Send the data received from the server to a client
    }
}
