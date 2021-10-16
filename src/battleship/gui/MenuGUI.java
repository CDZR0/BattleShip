//Csaba
package battleship.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Csaba
 */
public class MenuGUI extends JFrame
{
    public MenuGUI(){
        JButton newGame = new JButton(); 
        newGame.setText("New game");
        newGame.setBounds(50,100,95,30); 
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("new");
            }
        });
        this.add(newGame);
        
        JButton joinGame = new JButton(); 
        joinGame.setText("Join game");
        joinGame.setBounds(10,20,100,30); 
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("join");
            }
        });
        this.add(joinGame);
        
        JButton settingsButton = new JButton(); 
        settingsButton.setText("Settings");
        settingsButton.setBounds(50,100,95,30); 
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("settingsButton");
            }
        });
        this.add(settingsButton);
        
        JButton exitButton = new JButton(); 
        exitButton.setText("Exit");
        exitButton.setBounds(50,100,95,30); 
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("exitButton");
            }
        });
        this.add(exitButton);
        
        this.setSize(300,400);
        this.setTitle("BattleShip");
        this.setLayout(null);    
        this.setVisible(true);    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
    }
    
}
