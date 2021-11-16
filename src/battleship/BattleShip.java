package battleship;

import java.io.IOException;
import battleship.gui.MenuGUI;
import battleship.Networking.*;
import javafx.application.Application;
import javafx.stage.Stage;
import battleship.DataPackage.*;

public class BattleShip extends Application {
    
    public static boolean quit = false;
    
    @Override
    public void start(Stage primaryStage) {
        MenuGUI menuGUI = new MenuGUI();      
    }

    public static void main(String[] args) throws IOException{
        Settings settings = Settings.getInstance();
        ServerManager serverManager = ServerManager.getInstance();
        
        DataConverter.decode("1$CHAT$CIGÁNY VAGYOL");
        
        launch(args);
        BattleShip.quit = true;
    } 
}
