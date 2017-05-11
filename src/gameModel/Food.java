package gameModel;

/**
 * This class represents food that can be found on the island
 * and supplies ENERGY when being consumed (used) by the player.
 */
public abstract class Food extends Item {
    private final double ENERGY;

    /**
     * Construct a food object with known attributes.
     *
     * @param pos         the position of the food object
     * @param name        the name of the food object
     * @param description a longer description of the food object
     * @param weight      the weight of the food object
     * @param size        the size of the food object
     * @param energy      stamina contribution of the food object
     *                    when the player uses the object
     */
    Food(Position pos, String name, String description, double weight, double size, double energy) {
        super(pos, name, description, weight, size);
        this.ENERGY = energy;
    }


    /**
     * Gets the ENERGY of the food.
     *
     * @return the ENERGY of the food
     */
    public double getEnergy() {
        return this.ENERGY;
    }

    /**
     * @return string representation of food
     */
    @Override
    public String getStringRepresentation() {
        return "E";
    }
}
