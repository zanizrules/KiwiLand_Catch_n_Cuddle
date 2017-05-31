package gameController;

import gameController.HighScoreController.PlayerScore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

import static gameController.HighScoreController.addNewScore;
import static gameController.HighScoreController.checkIfPlayerScoreIsHighScore;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 * Controller for the games game over pop ups that also handle user input for high scores.
 */
public class GameOverPopUpUI_Controller {
    private static Stage gameStageReference;
    @FXML
    private Label titleLabel;
    @FXML
    private Label topInfoLabel;
    @FXML
    private Label bottomInfoLabel;
    @FXML
    private Label inputNameLabel;
    @FXML
    private TextField inputNameField;
    @FXML
    private Button okButton;

    private static final int MAX_PLAYER_NAME_LENGTH = 7;
    private static String popUpTitle, popUpTitleDescription, popUpDescription;
    private static boolean inputRequired;
    private static PlayerScore playerScoreReference;

    @FXML
    public void initialize() {
        titleLabel.setText(popUpTitle);
        topInfoLabel.setText(popUpTitleDescription);
        bottomInfoLabel.setText(popUpDescription);
        inputNameLabel.visibleProperty().setValue(inputRequired);
        inputNameField.visibleProperty().setValue(inputRequired);
    }

    public static void setValues(Stage stage, PlayerScore score, String loseMessage) {
        gameStageReference = stage;
        playerScoreReference = score;
        inputRequired = checkIfPlayerScoreIsHighScore(score);
        popUpTitle = "You scored " + score.totalScore + " points!";
        popUpTitleDescription = loseMessage + "\n" + score.toString();
        popUpDescription = inputRequired ? "You have exceeded all expectations and " +
                "achieved a spot on the kiwi conservation hall of fame!!!" :
                "Try playing again to rank in the top ten and have your " +
                        "name featured in the kiwi conservation hall of fame!";
    }

    @FXML
    public void okButtonClick() { // Called when ok button is clicked
        String playerName = inputNameField.getText();
        if (inputRequired) {
            if (playerName == null || playerName.trim().isEmpty()) {
                inputNameField.setText("");
                inputNameField.setPromptText("Please input your name");
            } else if (playerName.length() > MAX_PLAYER_NAME_LENGTH) {
                inputNameField.setText("");
                inputNameField.setPromptText("Please input a name no longer than " + MAX_PLAYER_NAME_LENGTH + " characters");
            } else {
                playerScoreReference.setName(playerName.trim());
                addNewScore(playerScoreReference);
                inputRequired = false;
                okButton.getScene().getWindow().hide();
                try { // Switch to high-scores screen
                    if(gameStageReference != null) {
                        Main.loadScene(gameStageReference, "/gameView/HighScoresUI.fxml");
                        System.out.println("hmm");
                    } else System.out.println("Was null");
                } catch (IOException ignored) {}
            }
        } else {
            okButton.getScene().getWindow().hide();
        }
    }
}
