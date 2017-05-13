package gameController;

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
 * Date: 27/04/2017.
 *
 */
public class InstructionsUI_Controller {
    @FXML
    private Button restartButton;

    @FXML
    public void returnButtonClick() throws IOException { // Called when return button is clicked
            Stage stage = (Stage) restartButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../gameView/mainMenuUI.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
    }
}
