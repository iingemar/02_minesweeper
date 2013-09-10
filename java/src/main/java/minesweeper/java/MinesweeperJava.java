package minesweeper.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import minesweeper.core.Minesweeper;

public class MinesweeperJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("minesweeper/resources");
    PlayN.run(new Minesweeper());
  }
}
