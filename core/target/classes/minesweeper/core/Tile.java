package minesweeper.core;

import playn.core.GroupLayer;
import playn.core.ImageLayer;

public class Tile extends Sprite {

    public static int SIZE = 20;
    private TileType type;
    private TileState state;
    private TileType visibleType;
    private int row;
    private int col;


    public Tile(GroupLayer groupLayer, int x, int y, TileType type, int row, int col) {
        super(groupLayer, x, y);
        this.row = row;
        this.col = col;

        // Load sprite
        loadImage("images/minesweeper_tiles.jpg");

        // Set size show to 20 x 20 px
        ImageLayer imageLayer = getImageLayer();
        imageLayer.setWidth(SIZE);
		imageLayer.setHeight(SIZE);

        // Set current visibleType as blank
        this.type = type;
        setVisibleType(TileType.BLANK);

        //
        this.state = TileState.ACTIVE;
    }

    public TileType getVisibleType() {
        return visibleType;
    }

    public void setVisibleType(TileType type) {
        this.visibleType = type;
		getImageLayer().setSourceRect(type.getX(), type.getY(), SIZE, SIZE);
    }

    public boolean clicked(float buttonX, float buttonY) {
        return buttonX > getX() && buttonX < getX() + SIZE
                && buttonY > getY() && buttonY < getY() + SIZE;
    }

    public TileType getType() {
        return type;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void toggleFlag() {
        System.out.println(visibleType);
        if (visibleType.equals(TileType.BLANK)) {
            setVisibleType(TileType.FLAG);
        } else {
            setVisibleType(TileType.BLANK);
        }
    }

    public boolean clickable() {
        return visibleType.equals(TileType.BLANK) || visibleType.equals(TileType.FLAG);
    }

    public String toString() {
        return String.format("%s [%s,%s]", type, row, col);
    }
}

