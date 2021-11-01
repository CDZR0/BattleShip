//Csaba
package battleship.gui.Game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class ShipSelecterGUI extends JPanel {

    int selectedShipSize;
    boolean shipPlaceHorizontal;
    int[] shipDB;
    JPanel[] ens;

    public ShipSelecterGUI() {
        setLayout(null);
        setSize(700, 100);
        setBackground(Color.CYAN);

        int shipTypeNumbers = 4;
        selectedShipSize = shipTypeNumbers;
        shipPlaceHorizontal = true;
        shipDB = new int[shipTypeNumbers];
        ens = new JPanel[shipTypeNumbers];

        int enWidth = size().width / shipTypeNumbers;
        int enHeight = size().height;
        for (int i = 0; i < shipTypeNumbers; i++) {
            shipDB[i] = shipTypeNumbers - i;
            JPanel egynegyed = new JPanel();
            egynegyed.setName("" + i);
            egynegyed.setSize(enWidth, enHeight);
            egynegyed.setLocation(i * enWidth, 0);
            egynegyed.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            egynegyed.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectedShipSize = Integer.parseInt(egynegyed.getName()) + 1;
                    System.out.println("Selected size: " + selectedShipSize);
                }
            });
            add(egynegyed);
            ens[i] = egynegyed;

            JLabel felirat = new JLabel();
            felirat.setText("1x" + (i + 1) + ": " + shipDB[i] + "db");
            egynegyed.add(felirat);

        }
    }

    public void LerakTablara(int size) {
        shipDB[size - 1]--;
        if (shipDB[size - 1] == 0) {
            ens[size - 1].setEnabled(false);
            System.out.println("Disabled: " + size);
        }
    }

    public void FelveszTablarol(int size) {

    }
}
