//Csaba
package battleship.gui.Game;

import battleship.DataPackage.GameEndedStatus;
import battleship.Resources.Resources;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class InfoPanelGUI extends JPanel {

    JLabel infoText;
    boolean meTurn = false;
    boolean itsEnd = false;

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
        meTurn = value;
        repaint();
    }

    public void setGameEnded() {
        itsEnd = true;
        repaint();
    }
//    public void setGameEndedText(GameEndedStatus status) {
//        switch (status) {
//            case Win:
//                infoText.setText("You win!");
//                break;
//            case Defeat:
//                infoText.setText("Defeat!");
//                break;
//            default:
//                throw new AssertionError();
//        }
//    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        if (!itsEnd) {
            if (meTurn) {
                g2.setColor(new Color(50, 168, 82));
                int[] x = {0, 60, 0};
                int[] y = {0, 30, 60};
                g2.fillPolygon(x, y, 3);
            } else {
                g2.setColor(new Color(168, 50, 50));
                int[] x = {60, 0, 60};
                int[] y = {60, 30, 0};
                g2.fillPolygon(x, y, 3);
            }
        }
    }
}
