package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUMN_SIZE = 10;

    public static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][ BOARD_COLUMN_SIZE ];

    public static final int LAND_MINE_COUNT = 10;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static final Scanner SCANNER   = new Scanner(System.in);


    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();

        while (true) {
            try {
                showBoard();

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) { // 예상된 exception
                System.out.println(e.getMessage());
            } catch (Exception e) { // 예상 못한 exception
                System.out.println("프로그램에 문제가 생겼습니다.");

            }
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColumnIndex = getSelectedColumnIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColumnIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColumnIndex)) {
                BOARD[selectedRowIndex][selectedColumnIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColumnIndex);
            checkIfGameIsOver();
            return;
        }
        throw new AppException("잘못된 번호를 선택하셨습니다.");


    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        return BOARD[selectedRowIndex][selectedColumnIndex].isLandMine();
    }



    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn);
    }

    private static String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getCellInputFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllChecked = isAllCellChecked();
        if (isAllChecked) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellChecked() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }


    private static int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZE) {
            throw new AppException("잘못된 입력 입니다.");
        }
        return rowIndex;
    }

    private static int convertColumnFrom(char cellInputColumn) {

        switch (cellInputColumn) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("잘못된 입력 입니다.");
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                System.out.print(BOARD[row][column].getSign() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                BOARD[row][column] = Cell.create();
            }
        }

        // 10개의 지뢰 생성
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COLUMN_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }

        // 선택된 칸 주변 8개의 박스에 지뢰가 몇개 있는지 확인
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                if (isLandMineCell(row, column)) { // 지뢰 (x)
                    continue;
                }
                int count = countNearByLandMines(row, column);
                BOARD[row][column].updateNearbyLandMineCount(count);
            }
        }
    }

    private static int countNearByLandMines(int row, int column) {
        int count = 0;
        if (row - 1 >= 0 && column - 1 >= 0 && isLandMineCell(row - 1, column - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, column)) {
            count++;
        }
        if (row - 1 >= 0 && column + 1 < BOARD_COLUMN_SIZE && isLandMineCell(row - 1, column + 1)) {
            count++;
        }
        if (column - 1 >= 0 && isLandMineCell(row, column - 1)) {
            count++;
        }
        if (column + 1 < BOARD_COLUMN_SIZE && isLandMineCell(row, column + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && column - 1 >= 0 && isLandMineCell(row + 1, column - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, column)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && column + 1 < BOARD_COLUMN_SIZE && isLandMineCell(row + 1, column + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COLUMN_SIZE) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        BOARD[row][col].open();

        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
