package gameController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 26/04/2017.
 * Insert Description...
 */
public class mainMenuUI_Controller {

    @FXML
    private Button playButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Button highscoresButton;

    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {

        if(event.getSource() == playButton) {
            loadFXML("../gameView/KiwiLandUI.fxml");
        } else if(event.getSource() == highscoresButton) {
            // TODO: link to highscores
            loadFXML("../gameView/");
        } else if(event.getSource() == instructionsButton) {
            // TODO: link to instructions
            loadFXML("../gameView/");
        }
    }

    private void loadFXML(String filePath) throws IOException {
        Stage stage;
        Parent root;
        // get reference to the button's stage
        stage = (Stage) playButton.getScene().getWindow();
        // load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource(filePath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
