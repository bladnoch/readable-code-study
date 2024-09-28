package cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUMN_SIZE = 10;

    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPEND_CELL_SIGN = "■";

    public static final String[][] BOARD = new String[BOARD_ROW_SIZE][ BOARD_COLUMN_SIZE ];
    private static final Integer[][] NEAR_BY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][ BOARD_COLUMN_SIZE ];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][ BOARD_COLUMN_SIZE ];
    public static final int LAND_MINE_COUNT = 10;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배


    public static void main(String[] args) {
        showGameStartComments();

        Scanner scanner = new Scanner(System.in); // 사용자 입력

        initializeGame();

        while (true) {
            showBoard();
            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (doesUserLoseTheGame()) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            String cellInput = getCellInputFromUser(scanner);
            String userActionInput = getUserActionInputFromUser(scanner);

            int selectedColumnIndex = getSelectedColumnIndex(cellInput);
            int selectedRowIndex = getSelectedRowIndex(cellInput);


            if (doesUserChooseToPlantFlag(userActionInput)) {
                BOARD[selectedRowIndex][selectedColumnIndex] = FLAG_SIGN;
                checkIfGameIsOver();
            } else if (doesUserChooseToOpenCell(userActionInput)) {
                if (isLandMineCell(selectedRowIndex, selectedColumnIndex)) {
                    BOARD[selectedRowIndex][selectedColumnIndex] = LAND_MINE_SIGN;
                    changeGameStatusToLose();
                    continue;
                } else {
                    open(selectedRowIndex, selectedColumnIndex);
                }
                checkIfGameIsOver();
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        return LAND_MINES[selectedRowIndex][selectedColumnIndex];
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

    private static String getUserActionInputFromUser(Scanner scanner) {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return scanner.nextLine();
    }

    private static String getCellInputFromUser(Scanner scanner) {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return scanner.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellIsAllOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellIsAllOpened() {
        boolean isAllOpened = true;
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                if (BOARD[row][column].equals(CLOSED_CELL_SIGN)) {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
    }

    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
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
                return -1;
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                System.out.print(BOARD[row][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                BOARD[row][column] = CLOSED_CELL_SIGN;
            }
        }
        // 10개의 지뢰 생성
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COLUMN_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[row][col] = true;
        }

        // 선택된 칸 주변 8개의 박스에 지뢰가 몇개 있는지 확인
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                int count = 0;
                if (!isLandMineCell(row, column)) { // 지뢰 (x)
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
                    NEAR_BY_LAND_MINE_COUNTS[row][column] = count;
                    continue;
                }
                NEAR_BY_LAND_MINE_COUNTS[row][column] = 0;
            }
        }
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
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        if (NEAR_BY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(NEAR_BY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPEND_CELL_SIGN;
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
