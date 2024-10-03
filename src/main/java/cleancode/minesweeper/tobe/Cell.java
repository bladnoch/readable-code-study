package cleancode.minesweeper.tobe;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : Cell
 * author         : doungukkim
 * date           : 2024. 10. 2.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 10. 2.        doungukkim       최초 생성
 */
public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlaged;
    private boolean isOpened;


    public Cell( int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {

        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlaged = isFlaged;
        this.isOpened = isOpened;
    }

    public static Cell of(int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {
        return new Cell(nearbyLandMineCount, isLandMine, isFlaged, isOpened);
    }

    public static Cell create() {
        return of( 0, false, false, false);
    }

    public void flag() {
        this.isFlaged = true;
    }
    public boolean isLandMine() {
        return isLandMine;
    }
    public void open() {
        this.isOpened = true;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount != 0;
    }

    public boolean isChecked() {
        return isFlaged || isOpened;
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }
            if (hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }
            return EMPTY_SIGN;
        }

        if (isFlaged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }
}
