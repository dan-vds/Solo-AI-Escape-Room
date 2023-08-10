package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class StartController {
  @FXML private Button startButton;

  @FXML
  public void startGame() throws IOException {
    Scene scene = startButton.getScene();
    return;
  }
}
