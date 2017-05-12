package gameController;

import gameModel.HighScoreHandler;
import gameModel.HighScoreHandler.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 *
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

    private static final HighScoreHandler highScoreHandler = new HighScoreHandler();

    @FXML
    public void initialize() {
        ObservableList<Node> nameList = highScoreNamesList.getChildren();
        ObservableList<Node> cuddleList = highScoreCuddledList.getChildren();
        ObservableList<Node> predatorList = highScoreCaughtList.getChildren();
        ObservableList<Node> scoreList = highScoreTotalList.getChildren();

        PriorityQueue<PlayerScore> scores = highScoreHandler.getHighScores();
        int index = 0;
        for(PlayerScore score : scores) {
            Label nameLabel = (Label) nameList.get(index);
            Label cuddleLabel = (Label) cuddleList.get(index);
            Label predatorLabel = (Label) predatorList.get(index);
            Label scoreLabel = (Label) scoreList.get(index);

            nameLabel.setText(score.name);
            cuddleLabel.setText("" + score.kiwisCuddled);
            predatorLabel.setText("" + score.predatorsCaptured);
            scoreLabel.setText("" + score.totalScore);

            index++;
        }
    }

    @FXML
    public void returnButtonClick() throws IOException { // Called when return button is clicked
            Stage stage = (Stage) returnButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../gameView/mainMenuUI.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
    }
}
