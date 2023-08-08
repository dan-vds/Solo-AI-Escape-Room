package nz.ac.auckland.se206;

import javafx.scene.shape.Rectangle;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  public static int secondsRemaining = 120;

  public static Rectangle itemToChoose;
}
