//Csaba
package battleship;

import java.io.IOException;
import battleship.gui.MenuGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BattleShip extends Application {
    
    public static boolean quit = false;
    
    @Override
    public void start(Stage primaryStage) {
        MenuGUI menuGUI = new MenuGUI(primaryStage);      
    }

    public static void main(String[] args) throws IOException{
        Settings settings = new Settings();
        //DEBUG
        NetworkBridge nwBridge = new NetworkBridge();
        nwBridge.CreateServer();
        //Thread t1 = new Thread(nwBridge);
        //t1.start();
        //DEBUG
        
        NetworkBridge partner = new NetworkBridge();
        partner.ConnectServer(Settings.ip, Integer.parseInt(Settings.port));
        
        NetworkBridge partner2 = new NetworkBridge();
        partner2.ConnectServer(Settings.ip, Integer.parseInt(Settings.port));
        
        launch(args);
        BattleShip.quit = true;
    } 
}
