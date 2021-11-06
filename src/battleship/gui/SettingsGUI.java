//Csaba
package battleship.gui;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nycs0
 */
public class SettingsGUI extends JPanel {

    public SettingsGUI() {
        setBackground(Color.yellow);

        JLabel title = new JLabel();
        title.setBackground(Color.red);
        title.setBounds(10, 10, 10, 10);
        this.add(title);
    }
}
