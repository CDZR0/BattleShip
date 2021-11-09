package battleship;

import java.io.IOException;
import battleship.gui.MenuGUI;
import battleship.Networking.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class BattleShip extends Application {
    
    public static boolean quit = false;
    
    @Override
    public void start(Stage primaryStage) {
        MenuGUI menuGUI = new MenuGUI();      
    }

    public static void main(String[] args) throws IOException{
        Settings settings = Settings.getInstance();
        //DEBUG
        
        Server server;
        server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();   
        
        Client client0 = new Client();
        Thread clientThread0 = new Thread(client0);
        clientThread0.start();
        
        Client client1 = new Client();
        Thread clientThread1 = new Thread(client1);
        clientThread1.start();
        
        client1.sendMessage("hahó1");
        client1.sendMessage("hahó2");
        
        client0.sendMessage("hahóFromTheOtherSide");
        client0.sendMessage("márta");
        client0.sendMessage("szökik a málna");
        
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
