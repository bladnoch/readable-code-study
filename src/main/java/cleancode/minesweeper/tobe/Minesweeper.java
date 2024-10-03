package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;
import java.util.Random;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : Minesweeper
 * author         : doungukkim
 * date           : 2024. 10. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 10. 3.        doungukkim       최초 생성
 */


public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUMN_SIZE = 10;

    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COLUMN_SIZE);
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        consoleOutputHandler.showGameStartComments();

        gameBoard.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComments();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComments();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) { // 예상된 exception
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) { // 예상 못한 exception
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }


    private  void actOnCell(String cellInput, String userActionInput) {
        int selectedColumnIndex = getSelectedColumnIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColumnIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex,selectedColumnIndex)) {
                gameBoard.openSurroundedCells(selectedRowIndex, selectedColumnIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColumnIndex);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");


    }

    private  void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private  boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private  boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private  int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private  int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn);
    }

    private  String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private  String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }

    private  boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private  boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private  void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private  void changeGameStatusToWin() {
        gameStatus = 1;
    }


    private  int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZE) {
            throw new GameException("잘못된 입력 입니다.");
        }
        return rowIndex;
    }

    private  int convertColumnFrom(char cellInputColumn) {

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
                throw new GameException("잘못된 입력 입니다.");
        }
    }

}
