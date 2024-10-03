package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

/**
 * packageName    : cleancode.minesweeper.tobe.io
 * fileName       : ConsoleInputHandler
 * author         : doungukkim
 * date           : 2024. 10. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 10. 3.        doungukkim       최초 생성
 */
public class ConsoleInputHandler {

    public static final Scanner SCANNER = new Scanner(System.in);

    public String getUserInput() {
        return SCANNER.nextLine();
    }

}
