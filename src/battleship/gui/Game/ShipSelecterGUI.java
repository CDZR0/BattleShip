//Csaba
package battleship.gui.Game;

import battleship.Events.ShipSelectorEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class ShipSelecterGUI extends JPanel {

    int selectedShipSize;
    boolean shipPlaceHorizontal;
    //int[] shipDB;
    ShipInfoPanel[] shipInfoPanels;
    private List<ShipSelectorEvent> listeners;

    public ShipSelecterGUI() {
        setLayout(null);
        setSize(700, 135);
        setBackground(Color.CYAN);

        JPanel felso = new JPanel();
        felso.setBounds(0, 0, size().width, 35);
        this.add(felso);

        JPanel also = new JPanel();
        also.setBounds(0, 35, size().width, 100);
        also.setLayout(null);
        this.add(also);

        JButton shipHorizontal = new JButton();
        shipHorizontal.setText("Horizontal");
        shipHorizontal.setBounds((size().width - 100) / 2, 65, 100, 35);
        shipHorizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (shipHorizontal.getText().equals("Horizontal")) {
                    shipHorizontal.setText("Vertical");
                    shipPlaceHorizontal = false;
                } else {
                    shipHorizontal.setText("Horizontal");
                    shipPlaceHorizontal = true;
                }
                for (ShipSelectorEvent listener : listeners) {
                    listener.onSelect(selectedShipSize, shipPlaceHorizontal);
                }
            }
        });
        felso.add(shipHorizontal);

        listeners = new ArrayList<>();

        int shipTypeNumbers = 4;
        selectedShipSize = shipTypeNumbers;
        shipPlaceHorizontal = true;
        //shipDB = new int[shipTypeNumbers];
        shipInfoPanels = new ShipInfoPanel[shipTypeNumbers];

        int enWidth = size().width / shipTypeNumbers;
        int enHeight = size().height;
        for (int i = 0; i < shipTypeNumbers; i++) {
            //shipDB[i] = shipTypeNumbers - i;
            ShipInfoPanel infoPanel = new ShipInfoPanel(i + 1, shipTypeNumbers - i);
            infoPanel.setSize(enWidth, enHeight);
            infoPanel.setLocation(i * enWidth, 0);
            infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    SelectShip(infoPanel);
                }
            });
            also.add(infoPanel);
            shipInfoPanels[i] = infoPanel;

        }
    }

    public void addShipSelectorListener(ShipSelectorEvent listener) {
        listeners.add(listener);
    }

    public void LerakTablara(int size) {
        shipInfoPanels[size - 1].decrease();
        System.out.println("Now: " + shipInfoPanels[size - 1].getPiece());
        if (shipInfoPanels[size - 1].getPiece() == 0) {
            shipInfoPanels[size - 1].setEnabled(false);
            System.out.println("Disabled: " + size);
            automaticSelectShip();
        }

    }

    public void FelveszTablarol(int size) {
        shipInfoPanels[size - 1].increase();
        System.out.println("Now: " + shipInfoPanels[size - 1].getPiece());
        if (shipInfoPanels[size - 1].getPiece() == shipInfoPanels.length) {
            shipInfoPanels[size - 1].setEnabled(false);
            System.out.println("Disabled: " + size);
            SelectShip(shipInfoPanels[size - 1]);
        }
        automaticSelectShip();
    }

    private void SelectShip(ShipInfoPanel infoPanel) {
        //System.out.println("Selected size: " + selectedShipSize);
        for (ShipSelectorEvent listener : listeners) {
            listener.onSelect(infoPanel.shipSize, shipPlaceHorizontal);
        }
    }

    public void automaticSelectShip() {
        for (ShipInfoPanel shipInfoPanel : shipInfoPanels) {
            if (shipInfoPanel.getPiece() > 0) {
                SelectShip(shipInfoPanel);
            }
        }
        int i = shipInfoPanels.length - 1;
        while (i >= 0) {
            if (shipInfoPanels[i].getPiece() > 0) {
                SelectShip(shipInfoPanels[i]);
                return;
            }
            --i;
        }
        for (ShipSelectorEvent listener : listeners) {
            listener.onRanOutOfShips(true);
        }
    }

}

class ShipInfoPanel extends JPanel {

    private final JLabel felirat;
    final int shipSize;
    private int piece;

    public ShipInfoPanel(int shipSize, int piece) {
        //setLayout(null);
        this.shipSize = shipSize;
        this.piece = piece;
        felirat = new JLabel("1x" + (shipSize) + ": " + piece + "db");
        add(felirat);
    }

    public int getPiece() {
        return this.piece;
    }

    public void decrease() {
        piece--;
        felirat.setText("1x" + (shipSize) + ": " + piece + "db");
        if (piece==0) {
            setVisible(false);
        }
    }

    public void increase() {
        piece++;
        felirat.setText("1x" + (shipSize) + ": " + piece + "db");
        setVisible(true);
    }

}
