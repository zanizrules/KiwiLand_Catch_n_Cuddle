package gameModel.gameObjects;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 12/05/2017.
 * This class was made to store all food image locations so that they were easy to access and edit.
 */
public enum FOOD_TYPE {

    // Food
    APPLE("images/apple.png"),
    MUESLI_BAR("images/mueslibar.png"),
    ORANGE_JUICE("images/orangejuice.png"),
    SANDWICH("images/sandwich.png");

    private final String imageLoc;

    FOOD_TYPE(String imageLoc) { this.imageLoc = imageLoc; }

    public String getImageLoc() { return imageLoc; }
}
