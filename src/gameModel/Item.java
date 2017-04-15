package gameModel;


/**
 * This class represents an item that can be found on the island.
 */
public abstract class Item extends Occupant {
    private double weight;
    private double size;

    Item(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description);
        this.weight = weight;
        this.size = size;
    }

    /**
     * Gets the weight of the item
     *
     * @return the weight of the item
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Gets the size of the item
     *
     * @return the size of the item
     */
    public double getSize() {
        return this.size;
    }


    /**
     * Is it OK to pick up and carry this item?
     * Items with size > 0 can be carried.
     *
     * @return true if item can be carried, false if not
     */
    public boolean isOkToCarry() {
        return size > 0;
    }

}
