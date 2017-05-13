package gameController;

import gameModel.HighScoreHandler.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static gameModel.HighScoreHandler.addNewScore;
import static gameModel.HighScoreHandler.checkIfPlayerScoreIsHighScore;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 *
 */
public class GameOverPopUpUI_Controller {

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

    public static void setValues(PlayerScore score, String loseMessage) {
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
        if(inputRequired) {
            if(playerName == null || playerName.trim().isEmpty()) {
                inputNameField.setText("");
                inputNameField.setPromptText("Please input your name");
            } else if(playerName.length() > MAX_PLAYER_NAME_LENGTH) {
                inputNameField.setText("");
                inputNameField.setPromptText("Please input a name no longer than " + MAX_PLAYER_NAME_LENGTH + " characters");
            } else {
                playerScoreReference.setName(playerName.trim());
                addNewScore(playerScoreReference);
                inputRequired = false;
            }
        }
        if(!inputRequired){
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.hide();
        }
    }
}
