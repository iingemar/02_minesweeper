package minesweeper.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import minesweeper.core.Minesweeper;

public class MinesweeperActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("minesweeper/resources");
    PlayN.run(new Minesweeper());
  }
}
