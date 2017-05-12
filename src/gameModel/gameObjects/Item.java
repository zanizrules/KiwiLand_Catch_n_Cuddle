package gameModel.gameObjects;

import gameModel.Position;

/**
 * This class represents an item that can be found on the island.
 */
public abstract class Item extends Occupant {
    private final double WEIGHT;
    private final double SIZE;

    Item(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description);
        this.WEIGHT = weight;
        this.SIZE = size;
    }

    /**
     * Gets the WEIGHT of the item
     *
     * @return the WEIGHT of the item
     */
    public double getWeight() {
        return this.WEIGHT;
    }

    /**
     * Gets the SIZE of the item
     *
     * @return the SIZE of the item
     */
    public double getSize() {
        return this.SIZE;
    }


    /**
     * Is it OK to pick up and carry this item?
     * Items with SIZE > 0 can be carried.
     *
     * @return true if item can be carried, false if not
     */
    public boolean isOkToCarry() {
        return SIZE > 0;
    }

}
