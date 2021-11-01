//Csaba
package battleship.gui.Game;

import battleship.Logic.Board;
import battleship.Logic.CellStatus;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Csaba
 */
public class BoardGUI extends JPanel {

    private Board board;
    private CellGUI[][] cells;
    public boolean shipPlaceHorizontal = true;
    public int selectedShipSize = 4;
    private ArrayList<CellGUI> selectedCells;

    public BoardGUI(Board board) {
        setLayout(null);
        int widtheight = 300;
        this.board = board;
        this.setSize(widtheight, widtheight);
        this.setBackground(Color.GRAY);
        cells = new CellGUI[board.getN()][board.getN()];
        selectedCells = new ArrayList<CellGUI>();

        int width = widtheight / cells.length;

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

    private void cellClick(CellGUI cell) {
        if (cell.getStatus() != CellStatus.Ship) { //Ha lerak
            System.out.println("lerak");
            for (CellGUI selectedCell : selectedCells) {
                selectedCell.setCell(CellStatus.Ship);
            }
        } else { //Ha felszedi
            System.out.println("felszed");
            int cellI = cell.getI(), cellJ = cell.getJ();
            int i = 1;

            //LE
            while (cellI >= 0 && cellI <= 9 && cellJ + i >= 0 && cellJ + i <= 9) {
                if (cells[cellI][cellJ + i].getStatus() == CellStatus.Ship) {
                    cells[cellI][cellJ + i].setCell(CellStatus.Empty);
                } else {
                    break;
                }
                ++i;
            }
            i = 0;
            //FEL
            while (cellI >= 0 && cellI <= 9 && cellJ + i >= 0 && cellJ + i <= 9) {
                if (cells[cellI][cellJ + i].getStatus() == CellStatus.Ship) {
                    cells[cellI][cellJ + i].setCell(CellStatus.Empty);
                } else {
                    break;
                }
                --i;
            }
            i = 1;
            //JOBBRA
            while (cellI + i >= 0 && cellI + i <= 9 && cellJ >= 0 && cellJ <= 9) {
                if (cells[cellI + i][cellJ].getStatus() == CellStatus.Ship) {
                    cells[cellI + i][cellJ].setCell(CellStatus.Empty);
                } else {
                    break;
                }
                ++i;
            }
            i = -1;
            //BALRA
            while (cellI + i >= 0 && cellI + i <= 9 && cellJ >= 0 && cellJ <= 9) {
                if (cells[cellI + i][cellJ].getStatus() == CellStatus.Ship) {
                    cells[cellI + i][cellJ].setCell(CellStatus.Empty);
                } else {
                    break;
                }
                --i;
            }
            cellEntered(cell); //Kijelölve legyen, amit levettünk
        }
    }

    private boolean isInTable(CellGUI cell) {
        if (cell.getI() + selectedShipSize - 1 <= 9 && cell.getJ() + selectedShipSize - 1 <= 9) {
            return true;
        }
        return false;
    }

    private void cellEntered(CellGUI cell) {
        selectedCells.clear();
        if (shipPlaceHorizontal) { //Vízszintes
            if (cell.getI() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[i + cell.getI()][cell.getJ()].select();
                    selectedCells.add(cells[i + cell.getI()][cell.getJ()]);
                }
            } else { // ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[i][cell.getJ()].select();
                    selectedCells.add(cells[i][cell.getJ()]);
                }
            }
        } else { //Függőleges
            if (cell.getJ() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[cell.getI()][cell.getJ() + i].select();
                    selectedCells.add(cells[cell.getI()][cell.getJ() + i]);
                }
            } else { //ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[cell.getI()][i].select();
                    selectedCells.add(cells[cell.getI()][i]);
                }
            }
        }
    }

    private void cellEntered___OLD(CellGUI cell) {
        selectedCells.clear();
        if (shipPlaceHorizontal) { //Vízszintes
            if (cell.getI() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[i + cell.getI()][cell.getJ()].select();
                    selectedCells.add(cells[i + cell.getI()][cell.getJ()]);
                }
            } else { // ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[i][cell.getJ()].select();
                    selectedCells.add(cells[i][cell.getJ()]);
                }
            }
        } else { //Függőleges
            if (cell.getJ() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[cell.getI()][cell.getJ() + i].select();
                    selectedCells.add(cells[cell.getI()][cell.getJ() + i]);
                }
            } else { //ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[cell.getI()][i].select();
                    selectedCells.add(cells[cell.getI()][i]);
                }
            }
        }
    }

    private void cellExited(CellGUI cell) {
        if (shipPlaceHorizontal) { //Vízszintes
            if (cell.getI() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = selectedShipSize - 1; i >= 0; i--) {
                    if (i + cell.getI() < cells.length) {
                        cells[i + cell.getI()][cell.getJ()].unSelect();
                    }
                }
            } else { // ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[i][cell.getJ()].unSelect();
                }
            }
        } else { //Függőleges
            if (cell.getJ() + selectedShipSize - 1 <= 9) { //ha elfér
                for (int i = selectedShipSize - 1; i >= 0; i--) {
                    if (i + cell.getJ() < cells.length) {
                        cells[cell.getI()][cell.getJ() + i].unSelect();
                    }
                }
            } else { // ha nem fér el
                for (int i = cells.length - selectedShipSize; i < cells.length; i++) {
                    cells[cell.getI()][i].unSelect();
                }
            }
        }
    }

}

class PanelListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println(me.toString());
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
