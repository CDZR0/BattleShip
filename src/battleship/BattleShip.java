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
        Settings.Init();
        //DEBUG
        NetworkBridge nwBridge = new NetworkBridge();
        nwBridge.CreateServer();      
        
        NetworkBridge partner = new NetworkBridge();
        partner.ConnectServer(Settings.ip, Integer.parseInt(Settings.port));
        
        NetworkBridge partner2 = new NetworkBridge();
        partner2.ConnectServer("192.128.0.2", Integer.parseInt(Settings.port));
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
