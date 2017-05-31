package gameModel.gameObjects;

import gameModel.Position;
import gameModel.Terrain;
import javafx.scene.image.Image;

/**
 * Abstract base class for occupants that inhabit Kiwi Island.
 */
public abstract class Occupant {
    private Position position;
    private Position previousPosition;
    protected String name;
    String description;
    private Image image;

    /**
     * Construct an occupant for a known position & name.
     *
     * @param position    the position of the occupant
     * @param name        the name of the occupant
     * @param description a longer description
     */
    Occupant(Position position, String name, String description) {
        this.position = position;
        previousPosition = position;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the position of the occupant.
     *
     * @return the position of the occupant
     */
    public Position getPosition() {
        return this.position;
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }

    public void setPreviousPosition(Position pos) {
        previousPosition = pos;
    }

    /**
     * Changes the position of the occupant.
     *
     * @param newPosition the new position
     */
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Gets the occupant's name.
     *
     * @return the name of the occupant
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description for the item.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the occupant's name for display.
     *
     * @return the occupant's name
     */
    @Override
    public String toString() {
        return getName();
    }


    /**
     * Gets a string representation of the occupant.
     * Used for interpretation of file content
     *
     * @return the string representation of the occupant
     */
    public abstract String getStringRepresentation();

    void setImage(String imageLoc) {
        try {
            image = new Image(getClass().getResource(imageLoc).toExternalForm());
        } catch (RuntimeException ignore) {}
    }

    protected abstract void setImage();

    public void moveToPosition(Position newPosition, Terrain terrain) {
        if ((position == null)) {
            throw new IllegalArgumentException("Null parameters");
        }
        this.position = newPosition;
    }

    public Image getImage() {
        return image;
    }
}
