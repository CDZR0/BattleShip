//Csaba
package battleship.gui.Game;

import battleship.Logic.Board;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Csaba
 */
public class EnemyBoardGUI extends BoardGUI {
    
    public EnemyBoardGUI(Board board) {
        super(board);
        
         cells = new CellGUI[board.getNLength()][board.getNLength()];

        int width = super.size().width / cells.length;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                CellGUI seged = new CellGUI(i, j);
                seged.setSize(width, width);
                seged.setLocation(i * width, j * width);
                seged.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isEnabled()) {
                            cellClick(seged);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isEnabled()) {
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
    
    private void cellClick(CellGUI seged){
        
    }
    
    private void cellEntered(CellGUI seged){
        
    }
    
    private void cellExited(CellGUI seged){
        
    }
}
