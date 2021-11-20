//Csaba
package battleship.gui;

import battleship.Events.ChatGUIEvent;
import battleship.Resources.Resources;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author nycs0
 */
public class ChatGUI extends JPanel {

    private List<ChatGUIEvent> listeners;
    JTextPane chatTextBox;
    JTextField sendTextBox;
    int prefferedHeight;
    int prefferedWidth;
    List<String> chat;

    public ChatGUI() {
        prefferedHeight = 15;
        chat = new ArrayList<>();
        listeners = new ArrayList<>();
        setLayout(null);
        chatTextBox = new JTextPane();
        chatTextBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        chatTextBox.setBackground(Resources.BackgroundColor);
        chatTextBox.setForeground(Color.WHITE);
        chatTextBox.setEditable(false);
        chatTextBox.setAutoscrolls(true);
        chatTextBox.setSize(350, 150);
        chatTextBox.setPreferredSize(new Dimension(330, prefferedHeight));
        chatTextBox.setLocation(0, 0);
        this.add(chatTextBox);

        JScrollPane scroll = new JScrollPane(chatTextBox);
        scroll.setBounds(0, 0, chatTextBox.size().width, chatTextBox.size().height);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);

        sendTextBox = new JTextField();
        sendTextBox.setLayout(new FlowLayout(FlowLayout.LEADING));
        sendTextBox.setSize(350, 35);
        sendTextBox.setLocation(0, 150);
        sendTextBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (sendTextBox.getText().equals("")) {
                    return;
                }
                for (ChatGUIEvent listener : listeners) {
                    listener.onSendMessage(sendTextBox.getText());
                }
                sendTextBox.setText("");
            }
        });
        this.add(sendTextBox);
    }

    public void addMessage(String sender, String message) {
        chat.add("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "] [" + sender + "] : " + message + "\n");
        if (chat.size() > 30) {
            chat.remove(0);
        }
        String chatMessage = "";
        for (String string : chat) {
            chatMessage += string;
        }
        chatTextBox.setText(chatMessage);
        //chatTextBox.append("[" + LocalTime.now() + "] [" + sender + "] : " + message + "\n");
        prefferedHeight += prefferedHeight;
        chatTextBox.setPreferredSize(new Dimension(330, prefferedHeight));
    }

    public void addSendMessageListener(ChatGUIEvent listener) {
        listeners.add(listener);
    }
}
