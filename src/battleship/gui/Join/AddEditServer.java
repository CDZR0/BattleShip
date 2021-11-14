/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui.Join;

import battleship.Networking.ServerAddress;
import battleship.Resources.Resources;
import battleship.Settings;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author nycs0
 */
public class AddEditServer extends JPanel {

    private JTextField nameText, ipText, portText;
    private JLabel nameLbl, ipLbl, portLbl;

    public AddEditServer() {
        this(new ServerAddress("", "", Settings.getPort()), true);
    }

    public AddEditServer(ServerAddress sa) {
        this(sa, false);
    }

    private AddEditServer(ServerAddress sa, boolean add) {
        setLayout(null);
        setBackground(Resources.BackgroundColor);
        Font font = new Font("", Font.BOLD, 12);
        nameText = new JTextField();
        nameText.setText(sa.getName());
        nameText.setLocation(110, 10);
        nameText.setSize(300, 35);
        nameText.setBackground(Resources.BackgroundColor);
        nameText.setFont(font);
        this.add(nameText);

        ipText = new JTextField();
        ipText.setText(sa.getIP());
        ipText.setLocation(110, 50);
        ipText.setSize(300, 35);
        ipText.setBackground(Resources.BackgroundColor);
        ipText.setFont(font);
        this.add(ipText);

        portText = new JTextField();
        portText.setText(sa.getPort() + "");
        portText.setLocation(110, 90);
        portText.setSize(50, 35);
        portText.setBackground(Resources.BackgroundColor);
        portText.setFont(font);
        this.add(portText);

        nameLbl = new JLabel();
        nameLbl.setText("Server name:");
        nameLbl.setSize(100, 35);
        nameLbl.setLocation(10, 10);
        this.add(nameLbl);

        ipLbl = new JLabel();
        ipLbl.setText("IP address:");
        ipLbl.setSize(100, 35);
        ipLbl.setLocation(10, 50);
        this.add(ipLbl);

        portLbl = new JLabel();
        portLbl.setText("Port:");
        portLbl.setSize(100, 35);
        portLbl.setLocation(10, 90);
        this.add(portLbl);

        JButton save, back;

        save = new JButton();
        save.setText("Save");
        save.setBounds(10, 200, 100, 35);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (add) {
                    try {
                        Settings.addServer(new ServerAddress(nameText.getText(), ipText.getText(), Integer.parseInt(portText.getText())));
                        Settings.WriteFile();
                        setVisible(false);
                    } catch (Exception e) {
                        if (e.getMessage().equals("SZERVER NÉV MÁR LÉTEZIK")) {
                            nameText.setText(nameText.getText() + " (1)");
                        }
                    }
                } else {
                    try {
                        Settings.editServer(sa.getName(), new ServerAddress(nameText.getText(), ipText.getText(), Integer.parseInt(portText.getText())));
                        Settings.WriteFile();
                        setVisible(false);
                    } catch (Exception e) {
                        if (e.getMessage().equals("SZERVER NÉV MÁR LÉTEZIK")) {
                            nameText.setText(nameText.getText() + " (1)");
                        }
                    }
                }
            }
        });
        this.add(save);

        back = new JButton();
        back.setText("Back");
        back.setBounds(10, 245, 100, 35);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                setVisible(false);
            }
        });
        this.add(back);
    }

}
