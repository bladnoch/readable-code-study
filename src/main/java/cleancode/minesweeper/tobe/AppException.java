package cleancode.minesweeper.tobe;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : AppException
 * author         : doungukkim
 * date           : 2024. 9. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 9. 29.        doungukkim       최초 생성
 */
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
