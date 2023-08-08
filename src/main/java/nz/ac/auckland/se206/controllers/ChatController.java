package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class ChatController {
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button sendButton;
  @FXML private Label timerLabelChat;
  private Timeline timeline;
  private ChatMessage message;

  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @FXML
  public void initialize() throws ApiProxyException {
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  updateTimerLabel();
                }));
    timeline.setCycleCount(120);
    timeline.setOnFinished(event -> handleTimerExpired());
    updateTimerLabel();
    timeline.play();
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    runGpt(
        new ChatMessage(
            "user", GptPromptEngineering.getRiddleWithGivenWord(GameState.itemToChoose.getId())));
  }

  private void updateTimerLabel() {
    int minutes = GameState.secondsRemaining / 60;
    int seconds = GameState.secondsRemaining % 60;
    timerLabelChat.setText(String.format("%d:%02d", minutes, seconds));
  }

  private void handleTimerExpired() {
    Scene scene = sendButton.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.START_SCREEN));
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);

    Task<Void> callGpt =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            try {
              ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
              Choice result = chatCompletionResult.getChoices().iterator().next();
              chatCompletionRequest.addMessage(result.getChatMessage());
              message = result.getChatMessage();
              Platform.runLater(
                  () -> {
                    appendChatMessage(result.getChatMessage());
                  });
            } catch (ApiProxyException e) {
              // TODO handle exception appropriately
              e.printStackTrace();
              return null;
            }
            return null;
          }
        };
    Thread thread = new Thread(callGpt);
    thread.start();
    return message;
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @FXML
  private void onSendMessage(ActionEvent event)
      throws ApiProxyException, IOException, InterruptedException, ExecutionException {
    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    inputText.clear();
    ChatMessage msg = new ChatMessage("user", message);
    appendChatMessage(msg);
    ChatMessage lastMsg = runGpt(msg);
    if (lastMsg.getRole().equals("assistant") && lastMsg.getContent().startsWith("Correct")) {
      GameState.isRiddleResolved = true;
    }
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    Scene scene = sendButton.getScene();
    scene.setRoot(SceneManager.getUiRoot(AppUi.ROOM));
  }
}
