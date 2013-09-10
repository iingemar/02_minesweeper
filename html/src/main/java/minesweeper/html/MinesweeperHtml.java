package minesweeper.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import minesweeper.core.Minesweeper;

public class MinesweeperHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("02_minesweeper/");
    PlayN.run(new Minesweeper());
  }
}
