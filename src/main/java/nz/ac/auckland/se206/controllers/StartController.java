package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class StartController {
  @FXML private Button startButton;

  @FXML
  public void startGame() throws IOException {
    Scene scene = startButton.getScene();
    SceneManager.addUi(AppUi.ROOM, App.loadFxml("room"));
    SceneManager.addUi(AppUi.CHAT, App.loadFxml("chat"));
    scene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
    return;
  }
}
