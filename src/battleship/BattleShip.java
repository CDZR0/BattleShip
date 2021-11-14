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
        
//        Server server = new Server(Settings.getPort());
//        Thread serverThread = new Thread(server);
//        serverThread.start();
        
        //DEBUG
        
        launch(args);
        BattleShip.quit = true;
    } 
}
