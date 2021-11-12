//TESZTRE
package battleship.gui;

import battleship.Networking.Client;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Csaba
 */
public class TesztGUI extends JPanel {

    JTextField textbox;
    JButton sendButton, backButton;

    public TesztGUI() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        this.setSize(800, 600);
        Client client0 = new Client();
        Thread clientThread0 = new Thread(client0);
        clientThread0.start();
        textbox = new JTextField();
        textbox.setSize(300, 40);
        textbox.setLocation((this.size().width - textbox.size().width) / 2, 30);
        this.add(textbox);
        sendButton = new JButton();
        sendButton.setText("Send");
        sendButton.setSize(100, 40);
        sendButton.setLocation((this.size().width - sendButton.size().width) / 2, 80);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("küld lenyomva:\t" + textbox.getText());
                client0.sendMessage(textbox.getText());
            }
        });
        this.add(sendButton);

        backButton = new JButton();
        backButton.setText("Back");
        backButton.setSize(100, 35);
        backButton.setLocation(10, 10);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                setVisible(false);
            }
        });
        this.add(backButton);

        repaint();
    }
}
