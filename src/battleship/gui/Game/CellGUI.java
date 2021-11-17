//Csaba
package battleship.gui.Game;

import battleship.Logic.CellStatus;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class CellGUI extends JPanel {

    private final Color backgroundColor = new Color(82,137,200);
    private final Color shipColor = new Color(18,73,136);
    private final Color waterColor = new Color(0, 0, 0);
    private final int i;
    private final int j;
    private CellStatus cellStatus;

    public CellGUI(int i, int j) {
        this.i = i;
        this.j = j;
        cellStatus = CellStatus.Empty;
        setBackground(backgroundColor);
        setName("Button: " + i + ":" + j);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setCell(CellStatus cellStatus) {
        switch (cellStatus) {
            case Empty:
                this.cellStatus = cellStatus;
                setBackground(backgroundColor);
                break;
            case EmptyHit:
                this.cellStatus = cellStatus;
                setBackground(waterColor);
                break;
            case NearShip:
                this.cellStatus = cellStatus;
                setBackground(waterColor);
                break;
            case Ship:
                placeShip();
                //paintX(this.getGraphics());
                break;
            case ShipHit:
                this.cellStatus = cellStatus;
                this.paintComponent(this.getGraphics());
                break;
            case ShipSunk:
                this.cellStatus = cellStatus;
                break;
            default:
                throw new AssertionError();
        }
        repaint();
    }

    private void placeShip() {
        if (cellStatus == CellStatus.Empty) {
            cellStatus = CellStatus.Ship;
            setBackground(shipColor);
        }
    }

    public void select() {
        if (cellStatus == CellStatus.Empty) {
//            setBackground(selectedColor);
            setColorSelected();
        }
    }

    public void unSelect() {
        if (cellStatus == CellStatus.Empty) {
            //setBackground(backgroundColor);
            SetColorUnSelected();
        }
    }

    public CellStatus getStatus() {
        return cellStatus;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isIJ(int i, int j) {
        return this.i == i && this.j == j;
    }

    private void setColorSelected() {
        int darkeningLevel = -32;

        Color color = new Color(
                getBackground().getRed() + darkeningLevel < 0 ? 0 : getBackground().getRed() + darkeningLevel,
                getBackground().getGreen() + darkeningLevel < 0 ? 0 : getBackground().getGreen() + darkeningLevel,
                getBackground().getBlue() + darkeningLevel < 0 ? 0 : getBackground().getBlue() + darkeningLevel
        );
        setBackground(color);
    }

    private void SetColorUnSelected() {
        int darkeningLevel = 32;
        Color color = new Color(
                getBackground().getRed() + darkeningLevel > 256 ? 255 : getBackground().getRed() + darkeningLevel,
                getBackground().getGreen() + darkeningLevel > 256 ? 255 : getBackground().getGreen() + darkeningLevel,
                getBackground().getBlue() + darkeningLevel > 256 ? 255 : getBackground().getBlue() + darkeningLevel
        );
        setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        switch (cellStatus) {
            case Empty:
                break;
            case EmptyHit:
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(2));
                for (int k = 0; k < 6; k++) {
                    int[] x = {0, 5, 10, 15, 20, 25, 30};
                    int[] y = {0 + k * 5, 5 + k * 5, 0 + k * 5, 5 + k * 5, 0 + k * 5, 5 + k * 5, 0 + k * 5};
                    for (int i = 1; i < x.length; i++) {
                        g2.draw(new Line2D.Float(x[i - 1], y[i - 1], x[i], y[i]));
                    }
                }
                break;
            case NearShip:
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(2));
                for (int k = 0; k < 6; k++) {
                    int[] x = {0, 5, 10, 15, 20, 25, 30};
                    int[] y = {0 + k * 5, 5 + k * 5, 0 + k * 5, 5 + k * 5, 0 + k * 5, 5 + k * 5, 0 + k * 5};
                    for (int i = 1; i < x.length; i++) {
                        g2.draw(new Line2D.Float(x[i - 1], y[i - 1], x[i], y[i]));
                    }
                }
                break;
            case Ship:
                break;
            case ShipHit:
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(5));
                g2.draw(new Line2D.Float(0, 0, 30, 30));
                g2.draw(new Line2D.Float(0, 30, 30, 0));
                break;
            case ShipSunk:
                break;
            default:
                throw new AssertionError();
        }
    }

}
