//Csaba
package battleship;

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

    public static void main(String[] args){
        Settings settings = new Settings();
        //DEBUG
        NetworkBridge nwBridge = new NetworkBridge();
        nwBridge.CreateServer();
        
        NetworkBridge nwBridge2 = new NetworkBridge();
        nwBridge2.ConnectServer(settings.ip + ":" + settings.port);
        NetworkBridge nwBridge3 = new NetworkBridge();
        nwBridge3.ConnectServer(settings.ip + ":" + settings.port);
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
