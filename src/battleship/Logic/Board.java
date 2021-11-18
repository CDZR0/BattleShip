//Csaba
package battleship.Logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Csaba
 */
public class Board {

    private static final Point[] relativeCoords = {
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

    CellStatus[][] cellstatus = new CellStatus[10][10];

    public Board() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cellstatus[i][j] = CellStatus.Empty;
            }
        }
    }

    public Board(String a) {
        String[] row = a.split(";");
        for (int i = 0; i < 10; i++) {
            String[] column = row[i].split(":");
            for (int j = 0; j < 10; j++) {
                cellstatus[i][j] = CellStatus.valueOf(column[j]);
            }
        }
        System.out.println(this);
    }

    public void setCell(int i, int j, CellStatus status) {
        cellstatus[i][j] = status;
    }

    public int getNLength() {
        return cellstatus.length;
    }

    public CellStatus[][] getCellstatus() {
        return cellstatus;
    }

    public String convertToString() {
        String re = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                re += cellstatus[i][j] + ":";
            }
            re += ";";
        }
        return re;
    }

    @Override
    public String toString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                a += cellstatus[i][j] + " ";
            }
            a += "\n";
        }
        return "Board:\n" + a;
    }

    public static Board TesztBoard() {
        Board board = new Board();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (rnd.nextBoolean()) {
                    board.getCellstatus()[i][j] = CellStatus.Ship;
                }
            }
        }

        return board;
    }

    public static Board RandomBoard() {
        //System.out.println("\n\n\n");
        Random rnd = new Random();
        Board board = new Board();
        int[] shipSizes = {4, 3, 2, 1};
        int[] shipPieces = {1, 2, 3, 4};
        ArrayList<Point> availableCells = new ArrayList<Point>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                availableCells.add(new Point(i, j));
            }
        }

        for (int i = 0; i < shipSizes.length; i++) {
            //System.out.println("##############\nShip size: " + shipSizes[i]);
            for (int dbI = 0; dbI < shipPieces[i]; dbI++) {
                //System.out.println("Darab: " + dbI);
                ArrayList<Integer> availableCellsIntegers = new ArrayList<Integer>();
                for (int s = 0; s < availableCells.size(); s++) {
                    availableCellsIntegers.add(s);
                }

                boolean probalkozz = true, horizontal = rnd.nextBoolean();
                int cellI, cellJ;

                do {
                    int rndnum = rnd.nextInt(availableCellsIntegers.size());
                    //System.out.println("size: " + availableCellsIntegers.size() + "\trndnum: " + rndnum);
                    int sgd = availableCellsIntegers.get(rndnum);

                    cellI = availableCells.get(sgd).x;
                    cellJ = availableCells.get(sgd).y;

                    if (Board.isEmptyPlace(board.cellstatus, cellI, cellJ, shipSizes[i], horizontal)) {
                        if (horizontal) {
                            for (int s = 0; s < shipSizes[i]; s++) {
                                board.setCell(cellI + s, cellJ, CellStatus.Ship);
                            }
                        } else {
                            for (int s = 0; s < shipSizes[i]; s++) {
                                board.setCell(cellI, cellJ + s, CellStatus.Ship);
                            }
                        }
                        probalkozz = false;
                    }
                    availableCellsIntegers.remove(rndnum);
                } while (probalkozz && availableCellsIntegers.size() > 0);

            }
        }

        return board;
    }

    private static boolean isEmptyPlace(CellStatus[][] cell, int ccelli, int ccellj, int selectedShipSize, boolean shipPlaceHorizontal) {
        //System.out.println("#######################");
        if (shipPlaceHorizontal) {
            if (ccelli + selectedShipSize - 1 <= 9) {
                for (int i = 0; i < selectedShipSize; i++) {
                    //System.out.println("");
                    for (Point relativeCoord : relativeCoords) {
                        int cellI = ccelli + relativeCoord.y;
                        int cellJ = ccellj + relativeCoord.x;

                        cellI += i;
                        //System.out.println("H_ CellI:" + cellI + " cellJ:" + cellJ);
                        if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                            if (cell[cellI][cellJ] == CellStatus.Ship) {
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
            if (ccellj + selectedShipSize - 1 <= 9) {
                for (int i = 0; i < selectedShipSize; i++) {
                    //System.out.println("V_ ");
                    for (Point relativeCoord : relativeCoords) {
                        int cellI = ccelli + relativeCoord.y;
                        int cellJ = ccellj + relativeCoord.x;

                        cellJ += i;
                        //System.out.println("V_ CellI:" + cellI + " cellJ:" + cellJ);
                        if (cellI >= 0 && cellI <= 9 && cellJ >= 0 && cellJ <= 9) {
                            if (cell[cellI][cellJ] != CellStatus.Empty) {
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

    public boolean hasCellStatus(CellStatus status) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cellstatus[i][j] == status) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Point> shipCoords(int i, int j) {
        List<Point> shipsCoords = new ArrayList<>();
        
        
        
        return shipsCoords;
    }

    public boolean isSunk(int i, int j) {
//HAJÓKON VÉGIGMENÉS, MAJD AZON LISTÁKON A RELATÍVKOORDINÁTÁT
        List<Point> shipsCoords = new ArrayList<>();

        for (int a = 0; a < relativeCoords.length; a++) {
            int si = i + relativeCoords[a].x;
            int sj = j + relativeCoords[a].y;
            if (si >= 0 && si < 10 && sj >= 0 && sj < 10) {
                if (cellstatus[si][sj] == CellStatus.Ship) {
                    shipsCoords.add(new Point(si, sj));
                }
            }
        }

        for (Point relativeCoord : relativeCoords) {
            int si = i + relativeCoord.x;
            int sj = j + relativeCoord.y;
            if (si >= 0 && si < 10 && sj >= 0 && sj < 10) {
                if (cellstatus[si][sj] == CellStatus.Ship) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Point> nearShipPoints(int i, int j) {
        List<Point> points = new ArrayList<>();
        for (Point relativeCoord : relativeCoords) {
            int si = i + relativeCoord.x;
            int sj = j + relativeCoord.y;
            if (si >= 0 && si < 10 && sj >= 0 && sj < 10) {
                if (cellstatus[si][sj] == CellStatus.Ship) {
                    for (Point nearShipPoint : nearShipPoints(si, sj)) {
                        if (!points.contains(nearShipPoint)) {
                            points.add(nearShipPoint);
                        }
                    }
                } else {
                    if (!points.contains(new Point(si, sj))) {
                        points.add(new Point(si, sj));
                    }
                }
            }
        }
        return points;
    }

}
