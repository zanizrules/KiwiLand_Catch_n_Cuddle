package gameController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 */
public class CharacterSelectUI_Controller implements Initializable {

    @FXML
    private Button selectButton;
    @FXML
    private Button returnButton;
    @FXML
    private RadioButton character1;
    @FXML
    private RadioButton character2;
    @FXML
    private RadioButton character3;
    @FXML
    private RadioButton character4;
    @FXML
    private RadioButton character5;
    @FXML
    private RadioButton character6;

    private ToggleGroup characterRadioButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        characterRadioButtons = new ToggleGroup();
        character1.setToggleGroup(characterRadioButtons);
        character1.setUserData("Abigail");

        character2.setToggleGroup(characterRadioButtons);
        character2.setUserData("Gus");

        character3.setToggleGroup(characterRadioButtons);
        character3.setUserData("Penny");

        character4.setToggleGroup(characterRadioButtons);
        character4.setUserData("Elliot");

        character5.setToggleGroup(characterRadioButtons);
        character5.setUserData("Alex");

        character6.setToggleGroup(characterRadioButtons);
        character6.setUserData("Demitrius");
    }

    @FXML
    public void returnButtonClick() throws IOException { // Called when return button is clicked
        Main.loadMenu((Stage) returnButton.getScene().getWindow());
    }

    @FXML
    public void selectButtonClick() throws IOException { // Called when return button is clicked
        if(characterRadioButtons.getSelectedToggle() != null) {
            KiwiLandUI_Controller.setSelectedCharacter(
                    (String) characterRadioButtons.getSelectedToggle().getUserData());
        }
        Main.loadScene((Stage) selectButton.getScene().getWindow(), "/gameView/KiwiLandUI.fxml");
    }
}
