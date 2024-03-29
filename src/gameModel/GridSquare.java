package gameModel;

import gameModel.gameObjects.Occupant;
import gameModel.gameObjects.Player;
import gameModel.gameObjects.Predator;
import javafx.scene.image.Image;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Class to represent a grid square on the island.
 */
public class GridSquare {
    private static final int MAX_OCCUPANTS = 3;
    private Terrain terrain;
    private boolean visible;
    private boolean explored;
    private Player player;
    private final CopyOnWriteArraySet<Occupant> occupants;

    /**
     * Creates a new GridSquare instance.
     *
     * @param terrain the terrain of the grid square
     */
    public GridSquare(Terrain terrain) {
        this.terrain = terrain;
        this.explored = false;
        this.visible = false;
        this.occupants = new CopyOnWriteArraySet<>();
        this.player = null;

    }

    /**
     * Gets the terrain of the grid square.
     *
     * @return the terrain of the grid square
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Check if this grid square has already been explored (ie visited by player).
     *
     * @return true if square has been explored, false if not.
     */
    public boolean isExplored() {
        return this.explored;
    }

    /**
     * Check if this grid square is visible to the player.
     * Squares are visible if they:
     * are visitable and adjacent to the player's current position
     * already explored by the player
     *
     * @return true is the grid square is visible to the player,
     * false if not.
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Check if the player is in this square.
     *
     * @return true if the player is in square, false if not.
     */
    public boolean hasPlayer() {
        return (this.player != null);
    }

    /**
     * Returns a string representation of the occupants.
     *
     * @return string that combines strings for all occupants
     */
    public String getOccupantStringRepresentation() {
        StringBuilder result = new StringBuilder();
        for (Occupant occupant : occupants) {
            result.append(occupant.getStringRepresentation());
        }
        return result.toString();
    }

    ArrayList<Image> getOccupantImages() {
        ArrayList<Image> result = new ArrayList<>();
        for (Occupant occupant : occupants) {
            result.add(occupant.getImage());
        }
        return result;
    }

    /**
     * Returns a string representation of the terrain.
     *
     * @return string that represents the terrains
     */
    public String getTerrainStringRepresentation() {
        return terrain.getStringRepresentation();
    }

    /**
     * Checks if this grid square contains a specific occupant.
     *
     * @param occupant the occupant to check
     * @return true if the square contains the occupant, false if not
     */
    public boolean hasOccupant(Occupant occupant) {
        return occupants.contains(occupant);
    }

    /**
     * Gets the occupants of the grid square as an array.
     *
     * @return the occupants of the grid square as an array
     */
    public CopyOnWriteArraySet<Occupant> getOccupants() {
        return occupants;
    }

    /**
     * Sets the terrain of the grid square.
     *
     * @param terrain the new terrain
     * @throws InvalidParameterException if the terrain is null
     */
    public void setTerrain(Terrain terrain) {
        if (terrain == null) {
            throw new InvalidParameterException("Terrain cannot be null");
        }
        this.terrain = terrain;
    }

    /**
     * Marks this grid square as explored.
     */
    public void setExplored() {
        this.explored = true;
    }

    /**
     * Marks this grid square as being visible or invisible to the player.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Sets the player on the grid square.
     *
     * @param player the player on the square
     *               or null if there is no player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Adds an occupant to a GridSquare if the occupant is not already there
     * and if the capacity of the grid square is not yet exceeded.
     *
     * @param occupant the occupant to add
     * @return true if successfully added
     */
    public boolean addOccupant(Occupant occupant) {
        boolean success = false;
        boolean validNewOccupant = occupant != null;
        boolean enoughRoom = occupants.size() < MAX_OCCUPANTS;
        if (validNewOccupant && enoughRoom) {
            success = occupants.add(occupant);
        }
        return success;
    }

    /**
     * Removes an occupant if it is on that GridSquare.
     *
     * @param occupant the occupant to be removed
     * @return true if occupant was removed,
     * false if it was not in there (or not valid)
     */
    public boolean removeOccupant(Occupant occupant) {
        boolean success = false;
        boolean validOccupant = occupant != null;
        if (validOccupant) {
            success = occupants.remove(occupant);
        }
        return success;
    }
}
