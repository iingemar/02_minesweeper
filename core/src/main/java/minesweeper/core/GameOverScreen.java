package minesweeper.core;

import playn.core.*;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-10
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class GameOverScreen implements Screen {
    private GameEngine gameEngine;

    public GameOverScreen(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        init();
    }

    @Override
    public void init() {
        // Create a GroupLayer to hold the sprites
        GroupLayer groupLayer = graphics().createGroupLayer();
        graphics().rootLayer().add(groupLayer);

        // Create gfx!
        Image image = assets().getImage("images/boom.png");
        ImageLayer imageLayer = graphics().createImageLayer(image);
        imageLayer.setTranslation(190, 50);
        groupLayer.add(imageLayer);

       // Text
        CanvasImage canvasImage = graphics().createImage(160, 70);
        Canvas result = canvasImage.canvas();
        result.drawText("BOOM! Game over!", 10, 10);
        ImageLayer resultLayer = graphics().createImageLayer(canvasImage);
        resultLayer.setTranslation(180, 120);
        graphics().rootLayer().add(resultLayer);
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseMovedEvent(Mouse.MotionEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(float delta) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
