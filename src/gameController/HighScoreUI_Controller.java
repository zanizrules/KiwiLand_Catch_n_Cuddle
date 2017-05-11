package gameController;

import gameModel.HighScoresHandler;
import gameModel.SingleHighScore;
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
import java.util.ArrayList;

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

    private static final HighScoresHandler highScoreHandler = new HighScoresHandler();

    @FXML
    public void initialize() {
        ArrayList<SingleHighScore> scores = highScoreHandler.getHighScores();
        ObservableList<Node> nameList = highScoreNamesList.getChildren();
        ObservableList<Node> cuddleList = highScoreCuddledList.getChildren();
        ObservableList<Node> predatorList = highScoreCaughtList.getChildren();
        ObservableList<Node> scoreList = highScoreTotalList.getChildren();

        for(int i = 0; i < nameList.size() && i < scores.size(); i++) {
            Label nameLabel = (Label) nameList.get(i);
            Label cuddleLabel = (Label) cuddleList.get(i);
            Label predatorLabel = (Label) predatorList.get(i);
            Label scoreLabel = (Label) scoreList.get(i);

            nameLabel.setText(scores.get(i).getName());
            cuddleLabel.setText("" + scores.get(i).getKiwisCuddled());
            predatorLabel.setText("" + scores.get(i).getPredatorsCaptured());
            scoreLabel.setText("" + scores.get(i).getTotalScore());
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
