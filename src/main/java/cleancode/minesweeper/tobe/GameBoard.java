package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : GameBoard
 * author         : doungukkim
 * date           : 2024. 10. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 10. 3.        doungukkim       최초 생성
 */
public class GameBoard {

    private static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;

    public GameBoard(int rowSize, int colSize) {
        board = new Cell[rowSize][colSize];
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < colSize; column++) {
                board[row][column] = Cell.create();
            }
        }

        // 10개의 지뢰 생성
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMinecell = findCell(landMineRow, landMineCol);
            landMinecell.turnOnLandMine();
        }

        // 선택된 칸 주변 8개의 박스에 지뢰가 몇개 있는지 확인
        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < colSize; column++) {
                if (isLandMineCell(row, column)) { // 지뢰 (x)
                    continue;
                }
                int count = countNearByLandMines(row, column);
                Cell cell = findCell(row, column);
                cell.updateNearbyLandMineCount(count);
            }
        }
    }

    public int countNearByLandMines(int row, int column) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && column - 1 >= 0 && isLandMineCell(row - 1, column - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, column)) {
            count++;
        }
        if (row - 1 >= 0 && column + 1 < colSize && isLandMineCell(row - 1, column + 1)) {
            count++;
        }
        if (column - 1 >= 0 && isLandMineCell(row, column - 1)) {
            count++;
        }
        if (column + 1 < colSize && isLandMineCell(row, column + 1)) {
            count++;
        }
        if (row + 1 < rowSize && column - 1 >= 0 && isLandMineCell(row + 1, column - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, column)) {
            count++;
        }
        if (row + 1 < rowSize && column + 1 < colSize && isLandMineCell(row + 1, column + 1)) {
            count++;
        }
        return count;
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        return findCell(selectedRowIndex,selectedColumnIndex).isLandMine();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    public Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void flag(int rowIndex, int colIndex) {

        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public   boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        open(row, col);


        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }


}
