package gameModel.gameObjects;

import gameModel.Position;
import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.gameObjects.Fauna;

/**
 * Predator represents a predator on the island.
 * If more specific behaviour is required for particular predators, descendants
 * for this class should be created
 */
public class Predator extends Fauna {
    private final String predatorFact;

    /**
     * Constructor for objects of class Predator
     *
     * @param pos         the position of the predator object
     * @param name        the name of the predator object
     * @param description a longer description of the predator object
     */
    public Predator(Position pos, String name, String description, String fact, ANIMAL_TYPE type) {
        super(pos, name, description, type);
        predatorFact = fact;
        setImage();
    }

    public String getPredatorFact() {
        return predatorFact;
    }

    @Override
    public String getStringRepresentation() {
        return "P";
    }
}
