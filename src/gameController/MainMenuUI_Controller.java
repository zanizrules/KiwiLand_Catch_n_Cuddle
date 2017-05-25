package gameController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 26/04/2017.
 * Insert Description...
 */
public class MainMenuUI_Controller {

    @FXML
    private Button playButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Button highscoresButton;

    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {

        if(event.getSource() == playButton) {
            loadFXML("/gameView/KiwiLandUI.fxml");
        } else if(event.getSource() == highscoresButton) {
            loadFXML("/gameView/HighScoresUI.fxml");
        } else if(event.getSource() == instructionsButton) {
            loadFXML("/gameView/InstructionsUI.fxml");

        }
    }

    private void loadFXML(String filePath) throws IOException {
        Stage stage;
        Region root;
        double sceneWidth = 1280;
        double sceneHeight = 960;

        // get reference to the button's stage
        stage = (Stage) playButton.getScene().getWindow();
        // load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(filePath));

        /*
            Source for the idea behind the following resize code:
            http://gillius.org/blog/2013/02/javafx-window-scaling-on-resize.html
         */
        if(root.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
            root.setPrefWidth(sceneWidth);
        } else sceneWidth = root.getPrefWidth();
        if(root.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
            root.setPrefHeight(sceneHeight);
        } else sceneHeight = root.getPrefHeight();

        Group group = new Group(root);
        StackPane rootPane = new StackPane(group);
        Scene scene = new Scene(rootPane);
        group.scaleXProperty().bind(scene.widthProperty().divide(sceneWidth));
        group.scaleYProperty().bind(scene.heightProperty().divide(sceneHeight));
        stage.setScene(scene);
        stage.show();
    }
}
