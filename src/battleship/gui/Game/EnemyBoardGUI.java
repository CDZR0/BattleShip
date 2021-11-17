//Csaba
package battleship.gui.Game;

import battleship.Events.ShotEvent;
import battleship.Logic.Board;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Csaba
 */
public class EnemyBoardGUI extends BoardGUI {

    private List<ShotEvent> listeners;
    boolean canTip;

    public EnemyBoardGUI(Board board) {
        super(board);

        this.listeners = new ArrayList<>();
        cells = new CellGUI[board.getNLength()][board.getNLength()];
        canTip = true;
        int width = super.size().width / cells.length;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                CellGUI seged = new CellGUI(i, j);
                seged.setSize(width, width);
                seged.setLocation(i * width, j * width);
                seged.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isEnabled() && canTip) {
                            System.out.println("cell clicked");
                            cellClick(seged);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isEnabled() && canTip) {
                            cellEntered(seged);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (isEnabled()) {
                            cellExited(seged);
                        }
                    }

                });
                cells[i][j] = seged;
                this.add(seged);
            }
        }
    }

    public void setTurnEnabled(boolean value) {
        canTip = value;
        setEnabled(value);
    }

    public void addShotListener(ShotEvent listener) {
        listeners.add(listener);
    }

    private void cellClick(CellGUI cell) {
        for (ShotEvent listener : listeners) {
            listener.onShot(cell.getI(), cell.getJ());
        }
        setTurnEnabled(false);
        cellExited(cell);
    }

    private void cellEntered(CellGUI cell) {
        cells[cell.getI()][cell.getJ()].select();
    }

    private void cellExited(CellGUI cell) {
        cells[cell.getI()][cell.getJ()].unSelect();
    }
}
