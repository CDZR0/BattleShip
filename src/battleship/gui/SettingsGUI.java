package battleship.gui;

import battleship.Resources.Resources;
import battleship.Utils.Settings;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SettingsGUI extends JPanel {
    private JLabel labelSaved;

    public SettingsGUI() {
        setLayout(null);
        this.setSize(800, 600 - MenuGUI.WindowInsets);
        setBackground(Resources.BackgroundColor);

        JLabel title = new JLabel();
        title.setBackground(Color.red);
        title.setBounds(10, 10, 10, 10);
        this.add(title);
        
        JButton backButton = new JButton();
        backButton.setText("Back to menu");
        backButton.setSize(115, 35);
        backButton.setLocation(10, 10);
        backButton.addActionListener((java.awt.event.ActionEvent ae) -> {
            setVisible(false);
        });
        this.add(backButton);
        
        JLabel labelPort = new JLabel();
        labelPort.setText("Port:");
        labelPort.setSize(115,35);
        labelPort.setLocation(360-(115/2),250);
        this.add(labelPort);
        
        JTextField textPort = new JTextField();
        textPort.setText(String.valueOf(Settings.getPort()));
        textPort.setSize(115, 35);
        textPort.setLocation(400-(115/2),250);
        textPort.addActionListener((java.awt.event.ActionEvent ae) -> {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    labelSaved.setVisible(false);
                }
            };
            labelSaved.setVisible(true);
            Timer timer = new Timer();
            timer.schedule(task, 2000);
            int parsedInt = 25564;
            try {
                parsedInt = Integer.parseInt(textPort.getText());
            }
            catch (NumberFormatException ex) { }
            Settings.setPort(parsedInt);
            Settings.WriteFile();
            
        });
        this.add(textPort);
        
        labelSaved = new JLabel("Settings saved.", JTextField.CENTER);
        labelSaved.setSize(240, 35);
        
        labelSaved.setLocation((size().width-labelSaved.getSize().width)/2, 500);
        labelSaved.setVisible(false);
        labelSaved.setFont(new Font("Dialog", Font.BOLD, 20));  
        this.add(labelSaved);
        
        
    }
}
