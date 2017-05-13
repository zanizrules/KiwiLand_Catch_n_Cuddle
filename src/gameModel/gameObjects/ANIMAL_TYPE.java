package gameModel.gameObjects;

/**
 * Author: Shane Birdsall
 * ID: 14870204
 * Date: 12/05/2017.
 * This class was made to store all animal image locations so that they were easy to access and edit.
 */
public enum ANIMAL_TYPE {

    // Predators
    RAT("images/rat2.png"), KIORE("images/rat.png"),
    CAT("images/cat.png"), STOAT("images/stoat.png"),
    POSSUM("images/possum.png"),

    // Friendly Fauna
    CRAB("images/crab.png"), FERNBIRD("images/fernbird.png"),
    HERON("images/heron.png"), OYSTER_CATCHER("images/oystercatcher.png"),
    ROBIN("images/robin.png"), TUI("images/tui.png"),

    // Kiwi
    KIWI("images/kiwi.png");

    private final String imageLoc;

    ANIMAL_TYPE(String imageLoc) {
        this.imageLoc = imageLoc;
    }

    public String getImageLoc() {
        return imageLoc;
    }
}
