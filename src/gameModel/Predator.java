package gameModel;

/**
 * Predator represents a predator on the island.
 * If more specific behaviour is required for particular predators, descendants
 * for this class should be created
 */
public abstract class Predator extends Fauna {
    private final String predatorFact;
    /**
     * Constructor for objects of class Predator
     *
     * @param pos         the position of the predator object
     * @param name        the name of the predator object
     * @param description a longer description of the predator object
     */
    Predator(Position pos, String name, String description, String fact) {
        super(pos, name, description);
        predatorFact = fact;
    }

    String getPredatorFact() {
        return predatorFact;
    }

    @Override
    public String getStringRepresentation() {
        return "P";
    }
}
