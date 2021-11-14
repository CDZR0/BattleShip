//Csaba
package battleship.gui;

import battleship.Resources.Resources;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nycs0
 */
public class SettingsGUI extends JPanel {

    public SettingsGUI() {
        setBackground(Resources.BackgroundColor);

        JLabel title = new JLabel();
        title.setBackground(Color.red);
        title.setBounds(10, 10, 10, 10);
        this.add(title);
    }
}
