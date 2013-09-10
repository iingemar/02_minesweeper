package minesweeper.core;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-02
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public enum TileType {
    BLANK(0, 0),
    FLAG(20, 0),
    MINE(40, 0),
    EMPTY(60, 0),
    ONE(0, 20),
    TWO(20, 20),
    THREE(40, 20),
    FOUR(60, 20),
    FIVE(40, 0),
    SIX(40, 0),
    SEVEN(40, 20),
    EIGHT(40, 40);

    private int x;
    private int y;

    TileType(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
