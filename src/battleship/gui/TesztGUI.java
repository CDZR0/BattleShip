//TESZTRE
package battleship.gui;

import com.sun.deploy.panel.JSmartTextArea;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Csaba
 */
public class TesztGUI extends JPanel {

    JTextField area;
    JButton button;

    public TesztGUI() {
        setLayout(null);
        this.setSize(800, 600);
        area = new JTextField();
        area.setSize(100, 40);
        area.setLocation(0, 0);
        this.add(area);
        button = new JButton();
        button.setText("Send");
        button.setSize(100, 40);
        button.setLocation(0, 100);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("küld lenyomva:\t" + area.getText());

            }
        });
        this.add(button);
        repaint();
    }

}
