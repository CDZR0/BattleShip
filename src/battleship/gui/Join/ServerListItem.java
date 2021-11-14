//Csaba
package battleship.gui.Join;

import battleship.Networking.ServerAddress;
import battleship.Resources.Resources;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setBackground(Resources.BackgroundColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Entered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Exited();
            }
        });

        JLabel name = new JLabel();
        name.setText(serverAddress.getName());
        this.add(name);
    }

    public ServerAddress getServerAddress() {
        return serverAddress;
    }

    public void Select() {
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public void UnSelect() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void Entered() {
        int darkeningLevel = -32;

        Color color = new Color(
                getBackground().getRed() + darkeningLevel < 0 ? 0 : getBackground().getRed() + darkeningLevel,
                getBackground().getGreen() + darkeningLevel < 0 ? 0 : getBackground().getGreen() + darkeningLevel,
                getBackground().getBlue() + darkeningLevel < 0 ? 0 : getBackground().getBlue() + darkeningLevel
        );
        setBackground(color);
    }

    private void Exited() {
        int darkeningLevel = 32;
        Color color = new Color(
                getBackground().getRed() + darkeningLevel > 256 ? 255 : getBackground().getRed() + darkeningLevel,
                getBackground().getGreen() + darkeningLevel > 256 ? 255 : getBackground().getGreen() + darkeningLevel,
                getBackground().getBlue() + darkeningLevel > 256 ? 255 : getBackground().getBlue() + darkeningLevel
        );
        setBackground(color);
    }
}
