//Csaba
package battleship.gui.Game;

import battleship.Events.ShipPlaceEvent;
import battleship.Logic.Board;
import battleship.Logic.CellStatus;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Csaba
 */
public class PlayerBoardGUI extends BoardGUI {

    public boolean shipPlaceHorizontal = true;
    public int selectedShipSize = 4;
    private ArrayList<CellGUI> selectedCells;
    private List<ShipPlaceEvent> listeners;
    public boolean canPlace;
    private final Point[] relativeCoords = {
        new Point(-1, -1),
        new Point(-1, 0),
        new Point(-1, 1),
        new Point(0, -1),
        new Point(0, 0),
        new Point(0, 1),
        new Point(1, -1),
        new Point(1, 0),
        new Point(1, 1)
    };

    public PlayerBoardGUI(Board board) {
        super(board);
        canPlace = true;
        selectedCells = new ArrayList<CellGUI>();
        this.listeners = new ArrayList<>();

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
                            if (canPlace) {
                                cellEntered(seged);
                            }
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
                seged.setCell(board.getCellstatus()[i][j]);
                this.add(seged);
            }
        }
    }

    private void setNearAreas(CellGUI cell, CellStatus status) {
        if (shipPlaceHorizontal) {
            for (int i = 0; i < selectedShipSize; i++) {
                //System.out.println("");
                for (Point relativeCoord : relativeCoords) {
                    int cellI = cell.getI() + relativeCoord.y;
                    int cellJ = cell.getJ() + relativeCoord.x;
                    cellI += i;
                    //System.out.println("H_ CellI:" + cellI + " cellJ:" + cellJ);
                    if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                        if (cells[cellI][cellJ].getStatus() == CellStatus.Empty) {
                            //System.out.println("H_ Invalid place: " + cellI + " - " + cellJ);
                            cells[cellI][cellJ].setCell(status);
                        }
                    }

                }
            }
        } else {
            for (int i = 0; i < selectedShipSize; i++) {
                //System.out.println("V_ ");
                for (Point relativeCoord : relativeCoords) {
                    int cellI = cell.getI() + relativeCoord.y;
                    int cellJ = cell.getJ() + relativeCoord.x;

                    cellJ += i;
                    //System.out.println("V_ CellI:" + cellI + " cellJ:" + cellJ);
                    if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                        if (cells[cellI][cellJ].getStatus() != CellStatus.Empty) {
                            //System.out.println("V_ Invalid place: " + cellI + " - " + cellJ);
                            cells[cellI][cellJ].setCell(status);
                        }
                    }
                }
            }
        }
    }

    public void addPlaceOrPickUpListener(ShipPlaceEvent listener) {
        listeners.add(listener);
    }

    private void shipPlaced() {
        for (ShipPlaceEvent listener : listeners) {
            listener.onPlace(selectedShipSize, shipPlaceHorizontal);
        }
    }

    private void shipPickUp(int pickupShipSize) {
        for (ShipPlaceEvent listener : listeners) {
            listener.onPickUp(pickupShipSize, shipPlaceHorizontal);
        }
    }

    private void cellClick(CellGUI cell) {
        if (cell.getStatus() != CellStatus.Ship && canPlace) { //Ha lerak
            if (selectedCells.size() > 0) {
                System.out.println("lerak");
                for (CellGUI selectedCell : selectedCells) {
                    selectedCell.setCell(CellStatus.Ship);
                    board.setCell(selectedCell.getI(), selectedCell.getJ(), selectedCell.getStatus());
                }
                shipPlaced();
                //setNearAreas(cell, CellStatus.NearShip);
            }
        } else if (cell.getStatus() == CellStatus.Ship) { //Ha felszedi
            System.out.println("felszed");
            int cellI = cell.getI(), cellJ = cell.getJ();
            int i = 1;
            int pickupShipSize = 0;
            //LE
            while (cellI >= 0 && cellI <= 9 && cellJ + i >= 0 && cellJ + i <= 9) {
                if (cells[cellI][cellJ + i].getStatus() == CellStatus.Ship) {
                    cells[cellI][cellJ + i].setCell(CellStatus.Empty);
                    ++pickupShipSize;
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
                    ++pickupShipSize;
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
                    ++pickupShipSize;
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
                    ++pickupShipSize;
                } else {
                    break;
                }
                --i;
            }
            shipPickUp(pickupShipSize);
            //setNearAreas(cell, CellStatus.Empty);
            cellEntered(cell); //Kijelölve legyen, amit levettünk
        }
    }

    private void cellEntered(CellGUI cell) {
        selectedCells.clear();

        if (isEmptyPlace(cell)) {
            if (shipPlaceHorizontal) {
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[i + cell.getI()][cell.getJ()].select();
                    selectedCells.add(cells[i + cell.getI()][cell.getJ()]);
                }
            } else {
                for (int i = 0; i < selectedShipSize; i++) {
                    cells[cell.getI()][cell.getJ() + i].select();
                    selectedCells.add(cells[cell.getI()][cell.getJ() + i]);
                }
            }
        }
    }

    private boolean isEmptyPlace(CellGUI cell) {
        //System.out.println("#######################");
        if (shipPlaceHorizontal) {
            if (cell.getI() + selectedShipSize - 1 <= 9) {
                for (int i = 0; i < selectedShipSize; i++) {
                    //System.out.println("");
                    for (Point relativeCoord : relativeCoords) {
                        int cellI = cell.getI() + relativeCoord.y;
                        int cellJ = cell.getJ() + relativeCoord.x;

                        cellI += i;
                        //System.out.println("H_ CellI:" + cellI + " cellJ:" + cellJ);
                        if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                            if (cells[cellI][cellJ].getStatus() == CellStatus.Ship) {
                                //System.out.println("H_ Invalid place: " + cellI + " - " + cellJ);
                                return false;
                            }
                        }
                    }
                }
            } else {
                //System.out.println("H_ Hahó ez kiesne");
                return false;
            }
        } else {//Vertical
            if (cell.getJ() + selectedShipSize - 1 <= 9) {
                for (int i = 0; i < selectedShipSize; i++) {
                    //System.out.println("V_ ");
                    for (Point relativeCoord : relativeCoords) {
                        int cellI = cell.getI() + relativeCoord.y;
                        int cellJ = cell.getJ() + relativeCoord.x;

                        cellJ += i;
                        //System.out.println("V_ CellI:" + cellI + " cellJ:" + cellJ);
                        if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                            if (cells[cellI][cellJ].getStatus() != CellStatus.Empty) {
                                //System.out.println("V_ Invalid place: " + cellI + " - " + cellJ);
                                return false;
                            }
                        }
                    }
                }
            } else {
                //System.out.println("V_ Hahó ez kiesne");
                return false;
            }
        }
        return true;
    }

    private void cellExited(CellGUI cell) {
        for (CellGUI selectedCell : selectedCells) {
            selectedCell.unSelect();
        }
    }

    public void ClearBoard() {
        System.out.println("Clearing board");
        for (CellGUI[] cell : cells) {
            for (CellGUI cellGUI : cell) {
                cellGUI.setCell(CellStatus.Empty);
            }
        }
    }

    public void RandomPlace() {
        board = Board.RandomBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j].setCell(board.getCellstatus()[i][j]);
            }
        }
    }

}
