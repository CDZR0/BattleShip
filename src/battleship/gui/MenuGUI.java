//Csaba
package battleship.gui;

import battleship.Events.JoinGUIEvent;
import battleship.Networking.ServerAddress;
import battleship.Resources.Resources;
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

    public static int WindowInsets;
    JButton newGame, joinGame, settingsButton, exitButton;
    JPanel menu;
    boolean dontShowMenu;

    public MenuGUI() {
        this.setSize(800, 600);
        this.setResizable(false);
        this.setTitle("BattleShip");
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowInsets = this.insets().top;

        menu = new JPanel();
        menu.setBackground(Resources.BackgroundColor);
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

        newGame = new JButton();
        newGame.setText("New game");
        newGame.setSize(buttonWidth, buttonHeight);
        newGame.setLocation((this.size().width - buttonWidth) / 2, 222);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                menu.setVisible(false);
                createGameGUI(null);
            }
        });
        menu.add(newGame);

        dontShowMenu = false;
        JoinGUI joinGUI = new JoinGUI();
        joinGUI.setBounds(0, 0, this.size().width, this.size().height);
        joinGUI.setVisible(false);
        joinGUI.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                if (!dontShowMenu) {
                    menu.setVisible(true);
                }
            }
        });
        joinGUI.AddConnectListener(new JoinGUIEvent() {
            @Override
            public void onConnect(ServerAddress serverAddress) {
                joinGUI.setVisible(false);
                dontShowMenu = true;
                createGameGUI(serverAddress);
            }
        });
        this.add(joinGUI);

        joinGame = new JButton();
        joinGame.setText("Join game");
        joinGame.setSize(buttonWidth, buttonHeight);
        joinGame.setLocation((this.size().width - buttonWidth) / 2, 262);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                dontShowMenu = false;
                menu.setVisible(false);
                joinGUI.setVisible(true);
            }
        });
        menu.add(joinGame);

        SettingsGUI settingsGUI = new SettingsGUI();
        settingsGUI.setBounds(0, 0, this.size().width, this.size().height);
        settingsGUI.setVisible(false);
        settingsGUI.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                if (!dontShowMenu) {
                    menu.setVisible(true);
                }
            }
        });
        this.add(settingsGUI);

        settingsButton = new JButton();
        settingsButton.setText("Settings");
        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setLocation((this.size().width - buttonWidth) / 2, 302);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                menu.setVisible(false);
                settingsGUI.setVisible(true);
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
                System.exit(0);
            }
        });
        menu.add(exitButton);

        TesztGUI t = new TesztGUI();
        t.setVisible(false);
        t.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {

            }

            public void componentHidden(ComponentEvent e) {
                menu.setVisible(true);
            }
        });
        this.add(t);

        JButton tesztButton = new JButton();
        tesztButton.setText("teszt környezet");
        tesztButton.setBounds(10, 10, 150, 40);
        tesztButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                menu.setVisible(false);
                t.setVisible(true);
            }
        });
        menu.add(tesztButton);
        repaint();
    }

    private void createGameGUI(ServerAddress sa) {
        GameGUI gameGUI;
        if (sa != null) {
            gameGUI = new GameGUI(sa.getIP(), sa.getPort());
        } else {
            gameGUI = new GameGUI();
        }
        gameGUI.setBounds(0, 0, this.size().width, this.size().height);
        gameGUI.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                menu.setVisible(false);
            }

            public void componentHidden(ComponentEvent e) {
                menu.setVisible(true);
                remove(gameGUI);
            }
        });
        menu.setVisible(false);
        this.add(gameGUI);
    }
}
