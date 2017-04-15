package gameModel;

/**
 * This class represents a tool that can be found on the island and gives the player any sort of advantage.
 */
public class Tool extends Item {
    private boolean broken;

    public Tool(Position pos, String name, String description, double weight, double size) {
        super(pos, name, description, weight, size);
        this.broken = false;
    }

    public void setBroken() {
        broken = true;
    }

    public void fix() {
        broken = false;
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean isTrap() {
        String name = getName();
        return name.equalsIgnoreCase("Trap");
    }

    public boolean isScrewdriver() {
        String name = getName();
        return name.equalsIgnoreCase("Screwdriver");
    }

    @Override
    public String getStringRepresentation() {
        return "T";
    }

    @Override
    public void setImage() {
        image = ""; //TODO: set
    }
}
