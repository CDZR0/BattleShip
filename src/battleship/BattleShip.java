//Csaba
package battleship;

import java.io.IOException;
import battleship.gui.MenuGUI;
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
        
        Networking.Server server = new Networking.Server();
        Thread serverThread = new Thread(server);
        serverThread.start();   
        
        Networking.Client client0 = new Networking.Client();
        Thread clientThread0 = new Thread(client0);
        clientThread0.start();
        
        Networking.Client client1 = new Networking.Client();
        Thread clientThread1 = new Thread(client1);
        clientThread1.start();
        client1.sendMessage("hahó1");
        
        client0.sendMessage("hahó2");
        
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
