//TESZTRE
package battleship.gui;

import battleship.Networking.Client;
import battleship.Networking.Server;
import battleship.Settings;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Csaba
 */
public class TesztGUI extends JPanel {

    JTextField sendTextBox, ipTextBox, portTextBox;
    JTextArea chatTextBox;
    JButton sendButton, backButton, serverToggleButton, clientToggleButton, clearChatButton;
    private Client client;
    private Thread clientThread, serverThread;
    private Server server;

    public TesztGUI() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        this.setSize(800, 600 - MenuGUI.WindowInsets);

        sendButton = new JButton();
        sendButton.setText("Send");
        sendButton.setSize(100, 40);
        sendButton.setLocation((this.size().width - sendButton.size().width) / 2, this.size().height - sendButton.size().height - 10);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("küld lenyomva:\t" + sendTextBox.getText());
                client.sendMessage(sendTextBox.getText());
                chatTextBox.append("[" + LocalTime.now() + "] " + sendTextBox.getText() + "\n");
            }
        });
        this.add(sendButton);

        sendTextBox = new JTextField();
        sendTextBox.setSize(300, 40);
        sendTextBox.setLocation((this.size().width - sendTextBox.size().width) / 2, this.size().height - sendTextBox.size().height - 20 - sendButton.size().height);
        this.add(sendTextBox);

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

        serverToggleButton = new JButton();
        serverToggleButton.setText("Server ON");
        serverToggleButton.setSize(100, 35);
        serverToggleButton.setLocation(this.size().width - serverToggleButton.size().width - 10, 10);
        serverToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (serverToggleButton.getText().equals("Server ON")) {
                    serverToggleButton.setText("Server OFF");
                    server = new Server(Settings.getPort());
                    serverThread = new Thread(server);
                    serverThread.start();
                } else {
                    serverToggleButton.setText("Server ON");
                    try {
                        server.close();
                    } catch (Exception e) {
                        System.out.println("Sikertelen server close():\n" + e.getMessage());
                    }
                }
            }
        });
        this.add(serverToggleButton);

        ipTextBox = new JTextField();
        ipTextBox.setSize(300, 35);
        ipTextBox.setText("IP");
        ipTextBox.setLocation(this.size().width - serverToggleButton.size().width - 10 - ipTextBox.size().width, 10);
        this.add(ipTextBox);

        clientToggleButton = new JButton();
        clientToggleButton.setText("Client ON");
        clientToggleButton.setSize(100, 35);
        clientToggleButton.setLocation(this.size().width - clientToggleButton.size().width - 10, 55);
        clientToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (clientToggleButton.getText().equals("Client ON")) {
                    clientToggleButton.setText("Client OFF");
                    client = new Client(ipTextBox.getText(), Integer.parseInt(portTextBox.getText()));
                    Thread clientThread0 = new Thread(client);
                    clientThread0.start();
                } else {
                    clientToggleButton.setText("Client ON");
                    client.close();
                }
            }
        });
        this.add(clientToggleButton);

        portTextBox = new JTextField();
        portTextBox.setSize(300, 35);
        portTextBox.setText("port");
        portTextBox.setLocation(this.size().width - serverToggleButton.size().width - 10 - portTextBox.size().width, 55);
        this.add(portTextBox);

        chatTextBox = new JTextArea();
        chatTextBox.setSize(700, 350);
        chatTextBox.setBackground(Color.DARK_GRAY);
        chatTextBox.setForeground(Color.WHITE);
        chatTextBox.setEnabled(false);
        chatTextBox.setLocation((this.size().width - chatTextBox.size().width) / 2, 100);
        this.add(chatTextBox);

        repaint();
    }
}
