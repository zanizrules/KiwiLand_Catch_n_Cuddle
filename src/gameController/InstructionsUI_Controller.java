package gameController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 */
public class InstructionsUI_Controller {
    @FXML
    private Button returnButton;

    @FXML
    public void returnButtonClick() throws IOException { // Called when return button is clicked
        Main.loadMenu((Stage) returnButton.getScene().getWindow());
    }
}
