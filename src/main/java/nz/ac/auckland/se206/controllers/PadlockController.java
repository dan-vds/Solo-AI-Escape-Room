package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.SceneManager;

public class PadlockController {

  @FXML private Button openButton;
  @FXML private ImageView digitOnePlus;
  @FXML private ImageView digitOneMinus;
  @FXML private ImageView digitTwoPlus;
  @FXML private ImageView digitTwoMinus;
  @FXML private ImageView digitThreePlus;
  @FXML private ImageView digitThreeMinus;
  @FXML private ImageView digitFourPlus;
  @FXML private ImageView digitFourMinus;
  @FXML private Label digitOne;
  @FXML private Label digitTwo;
  @FXML private Label digitThree;
  @FXML private Label digitFour;

  @FXML
  private void digitOneIncrement() {
    int digit = Integer.parseInt(digitOne.getText());
    digit = (digit + 1) % 10;
    digitOne.setText(Integer.toString(digit));
  }

  @FXML
  private void digitOneDecrement() {
    int digit = Integer.parseInt(digitOne.getText());
    digit = (digit - 1 + 10) % 10;
    digitOne.setText(Integer.toString(digit));
  }

  @FXML
  private void digitTwoIncrement() {
    int digit = Integer.parseInt(digitTwo.getText());
    digit = (digit + 1) % 10;
    digitTwo.setText(Integer.toString(digit));
  }

  @FXML
  private void digitTwoDecrement() {
    int digit = Integer.parseInt(digitTwo.getText());
    digit = (digit - 1 + 10) % 10;
    digitTwo.setText(Integer.toString(digit));
  }

  @FXML
  private void digitThreeIncrement() {
    int digit = Integer.parseInt(digitThree.getText());
    digit = (digit + 1) % 10;
    digitThree.setText(Integer.toString(digit));
  }

  @FXML
  private void digitThreeDecrement() {
    int digit = Integer.parseInt(digitThree.getText());
    digit = (digit - 1 + 10) % 10;
    digitThree.setText(Integer.toString(digit));
  }

  @FXML
  private void digitFourIncrement() {
    int digit = Integer.parseInt(digitFour.getText());
    digit = (digit + 1) % 10;
    digitFour.setText(Integer.toString(digit));
  }

  @FXML
  private void digitFourDecrement() {
    int digit = Integer.parseInt(digitFour.getText());
    digit = (digit - 1 + 10) % 10;
    digitFour.setText(Integer.toString(digit));
  }

  @FXML
  private void openPadlock() {
    int digitOneInt = Integer.parseInt(digitOne.getText());
    int digitTwoInt = Integer.parseInt(digitTwo.getText());
    int digitThreeInt = Integer.parseInt(digitThree.getText());
    int digitFourInt = Integer.parseInt(digitFour.getText());
    if (digitOneInt == 1 && digitTwoInt == 2 && digitThreeInt == 3 && digitFourInt == 4) {
      Scene scene = openButton.getScene();
      scene.setRoot(SceneManager.getUiRoot(SceneManager.AppUi.START_SCREEN));
    } else {
      showDialog("Wrong combination", "Try again", "The padlock did not open.");
    }
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
}
