package gameModel.gameObjects;

import gameModel.Position;

/**
 * This class represents food that can be found on the island
 * and supplies ENERGY when being consumed (used) by the player.
 */
public class Food extends Item {
    private final double ENERGY;
    private FOOD_TYPE foodType;

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
    public Food(Position pos, String name, String description, double weight, double size, double energy, FOOD_TYPE type) {
        super(pos, name, description + ", it will give you " +energy + " stamina if consumed", weight, size);
        this.ENERGY = energy;
        foodType = type;
        setImage();
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

    @Override
    protected void setImage() {
        super.setImage(foodType.getImageLoc());
    }
}
