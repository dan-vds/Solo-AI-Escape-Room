package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle vent;
  @FXML private Rectangle toiletPaper;
  @FXML private Rectangle toilet;
  @FXML private Rectangle posters;
  @FXML private Rectangle topBed;
  @FXML private Rectangle bottomBed;
  @FXML private Rectangle bedsideTable;
  @FXML private Rectangle tap;
  @FXML private Rectangle mirror;
  @FXML private Rectangle picture;
  @FXML private Rectangle towel;
  @FXML private ImageView toiletBig;
  @FXML private ImageView toiletPaperBig;
  @FXML private ImageView doorBig;
  @FXML private ImageView ventBig;
  @FXML private ImageView postersBig;
  @FXML private ImageView bedsideTableBig;
  @FXML private ImageView sinkBig;
  @FXML private ImageView mirrorBig;
  @FXML private ImageView towelBig;

  @FXML private Label timerLabel;

  private Timeline timeline;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  GameState.secondsRemaining--;
                  updateTimerLabel();
                }));
    timeline.setCycleCount(120);
    timeline.setOnFinished(event -> handleTimerExpired());
    updateTimerLabel();
    timeline.play();

    // Getting random item to be used in the riddle

    Rectangle[] items =
        new Rectangle[] {vent, toiletPaper, toilet, bedsideTable, tap, mirror, towel};

    Random random = new Random();
    int randomIndex = random.nextInt(items.length);
    GameState.itemToChoose = items[randomIndex];
  }

  private void updateTimerLabel() {
    int minutes = GameState.secondsRemaining / 60;
    int seconds = GameState.secondsRemaining % 60;
    timerLabel.setText(String.format("%d:%02d", minutes, seconds));
  }

  private void handleTimerExpired() {
    Scene scene = door.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.START_SCREEN));
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  public void toiletMouseEntered() {
    toiletBig.setOpacity(1);
  }

  @FXML
  public void toiletMouseExit() {
    toiletBig.setOpacity(0);
  }

  @FXML
  public void toiletPaperMouseEntered() {
    toiletPaperBig.setOpacity(1);
  }

  @FXML
  public void toiletPaperMouseExit() {
    toiletPaperBig.setOpacity(0);
  }

  @FXML
  public void doorMouseEntered() {
    doorBig.setOpacity(1);
  }

  @FXML
  public void doorMouseExit() {
    doorBig.setOpacity(0);
  }

  @FXML
  public void ventMouseEntered() {
    ventBig.setOpacity(1);
  }

  @FXML
  public void ventMouseExit() {
    ventBig.setOpacity(0);
  }

  @FXML
  public void postersMouseEntered() {
    postersBig.setOpacity(1);
  }

  @FXML
  public void postersMouseExit() {
    postersBig.setOpacity(0);
  }

  @FXML
  public void bedsideTableMouseEntered() {
    bedsideTableBig.setOpacity(1);
  }

  @FXML
  public void bedsideTableMouseExit() {
    bedsideTableBig.setOpacity(0);
  }

  @FXML
  public void sinkMouseEntered() {
    sinkBig.setOpacity(1);
  }

  @FXML
  public void sinkMouseExit() {
    sinkBig.setOpacity(0);
  }

  @FXML
  public void mirrorMouseEntered() {
    mirrorBig.setOpacity(1);
  }

  @FXML
  public void mirrorMouseExit() {
    mirrorBig.setOpacity(0);
  }

  @FXML
  public void towelMouseEntered() {
    towelBig.setOpacity(1);
  }

  @FXML
  public void towelMouseExit() {
    towelBig.setOpacity(0);
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    if (!GameState.isRiddleResolved) {
      showDialog(
          "Info",
          "A guard approaches...",
          "\"Are you ready to break out of here? Then listen to this riddle:\" he says");
      Scene scene = door.getScene();
      scene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
      return;
    }

    if (!GameState.isKeyFound) {
      showDialog(
          "Info", "Find the key!", "You resolved the riddle, now you know where the key is.");
    } else {
      showDialog("Info", "You Won!", "Good Job!");
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickVase(MouseEvent event) {
    System.out.println("vase clicked");
    if (GameState.isRiddleResolved && !GameState.isKeyFound) {
      showDialog("Info", "Key Found", "You found a key under the vase!");
      GameState.isKeyFound = true;
    }
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickWindow(MouseEvent event) {
    System.out.println("window clicked");
  }
}
