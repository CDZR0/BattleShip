//Csaba
package battleship.gui;

import battleship.Resources.Resources;
import battleship.Settings;
import battleship.gui.Join.AddEditServer;
import battleship.gui.Join.ServerListItem;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import battleship.Events.JoinGUIEvent;
import java.util.ArrayList;

/**
 *
 * @author nycs0
 */
public class JoinGUI extends JPanel {

    private ServerListItem selectedServer;
    private JPanel listPanel, segedServersListPanel;
    JButton connectButton, addButton, editButton, deleteButton;
    private List<JoinGUIEvent> listeners;

    public JoinGUI() {
        //zöld 50, 168, 82

        setLayout(null);
        this.setSize(800, 600 - MenuGUI.WindowInsets);
        setBackground(Resources.BackgroundColor);
        
        listeners = new ArrayList<>();
        
        JButton backButton = new JButton();
        backButton.setText("Back to menu");
        backButton.setSize(115, 35);
        backButton.setLocation(10, 10);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                setVisible(false);
            }
        });
        this.add(backButton);

        listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBackground(Resources.BackgroundColor);
        listPanel.setBounds(0, 55, this.size().width, this.size().height - 55);
        listPanel.addComponentListener(new ComponentAdapter() {
            public void componentShow(ComponentEvent e) {
                loadList();
            }

            public void componentHidden(ComponentEvent e) {
                loadList();
            }
        });
        this.add(listPanel);

        segedServersListPanel = new JPanel();
        segedServersListPanel.setLayout(null);
        segedServersListPanel.setLocation(0, 0);
        segedServersListPanel.setBackground(Resources.BackgroundColor);

        JScrollPane listServerPanel = new JScrollPane(segedServersListPanel);
        listServerPanel.getVerticalScrollBar().setPreferredSize(new Dimension(30, 0));
        listServerPanel.getVerticalScrollBar().setUnitIncrement(20);
        listServerPanel.setBounds(0, 0, listPanel.size().width, listPanel.size().height - 55);
        listServerPanel.setAutoscrolls(true);
        listPanel.add(listServerPanel);

        JPanel sp = new JPanel();
        sp.setSize(listPanel.getSize().width, 55);
        sp.setLocation(0, listPanel.size().height - 55);
        sp.setBackground(Resources.BackgroundColor);
        listPanel.add(sp);

        connectButton = new JButton();
        connectButton.setText("Connect to server");
        connectButton.setSize(150, 35);
        connectButton.setLocation(110, listPanel.size().height - 45);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                setVisible(false);
                ConnectServer();
            }
        });
        sp.add(connectButton);

        addButton = new JButton();
        addButton.setText("Add server");
        addButton.setSize(100, 35);
        addButton.setLocation(10, listPanel.size().height - 45);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                listPanel.setVisible(false);
                ShowAddEdit();
            }
        });
        sp.add(addButton);

        editButton = new JButton();
        editButton.setText("Edit server");
        editButton.setSize(100, 35);
        editButton.setLocation(10, listPanel.size().height - 45);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                listPanel.setVisible(false);
                ShowAddEdit(selectedServer);
            }
        });
        sp.add(editButton);

        deleteButton = new JButton();
        deleteButton.setText("Delete server");
        deleteButton.setSize(100, 35);
        deleteButton.setLocation(10, listPanel.size().height - 45);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                listPanel.setVisible(false);
                Settings.deleteServer(selectedServer.getServerAddress());
                Settings.WriteFile();
                listPanel.setVisible(true);
            }
        });
        sp.add(deleteButton);

        loadList();
    }

    private void ShowAddEdit() {
        ShowAddEdit(null);
    }

    private void ShowAddEdit(ServerListItem selectedServer) {
        AddEditServer addEditServer;
        if (selectedServer != null) {
            addEditServer = new AddEditServer(selectedServer.getServerAddress());
        } else {
            addEditServer = new AddEditServer();
        }
        addEditServer.setBounds(0, 55, size().width, size().height - 55);
        addEditServer.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                remove(addEditServer);
                loadList();
                listPanel.setVisible(true);
            }
        });
        this.add(addEditServer);
    }

    private void ConnectServer() {
        System.out.println("Connecting to server: " + selectedServer.getServerAddress().getName());
        for (JoinGUIEvent listener : listeners) {
            listener.onConnect(selectedServer.getServerAddress());
        }
    }

    public void AddConnectListener(JoinGUIEvent listener){
        listeners.add(listener);
    }
    
    private void loadList() {
        selectedServer = null;
        connectButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        segedServersListPanel.removeAll();
        for (int i = 0; i < Settings.getServers().size(); i++) {
            ServerListItem sli = new ServerListItem(Settings.getServers().get(i));
            sli.setSize(this.size().width, 60);
            sli.setLocation(0, 60 * i);
            sli.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    for (Component component : segedServersListPanel.getComponents()) {
                        if (component instanceof ServerListItem) {
                            ((ServerListItem) component).UnSelect();
                        }
                    }

                    selectedServer = sli;
                    connectButton.setEnabled(true);
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    sli.Select();
                    if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        ConnectServer();
                    }
                }
            });
            segedServersListPanel.add(sli);
            segedServersListPanel.setPreferredSize(new Dimension(0, 60 * (i + 1)));
        }
    }
}
