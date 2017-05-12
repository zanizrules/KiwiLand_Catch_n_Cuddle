package gameModel.gameObjects;

import gameModel.Position;
import gameModel.gameObjects.Item;

/**
 * This class represents a tool that can be found on the island and gives the player any sort of advantage.
 */
public abstract class Tool extends Item {
    private boolean broken;

    Tool(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description, weight, size);
        broken = false;
    }

    private void setBroken(boolean value) {
        broken = value;
        setImage();
    }
    public void setBroken() {
        setBroken(true);
    }
    public void fix() {
        setBroken(false);
    }

    public boolean isBroken() {
        return broken;
    }

    @Override
    public String getStringRepresentation() {
        return "T";
    }
}
