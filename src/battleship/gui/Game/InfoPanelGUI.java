//Csaba
package battleship.gui.Game;

import battleship.DataPackage.GameEndedStatus;
import battleship.Resources.Resources;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class InfoPanelGUI extends JPanel {

    JLabel infoText;

    public InfoPanelGUI() {
        setLayout(null);
        setBackground(Resources.BackgroundColor);
        //setBackground(Color.yellow);
        infoText = new JLabel();
        infoText.setText("");
        infoText.setSize(350, 50);
        infoText.setFont(new Font("Dialog", Font.BOLD, 30));
        infoText.setLocation(0, 140);
        infoText.setHorizontalAlignment(JLabel.CENTER);
        infoText.setVerticalTextPosition(JLabel.CENTER);
        infoText.setHorizontalTextPosition(JLabel.CENTER);
        this.add(infoText);

        repaint();
    }

    public void setTurnText(boolean value) {
        infoText.setText(value ? "Its your turn" : "");
    }

    public void setGameEndedText(GameEndedStatus status) {
        switch (status) {
            case Win:
                infoText.setText("You win!");
                break;
            case Defeat:
                infoText.setText("Defeat!");
                break;
            default:
                throw new AssertionError();
        }
    }
}
