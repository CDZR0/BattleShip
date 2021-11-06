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
        
//        Networking.Server server = new Networking.Server();
//        Thread serverThread = new Thread(server);
//        serverThread.start();
//        
//        Networking.Client client = new Networking.Client();
//        Thread clientThread = new Thread(client);
//        clientThread.start();

        Server server = new Server("0");
        
        NetworkBridge nwBridge = new NetworkBridge();
        nwBridge.ConnectServer(Settings.getIP(), Settings.getPort());
        
        NetworkBridge nwBridge2 = new NetworkBridge();
        nwBridge2.ConnectServer(Settings.getIP(), Settings.getPort());
        
        //NetworkBridge partner = new NetworkBridge();
        //partner.ConnectServer(Settings.ip, Integer.parseInt(Settings.port));
        
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
