package minesweeper.core;

import playn.core.*;

import java.util.*;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-10
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen implements Screen {
    private static int BOARD_MAX_WIDTH = 9;
    private static int BOARD_MAX_HEIGHT = 9;
    private static int MINES = 10;
    private static int TILE_EMPTY = 0;
    private static int TILE_MINE = 9;

    private GameEngine gameEngine;
    private int[][] tiles;
    private List<Tile> tileList;
    private Canvas timerLabel;
    private float timer;
    private Canvas clearedTilesLabel;
    private int clearedTiles = -1;
    private boolean gameOver;

    public GameScreen(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        init();
    }
    
    @Override
    public void init() {
        // Create and add background image layer
        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        graphics().rootLayer().add(bgLayer);

        // Create tiles
        tiles = new int[BOARD_MAX_WIDTH][BOARD_MAX_HEIGHT];
        printTiles();

        // Place mines at random positions
        int placedMines = 0;
        Random r = new Random();
        while (placedMines < MINES) {
            int row = r.nextInt(BOARD_MAX_WIDTH);
            int col = r.nextInt(BOARD_MAX_HEIGHT);
            if (tiles[row][col] == TILE_EMPTY) {
                tiles[row][col] = TILE_MINE;
                placedMines++;
            }
        }
        printTiles();

        // Count number of mines around blank tiles
        for (int row = 0; row < BOARD_MAX_WIDTH; row++) {
            for (int col = 0; col < BOARD_MAX_WIDTH; col++) {
                // Check surrounding tiles if mine
                if (tiles[row][col] == TILE_MINE) {
                    for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
                        for (int colCheck = -1; colCheck <= 1; colCheck++) {
                            if (tileExistAndNotMine(row+rowCheck, col+colCheck)) {
                                tiles[row+rowCheck][col+colCheck]++;
                            }
                        }
                    }
                }
            }
        }
        printTiles();

        // Create tile map
        Map<Integer, TileType> tileMap = new HashMap<Integer, TileType>();
        tileMap.put(0, TileType.EMPTY);
        tileMap.put(1, TileType.ONE);
        tileMap.put(2, TileType.TWO);
        tileMap.put(3, TileType.THREE);
        tileMap.put(4, TileType.FOUR);
        tileMap.put(5, TileType.FIVE);
        tileMap.put(6, TileType.SIX);
        tileMap.put(7, TileType.SEVEN);
        tileMap.put(8, TileType.EIGHT);
        tileMap.put(9, TileType.MINE);

        // Create a GroupLayer to hold the sprites
        GroupLayer groupLayer = graphics().createGroupLayer();
        graphics().rootLayer().add(groupLayer);

        // Create graphic tiles
        tileList = new ArrayList<Tile>();
        for (int row = 0; row < BOARD_MAX_WIDTH; row++) {
            for (int col = 0; col < BOARD_MAX_WIDTH; col++) {
                Tile tile = new Tile(groupLayer, col*Tile.SIZE, row*Tile.SIZE, tileMap.get(tiles[row][col]), row, col);
                tileList.add(tile);
            }
        }

        // Timer text
        CanvasImage canvasImage = graphics().createImage(160, 70);
        timerLabel = canvasImage.canvas();
        ImageLayer countDownLayer = graphics().createImageLayer(canvasImage);
        countDownLayer.setTranslation(180, 10);
        graphics().rootLayer().add(countDownLayer);

        // Cleared tiles text
        CanvasImage clearedTilesImage = graphics().createImage(160, 70);
        clearedTilesLabel = clearedTilesImage.canvas();
        ImageLayer clearedTilesLayer = graphics().createImageLayer(clearedTilesImage);
        clearedTilesLayer.setTranslation(180, 30);
        graphics().rootLayer().add(clearedTilesLayer);
        updateClearedTiles();
    }

    @Override
    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseUpEvent(Mouse.ButtonEvent buttonEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseDownEvent(Mouse.ButtonEvent buttonEvent) {
        for (Tile tile : tileList) {
            if (tile.clicked(buttonEvent.x(), buttonEvent.y()) && tile.clickable()) {
                // Mark as flag on right mouse button
                if (buttonEvent.button() == Mouse.BUTTON_RIGHT) {
                    tile.toggleFlag();
                } else {
                    // Flood fill current tile and neighbors if empty
                    if (tile.getType().equals(TileType.EMPTY)) {
                        floodFill(tile);
                    } else if (tile.getType().equals(TileType.MINE)) {
                        tile.setVisibleType(tile.getType());
                        gameOver = true;
                        gameEngine.setScreen(new GameOverScreen(gameEngine));
                    } else {
                        System.out.println(tile);
                        tile.setVisibleType(tile.getType());
                        updateClearedTiles();
                    }
                }
            }
        }
    }

    @Override
    public void handleMouseMovedEvent(Mouse.MotionEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(float delta) {
        // Update timer
        timer += delta;
        timerLabel.clear().drawText("Timer: " + timer, 10, 10);
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets given tile as empty if blank/flagged and then all its neighbors recursivly.
     * @param tile
     */
    private void floodFill(Tile tile) {
        // Check that this tile is non clicked to prevent infinite loops
        if (tile.clickable()) {
            // Show contents and set as clicked
            tile.setVisibleType(tile.getType());
            updateClearedTiles();

            int row = tile.getRow();
            int col = tile.getCol();

            if (tiles[row][col] == 0) {
                for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
                    for (int colCheck = -1; colCheck <= 1; colCheck++) {
                        int tempRow = row+rowCheck;
                        int tempCol = col+colCheck;
                        if (tileExistAndNotMine(tempRow, tempCol)) {
                            Tile current = tileList.get(tempRow * BOARD_MAX_WIDTH + tempCol);
                            System.out.println(current);
                            floodFill(current);
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates cleared tiles label.
     */
    public void updateClearedTiles() {
        clearedTiles++;
        String text = String.format("Cleared tiles: %s / %s",
                clearedTiles,
                tileList.size() - MINES);
        clearedTilesLabel.clear().drawText(text, 10, 10);

        // Check if cleared game
        if (clearedTiles == (tileList.size() - MINES)) {
            gameEngine.setScreen(new WinScreen(gameEngine));
        }
    }

    /**
     * Check if given position is an existing tile and is *not* a mine.
     * @param row
     * @param col
     * @return
     */
    public boolean tileExistAndNotMine(int row, int col) {
        // Bounds check
        if (row >= 0 && row <= (BOARD_MAX_WIDTH - 1)&& col >= 0 && col <= (BOARD_MAX_HEIGHT - 1)) {
            // Mine check
            return tiles[row][col] != TILE_MINE;
        } else {
            return false;
        }
    }

    /**
     * Print tiles configuration in console.
     */
    public void printTiles() {
        for (int row=0; row<BOARD_MAX_WIDTH; row++) {
            for (int col=0; col<BOARD_MAX_WIDTH; col++) {
                System.out.print(tiles[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
