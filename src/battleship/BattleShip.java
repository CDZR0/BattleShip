package battleship;

import battleship.Utils.Settings;
import java.io.IOException;
import battleship.gui.MenuGUI;
import battleship.Networking.*;
import javafx.application.Application;
import javafx.stage.Stage;
import battleship.DataPackage.*;
import java.util.List;

public class BattleShip extends Application {
    
    public static boolean quit = false;
    
    @Override
    public void start(Stage primaryStage) {
        MenuGUI menuGUI = new MenuGUI();      
    }

    public static void main(String[] args) throws IOException{
        Settings settings = Settings.getInstance();
        ServerManager serverManager = ServerManager.getInstance();
        
        launch(args);
        BattleShip.quit = true;
    } 
}
