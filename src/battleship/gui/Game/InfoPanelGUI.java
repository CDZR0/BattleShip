//Csaba
package battleship.gui.Game;

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

    JLabel turn;

    public InfoPanelGUI() {
        setLayout(null);
        setBackground(Resources.BackgroundColor);
        //setBackground(Color.yellow);
        turn = new JLabel();
        turn.setText("");
        turn.setSize(800, 50);
        turn.setFont(new Font("Dialog", Font.BOLD, 30));
        turn.setLocation(0, 140);
        turn.setHorizontalAlignment(JLabel.CENTER);
        turn.setVerticalTextPosition(JLabel.CENTER);
        turn.setHorizontalTextPosition(JLabel.CENTER);
        this.add(turn);

        repaint();
    }

    public void setTurnText(boolean value) {
        turn.setText(value ? "Its your turn" : "");
    }
}
