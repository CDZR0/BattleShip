//Csaba
package battleship.gui;

import battleship.Networking.ServerAddress;
import battleship.Settings;
import battleship.gui.Join.ServerListItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author nycs0
 */
public class JoinGUI extends JPanel {

    public JoinGUI() {
        //zöld 50, 168, 82

        setLayout(null);
        this.setSize(800, 600);
        setBackground(new Color(50, 105, 168));

        JButton backButton = new JButton();
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

        JPanel segedPanel = new JPanel();
        segedPanel.setLayout(null);
        segedPanel.setSize(0, 0);
        segedPanel.setLocation(0, 0);
        segedPanel.setBackground(Color.yellow);
        //segedPanel.setPreferredSize(new Dimension(0,5000));

        JScrollPane listPanel = new JScrollPane(segedPanel);
        listPanel.getVerticalScrollBar().setPreferredSize(new Dimension(30, 0));
        listPanel.getVerticalScrollBar().setUnitIncrement(20);
        listPanel.setBounds(0, 80, this.size().width, this.size().height-200);
        this.add(listPanel);

        //listPanel.setBackground(Color.BLACK);
        listPanel.setAutoscrolls(true);
        for (int i = 0; i < Settings.getServers().size(); i++) {
            System.out.println(Settings.getServers().get(i));
            ServerListItem sli = new ServerListItem(Settings.getServers().get(i));
            sli.setSize(this.size().width, 60);
            sli.setLocation(0, 60 * i);
            segedPanel.add(sli);
            segedPanel.setPreferredSize(new Dimension(0,60*(i+1)));
        }
    }
}
