//TESZTRE
package battleship.gui;

import battleship.DataPackage.ChatData;
import battleship.DataPackage.GameEndedStatus;
import battleship.Events.ClientEvent;
import battleship.Logic.CellStatus;
import battleship.Networking.Client;
import battleship.Networking.Server;
import battleship.Utils.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

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
                client.sendMessage(new ChatData(client.ID, sendTextBox.getText()));
                //send(sendTextBox.getText(), "SendButton");
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
                send("lenyomva", "serverToggleButton");
                if (serverToggleButton.getText().equals("Server ON")) {
                    try {
                        server = new Server(Settings.getPort());
                        serverThread = new Thread(server);
                        serverThread.start();
                        serverToggleButton.setText("Server OFF");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
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
        ipTextBox.setText(Settings.getIP());
        ipTextBox.setLocation(this.size().width - serverToggleButton.size().width - 10 - ipTextBox.size().width, 10);
        this.add(ipTextBox);

        clientToggleButton = new JButton();
        clientToggleButton.setText("Client ON");
        clientToggleButton.setSize(100, 35);
        clientToggleButton.setLocation(this.size().width - clientToggleButton.size().width - 10, 55);
        clientToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                send("lenyomva", "clientToggleButton");
                if (clientToggleButton.getText().equals("Client ON")) {
                    try {
                        client = new Client(ipTextBox.getText(), Integer.parseInt(portTextBox.getText()));
                        client.addClientEventListener(new ClientEvent() {
                            @Override
                            public void onMessageReceived(int sender, String message) {
                                //System.out.println("get message: " + message);
                                send(message, "client event");
                            }

                            @Override
                            public void onYourTurn() {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onEnemyHitMe(int x, int y) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onMyHit(int i, int j, CellStatus status) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onGameEnded(GameEndedStatus status) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onJoinedEnemy() {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                        clientThread = new Thread(client);
                        clientThread.start();
                        clientToggleButton.setText("Client OFF");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    clientToggleButton.setText("Client ON");
                    client.close();
                }
            }
        });
        this.add(clientToggleButton);

        portTextBox = new JTextField();
        portTextBox.setSize(300, 35);
        portTextBox.setText(Settings.getPort() + "");
        portTextBox.setLocation(this.size().width - serverToggleButton.size().width - 10 - portTextBox.size().width, 55);
        this.add(portTextBox);

        chatTextBox = new JTextArea();
        chatTextBox.setSize(700, 350);
        chatTextBox.setBackground(Color.DARK_GRAY);
        chatTextBox.setForeground(Color.WHITE);
        chatTextBox.setEditable(false);
        chatTextBox.setAutoscrolls(true);
        chatTextBox.setPreferredSize(new Dimension(680, 5000));
        chatTextBox.setLocation(0, 0);
        this.add(chatTextBox);

        JScrollPane scroll = new JScrollPane(chatTextBox);
        scroll.setBounds((this.size().width - chatTextBox.size().width) / 2, 100, chatTextBox.size().width, chatTextBox.size().height);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
        repaint();
    }

    private void send(String text, String sender) {
        chatTextBox.append("[" + LocalTime.now() + "] [" + sender + "] : " + text + "\n");
    }
}
