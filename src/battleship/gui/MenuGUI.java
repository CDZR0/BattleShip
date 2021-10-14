//Csaba
package battleship.gui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;

/**
 *
 * @author Csaba
 */
public class MenuGUI extends JFrame
{
    public MenuGUI(Stage primaryStage){
        Button newGame = new Button();
        newGame.setText("New game");
        newGame.setOnAction((ActionEvent event) -> {
            //
        });
        
        Button joinGame = new Button();
        joinGame.setText("Join game");
        joinGame.setOnAction((ActionEvent event) -> {
            //
        });
        
        Button settingsButton = new Button();
        settingsButton.setText("Settings");
        settingsButton.setOnAction((ActionEvent event) -> {
            //
        });
        
        Button exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction((ActionEvent event) -> {
            //
        });      

        StackPane root = new StackPane();
        root.getChildren().add(newGame);
        root.getChildren().add(joinGame);
        root.getChildren().add(settingsButton);
        root.getChildren().add(exitButton);
        
        Scene scene = new Scene(root, 500, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
