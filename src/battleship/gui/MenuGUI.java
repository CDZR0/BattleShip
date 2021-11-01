//Csaba
package battleship.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class MenuGUI extends JFrame {

    JButton newGame, joinGame, settingsButton, exitButton;
    JPanel menu;

    public MenuGUI() {
        this.setSize(800, 600);
        this.setResizable(false);
        this.setTitle("BattleShip");
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new JPanel();
        menu.setBackground(Color.green);
        menu.setLayout(null);
        menu.setBounds(0, 0, 800, 600);
        this.add(menu);

        int buttonWidth = 100;
        int buttonHeight = 35;

        JLabel title = new JLabel();
        title.setText("Battleship game");
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setSize(800, 100);
        title.setLocation(0, 0);
        menu.add(title);

        GameGUI gameGUI = new GameGUI();
        gameGUI.setBounds(0, 0, this.size().width, this.size().height);
        gameGUI.setVisible(false);
        gameGUI.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {

            }

            public void componentHidden(ComponentEvent e) {
                menu.setVisible(true);
            }
        });
        //this.setGlassPane(gameGUI);

        this.add(gameGUI);

        newGame = new JButton();
        newGame.setText("New game");
        newGame.setSize(buttonWidth, buttonHeight);
        newGame.setLocation((this.size().width - buttonWidth) / 2, 222);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("new");
                menu.setVisible(false);
                gameGUI.setVisible(true);
            }
        });
        menu.add(newGame);

        joinGame = new JButton();
        joinGame.setText("Join game");
        joinGame.setSize(buttonWidth, buttonHeight);
        joinGame.setLocation((this.size().width - buttonWidth) / 2, 262);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("join");
            }
        });
        menu.add(joinGame);

        settingsButton = new JButton();
        settingsButton.setText("Settings");
        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setLocation((this.size().width - buttonWidth) / 2, 302);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("settingsButton");
            }
        });
        menu.add(settingsButton);

        exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setLocation((this.size().width - buttonWidth) / 2, 342);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("exitButton");
                System.exit(0);
            }
        });
        menu.add(exitButton);

    }

    private void setVisibleMenu(boolean value) {
        newGame.setVisible(value);
        joinGame.setVisible(value);
        settingsButton.setVisible(value);
        exitButton.setVisible(value);
    }

}
