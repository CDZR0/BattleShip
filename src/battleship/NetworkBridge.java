package battleship;

import java.net.*;
import java.io.*;

public class NetworkBridge {
    public Server server;
    public byte ID;
    
    void ConnectServer(String IP){
        //Connect to the server of the host
    }
    
    void CreateServer(){
        server = new Server(this);
        server.start();
        //Create TCP/IP server
    }
    
    void SendData(){
        //Send the data received from a client to the server
    }
    
    void ReceiveData(){
        //Send the data received from the server to a client
    }
}
