package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class ConverterController {
  @FXML private Button exitViewButton;

  @FXML
  private void exitView() {
    Scene scene = exitViewButton.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
