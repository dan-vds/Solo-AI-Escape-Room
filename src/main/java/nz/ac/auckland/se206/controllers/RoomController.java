package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

/** Controller class for the room view. */
public class RoomController {
  @FXML private Pane room;
  @FXML private Rectangle door;
  @FXML private Rectangle vent;
  @FXML private Rectangle toiletPaper;
  @FXML private Rectangle toilet;
  @FXML private Rectangle posters;
  @FXML private Rectangle sink;
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
  @FXML private ImageView toiletArrow;
  @FXML private ImageView postersArrow;
  @FXML private ImageView toiletPaperArrow;
  @FXML private ImageView ventArrow;
  @FXML private ImageView bedsideTableArrow;
  @FXML private ImageView sinkArrow;
  @FXML private ImageView mirrorArrow;
  @FXML private ImageView towelArrow;
  @FXML private ImageView windowArrow;
  @FXML private ImageView doorArrow;
  @FXML private Label timerLabel;

  private Timeline timeline;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    animateArrows(doorArrow);
    GameState.isRiddleResolvedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                animateAllArrows();
              }
            });
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

    Rectangle[] items = new Rectangle[] {vent, toiletPaper, toilet, bedsideTable, mirror, towel};

    Random random = new Random();
    int randomIndex = random.nextInt(items.length);
    GameState.itemToChoose = items[randomIndex];
  }

  public void animateAllArrows() {
    animateArrows(toiletArrow);
    animateArrows(postersArrow);
    animateArrows(toiletPaperArrow);
    animateArrows(ventArrow);
    animateArrows(bedsideTableArrow);
    animateArrows(sinkArrow);
    animateArrows(mirrorArrow);
    animateArrows(towelArrow);
    animateArrows(windowArrow);
  }

  public void animateArrows(ImageView arrow) {
    arrow.setOpacity(1);

    double startY = 0;

    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), arrow);
    translateTransition.setFromY(startY);
    translateTransition.setToY(startY + 5);
    translateTransition.setAutoReverse(true);
    translateTransition.setCycleCount(Animation.INDEFINITE);
    translateTransition.play();
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
    if (GameState.isRiddleResolved()) {
      toiletBig.setOpacity(1);
      toiletArrow.setOpacity(0);
    }
  }

  @FXML
  public void toiletMouseExit() {
    toiletBig.setOpacity(0);
  }

  @FXML
  public void toiletPaperMouseEntered() {
    if (GameState.isRiddleResolved()) {
      toiletPaperBig.setOpacity(1);
      toiletPaperArrow.setOpacity(0);
    }
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
    if (GameState.isRiddleResolved()) {
      ventBig.setOpacity(1);
      ventArrow.setOpacity(0);
    }
  }

  @FXML
  public void ventMouseExit() {
    ventBig.setOpacity(0);
  }

  @FXML
  public void postersMouseEntered() {
    if (GameState.isRiddleResolved()) {
      postersBig.setOpacity(1);
      postersArrow.setOpacity(0);
    }
  }

  @FXML
  public void postersMouseExit() {
    postersBig.setOpacity(0);
  }

  @FXML
  public void bedsideTableMouseEntered() {
    if (GameState.isRiddleResolved()) {
      bedsideTableBig.setOpacity(1);
      bedsideTableArrow.setOpacity(0);
    }
  }

  @FXML
  public void bedsideTableMouseExit() {
    bedsideTableBig.setOpacity(0);
  }

  @FXML
  public void sinkMouseEntered() {
    if (GameState.isRiddleResolved()) {
      sinkBig.setOpacity(1);
      sinkArrow.setOpacity(0);
    }
  }

  @FXML
  public void sinkMouseExit() {
    sinkBig.setOpacity(0);
  }

  @FXML
  public void mirrorMouseEntered() {
    if (GameState.isRiddleResolved()) {
      mirrorBig.setOpacity(1);
      mirrorArrow.setOpacity(0);
    }
  }

  @FXML
  public void mirrorMouseExit() {
    mirrorBig.setOpacity(0);
  }

  @FXML
  public void towelMouseEntered() {
    if (GameState.isRiddleResolved()) {
      towelBig.setOpacity(1);
      towelArrow.setOpacity(0);
    }
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
    doorArrow.setOpacity(0);

    if (!GameState.isRiddleResolved()) {
      showDialog(
          "Info",
          "A guard approaches...",
          "\"Are you ready to break out of here? Then listen to this riddle:\" he says");
      Scene scene = door.getScene();
      scene.setRoot(SceneManager.getUiRoot(AppUi.CHAT));
      return;
    }

    if (!GameState.isKeyFound) {
      showDialog("Info", "The door is padlocked shut!", "You must find the passcode to escape!");
    } else {
      showDialog("Info", "You Won!", "Good Job!");
    }
  }

  @FXML
  public void clickPosters(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      Scene scene = door.getScene();
      scene.setRoot(SceneManager.getUiRoot(AppUi.CONVERTER));
    }
    return;
  }

  @FXML
  public void clickToilet(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == toilet) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "On the inside of the toilet is written the word " + "JAIL");
      } else {
        showDialog("Nothing!", "Toilet", "Just a normal toilet.");
      }
    }
    return;
  }

  @FXML
  public void clickToiletPaper(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == toiletPaper) {
        showDialog(
            "Nice Job", "You found the item!", "On the toilet paper is written the word " + "JAIL");
      } else {
        showDialog("Nothing!", "Toilet Paper", "Just a normal roll of toilet paper.");
      }
    }
    return;
  }

  @FXML
  public void clickVent(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == vent) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "In the vent you notice a piece of paper, scribbled on one side is the word " + "JAIL");
      } else {
        showDialog("Nothing!", "Vent", "Just an empty vent");
      }
    }
  }

  @FXML
  public void clickBedsideTable(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == bedsideTable) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "In the drawer you notice a piece of paper, scribbled on one side is the word "
                + "JAIL");
      } else {
        showDialog("Nothing!", "Bedside Table", "Nothing inside...");
      }
    }
  }

  @FXML
  public void clickSink(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == sink) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "In the sink you notice scribbled on the side is the word " + "JAIL");
      } else {
        showDialog("Nothing!", "Sink", "Just an empty sink");
      }
    }
  }

  @FXML
  public void clickMirror(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == mirror) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "On the mirror you notice a word written in ink: " + "JAIL");
      } else {
        showDialog("Nothing!", "Mirror", "Just a normal mirror");
      }
    }
  }

  @FXML
  public void clickTowel(MouseEvent event) {
    if (GameState.isRiddleResolved()) {
      if (GameState.itemToChoose == towel) {
        showDialog(
            "Nice Job",
            "You found the item!",
            "You notice that scribbled on one side of the towel is the word " + "JAIL");
      } else {
        showDialog("Nothing!", "Towel", "Just a normal towel");
      }
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
