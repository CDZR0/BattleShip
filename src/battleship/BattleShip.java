package battleship;

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
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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
