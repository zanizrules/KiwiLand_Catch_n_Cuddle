package gameController;

import gameController.HighScoreController.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 * Highscore controller that handles populating the UI with highscores, and returning to the main menu.
 */
public class HighScoreUI_Controller {
    @FXML
    private Button returnButton;
    @FXML
    private VBox highScoreNamesList;
    @FXML
    private VBox highScoreCuddledList;
    @FXML
    private VBox highScoreCaughtList;
    @FXML
    private VBox highScoreTotalList;

    private static final int MAX_HIGH_SCORES_AMOUNT = 10;

    @FXML
    public void initialize() {
        ObservableList<Node> nameList = highScoreNamesList.getChildren();
        ObservableList<Node> cuddleList = highScoreCuddledList.getChildren();
        ObservableList<Node> predatorList = highScoreCaughtList.getChildren();
        ObservableList<Node> scoreList = highScoreTotalList.getChildren();

        int index = 0;
        for(PlayerScore score : HighScoreController.getHighScores()) {
            if(index < MAX_HIGH_SCORES_AMOUNT) {
                Label nameLabel = (Label) nameList.get(index);
                Label cuddleLabel = (Label) cuddleList.get(index);
                Label predatorLabel = (Label) predatorList.get(index);
                Label scoreLabel = (Label) scoreList.get(index);

                nameLabel.setText(score.getName());
                cuddleLabel.setText("" + score.kiwisCuddled);
                predatorLabel.setText("" + score.predatorsCaptured);
                scoreLabel.setText("" + score.totalScore);

                index++;
            } else break;
        }
    }

    @FXML
    public void returnButtonClick() throws IOException { // Called when return button is clicked
        Main.loadMenu((Stage) returnButton.getScene().getWindow());
    }
}
