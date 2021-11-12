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

    private final int shipTypeNumbers = 4;
    int selectedShipSize;
    boolean shipPlaceHorizontal;
    ShipInfoPanel[] shipInfoPanels;
    private List<ShipSelectorEvent> listeners;
    JButton doneButton;

    public ShipSelecterGUI() {
        setLayout(null);
        setSize(700, 135);

        JPanel felso = new JPanel();
        felso.setLayout(null);
        felso.setBounds(0, 0, size().width, 35);
        felso.setBackground(new Color(50, 105, 168));
        this.add(felso);

        JPanel also = new JPanel();
        also.setBounds(0, 35, size().width, 100);
        also.setLayout(null);
        also.setBackground(new Color(66, 121, 184));
        this.add(also);

        JButton shipHorizontal = new JButton();
        shipHorizontal.setText("Horizontal");
        shipHorizontal.setBounds(20, 0, 100, 35);
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
                    listener.onSelectDirection(shipPlaceHorizontal);
                }
            }
        });
        felso.add(shipHorizontal);

        JButton clearBoard = new JButton();
        clearBoard.setText("Clear board");
        clearBoard.setBounds(140, 0, 100, 35);
        clearBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                resetShips();
                for (ShipSelectorEvent listener : listeners) {
                    listener.onClearBoard();
                }
            }
        });
        felso.add(clearBoard);

        JButton randomPlaceShips = new JButton();
        randomPlaceShips.setText("Randomize ships");
        randomPlaceShips.setBounds(260, 0, 140, 35);
        randomPlaceShips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                setShipsPieceTo(0);
                for (ShipSelectorEvent listener : listeners) {
                    listener.onPlaceRandomShips();
                }
            }
        });
        felso.add(randomPlaceShips);

        doneButton = new JButton();
        doneButton.setText("Done");
        doneButton.setBounds(felso.size().width - 120, 0, 100, 35);
        doneButton.setEnabled(false);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                for (ShipSelectorEvent listener : listeners) {
                    listener.onDone();
                }
                setVisible(false);
            }
        });
        felso.add(doneButton);

        listeners = new ArrayList<>();

        selectedShipSize = shipTypeNumbers;
        shipPlaceHorizontal = true;
        shipInfoPanels = new ShipInfoPanel[shipTypeNumbers];

        int enWidth = size().width / shipTypeNumbers;
        int enHeight = size().height;
        for (int i = 0; i < shipTypeNumbers; i++) {
            ShipInfoPanel infoPanel = new ShipInfoPanel(i + 1, shipTypeNumbers - i);
            infoPanel.setSize(enWidth, enHeight);
            infoPanel.setLocation(i * enWidth, 0);
            infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (infoPanel.isEnabled()) {
                        SelectShip(infoPanel);
                    }
                }
            });
            also.add(infoPanel);
            shipInfoPanels[i] = infoPanel;

        }
    }

    private void setShipsPieceTo(int number) {
        for (ShipInfoPanel shipInfoPanel : shipInfoPanels) {
            shipInfoPanel.SetPiece(number);
        }
    }

    private void resetShips() {
        for (int i = 0; i < shipInfoPanels.length; i++) {
            shipInfoPanels[i].SetPiece(shipTypeNumbers - i);
        }
    }

    public void setCanDoneButton(boolean value) {
        doneButton.setEnabled(value);
    }

    public void addShipSelectorListener(ShipSelectorEvent listener) {
        listeners.add(listener);
    }

    public void LerakTablara(int size) {
        shipInfoPanels[size - 1].decrease();
        if (shipInfoPanels[size - 1].getPiece() == 0) {
            shipInfoPanels[size - 1].setEnabled(false);
            automaticSelectShip();
        }

    }

    public void FelveszTablarol(int size) {
        shipInfoPanels[size - 1].increase();
        if (shipInfoPanels[size - 1].getPiece() == shipInfoPanels.length) {
            shipInfoPanels[size - 1].setEnabled(false);
            SelectShip(shipInfoPanels[size - 1]);
        }
        automaticSelectShip();
    }

    private void SelectShip(ShipInfoPanel infoPanel) {
        for (ShipInfoPanel shipInfoPanel : shipInfoPanels) {
            shipInfoPanel.UnSelect();
        }
        infoPanel.Select();
        for (ShipSelectorEvent listener : listeners) {
            listener.onSelectShip(infoPanel.shipSize);
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
            listener.onRanOutOfShips();
        }
    }

}

class ShipInfoPanel extends JPanel {

    private final JLabel felirat;
    final int shipSize;
    private int piece;

    public ShipInfoPanel(int shipSize, int piece) {
        //setLayout(null);
        setBackground(new Color(66, 121, 184));
        this.shipSize = shipSize;
        this.piece = piece;
        felirat = new JLabel("1x" + (shipSize) + ": " + piece + "db");
        add(felirat);
    }

    protected void SetPiece(int piece) {
        this.piece = piece;
        felirat.setText("1x" + (shipSize) + ": " + piece + "db");
        if (piece > 0) {
            setEnabled(true);
        } else {
            setEnabled(false);
            UnSelect();
        }
    }

    public int getPiece() {
        return this.piece;
    }

    public void Select() {
        setBackground(new Color(34, 89, 152));
    }

    public void UnSelect() {
        setBackground(new Color(66, 121, 184));
    }

    public void decrease() {
        piece--;
        felirat.setText("1x" + (shipSize) + ": " + piece + "db");
        if (piece == 0) {
            setEnabled(false);
            UnSelect();
        }
    }

    public void increase() {
        piece++;
        felirat.setText("1x" + (shipSize) + ": " + piece + "db");
        setEnabled(true);
    }

}
