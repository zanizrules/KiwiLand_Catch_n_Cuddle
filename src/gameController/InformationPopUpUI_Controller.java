package gameController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 27/04/2017.
 *
 */
public class InformationPopUpUI_Controller {
    @FXML
    private ImageView imageDisplay;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button okButton;

    private static String currentDescription, currentName;
    private static Image currentImage;

    @FXML
    public void initialize() {
        imageDisplay.setImage(currentImage);
        nameLabel.setText(currentName);
        descriptionLabel.setText(currentDescription);
    }

    public static void setValues(Image image, String name, String description) {
        currentImage = image;
        currentName = name;
        currentDescription = description;
    }

    @FXML
    public void okButtonClick() { // Called when ok button is clicked
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.hide();
    }
}
