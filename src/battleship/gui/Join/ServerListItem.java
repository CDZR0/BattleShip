//Csaba
package battleship.gui.Join;

import battleship.Networking.ServerAddress;
import java.awt.Color;
import javafx.scene.Parent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nycs0
 */
public class ServerListItem extends JPanel {

    private ServerAddress serverAddress;

    public ServerListItem(ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
        setBackground(new Color(50, 105, 168));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel name = new JLabel();
        name.setText(serverAddress.getName());
        
        this.add(name);
    }
}
