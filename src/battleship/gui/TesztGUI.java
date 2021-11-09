//TESZTRE
package battleship.gui;
import battleship.Networking.Client;
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
    JButton button;

    public TesztGUI() {
        setLayout(null);
        this.setSize(800, 600);
        Client client0 = new Client();
        Thread clientThread0 = new Thread(client0);
        clientThread0.start();
        textbox = new JTextField();
        textbox.setSize(100, 40);
        textbox.setLocation(0, 0);
        this.add(textbox);
        button = new JButton();
        button.setText("Send");
        button.setSize(100, 40);
        button.setLocation(0, 100);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("küld lenyomva:\t" + textbox.getText());
                client0.sendMessage(textbox.getText());
            }
        });
        this.add(button);
        repaint();
    }

}
