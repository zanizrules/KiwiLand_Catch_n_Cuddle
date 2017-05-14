
package gameModel.gameObjects;

import gameModel.Position;

/**
 * Fauna at this point represents any species that is not a kiwi or a predator on the island.
 * If we need additional endangered species this class should have descendant classes created.
 */
public class Fauna extends Occupant {
    private final ANIMAL_TYPE animalType;

    /**
     * Constructor for objects of class Endangered
     * @param pos the position of the kiwi
     * @param name the name of the kiwi
     * @param description a longer description of the kiwi
     */
    public Fauna(Position pos, String name, String description, ANIMAL_TYPE type) {
        super(pos, name, description);
        animalType = type;
        setImage();
    }

    @Override
    protected void setImage() {
        super.setImage(animalType.getImageLoc());
    }

    @Override
    public String getStringRepresentation() {
        return "F";
    }

    public ANIMAL_TYPE getAnimalType() {
        return animalType;
    }
}
