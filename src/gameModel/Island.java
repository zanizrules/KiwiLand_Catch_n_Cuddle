package gameModel;

import javafx.geometry.Pos;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A class to represent an island in the world on which the game is played.
 */
public class Island {
    private final int numRows;
    private final int numColumns;
    private GridSquare[][] islandGrid;
    private Position previousPlayerPos;


    /**
     * Initial island constructor.
     *
     * @param numRows    the amount of rows on the island
     * @param numColumns the amount of columns on the island
     */
    public Island(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.previousPlayerPos = null;
        initialiseIsland();
    }

    /**
     * Get the value of numRows
     *
     * @return the value of numRows
     */
    public int getNumRows() {
        return numRows;
    }


    /**
     * Get the value of numColumns
     *
     * @return the value of numColumns
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * What is terrain here?
     *
     * @param position to check
     * @return terrain for position
     */
    Terrain getTerrain(Position position) {
        GridSquare square = getGridSquare(position);
        return square.getTerrain();
    }

    /**
     * G position in this direction
     *
     * @param position  starting position
     * @param direction to go
     * @return new position
     */
    public Position getNewPosition(Position position, MoveDirection direction) {
        assert position != null;
        assert direction != null;
        return position.getNewPosition(direction);
    }

    /**
     * Is this square visible
     *
     * @return true if visible
     */
    public boolean isVisible(Position position) {
        GridSquare square = getGridSquare(position);
        return square.isVisible();
    }

    /**
     * Is this square explored
     *
     * @return true if explored
     */
    public boolean isExplored(Position position) {
        GridSquare square = getGridSquare(position);
        return square.isExplored();
    }

    /**
     * Is player in this position?
     *
     * @return true if player in this position
     */
    boolean hasPlayer(Position position) {
        GridSquare square = getGridSquare(position);
        return square.hasPlayer();
    }

    /**
     * Is this occupant in this position
     *
     * @param position to check
     * @param occupant to check
     * @return true if in this position
     */
    public boolean hasOccupant(Position position, Occupant occupant) {
        GridSquare square = getGridSquare(position);
        return square.hasOccupant(occupant);
    }

    public boolean hasOccupantWithinArea(Position centrePosition, Occupant occupant) {
        boolean hasOccupant;
        int rowStart = centrePosition.getRow() - 1;
        int rowEnd = centrePosition.getRow() + 1;
        int colStart = centrePosition.getColumn()-1;
        int colEnd = centrePosition.getColumn()+1;

        if(rowStart < 0) { rowStart = 0; }
        if(colStart < 0) { colStart = 0; }
        if(rowEnd > numRows) { rowEnd = numRows -1; }
        if(colEnd > numColumns) { colEnd = numColumns -1; }
        if(hasHazardOnPosition(centrePosition) == true){return true;}
        for(int i = rowStart; i < rowEnd; i++) {
            for(int j = colStart; j < colEnd; j++) {
                Position pos = new Position(this, i, j);
                hasOccupant = getGridSquare(pos).hasOccupant(occupant);
                if(hasOccupant) {
                    return true; // hasOccupant = TRUE
                }
            }
        } return false; // hasOccupant = FALSE
    }

    public boolean hasHazardOnPosition(Position pos){
        String occupantString = getGridSquare(pos).getOccupantStringRepresentation();
        if(occupantString == "H") {
            System.out.println("Space had a hazard");
            return true;
        }else{
            System.out.println("No Hazard");
            return false;
        }
    }

    /**
     * Gets the occupants of position as an array.
     *
     * @param position to use
     * @return the occupants of this position
     */
    CopyOnWriteArraySet<Occupant> getOccupants(Position position) {
        return getGridSquare(position).getOccupants();
    }

    /**
     * Get string for occupants of this position
     *
     * @return string representing occupants
     */
    String getOccupantStringRepresentation(Position position) {
        GridSquare square = getGridSquare(position);
        return square.getOccupantStringRepresentation();
    }

    ArrayList<Image> getOccupantImage(Position position) {
        GridSquare square = getGridSquare(position);
        return square.getOccupantImages();
    }

    /**
     * Checks if this position contains a predator.
     *
     * @param position which position
     * @return true if contains a predator, false if not
     */
    public boolean hasPredator(Position position) {
        GridSquare square = getGridSquare(position);
        CopyOnWriteArraySet<Occupant> occupants = square.getOccupants();
        boolean isPredator = false;
        if (!occupants.isEmpty()) {
            Iterator<Occupant> it = occupants.iterator();
            while (it.hasNext() && !isPredator) {
                isPredator = it.next() instanceof Predator;
            }
        }
        return isPredator;
    }

    /**
     * Set terrain for this position
     *
     * @param position to set
     * @param terrain  for this position
     */
    void setTerrain(Position position, Terrain terrain) {
        GridSquare square = getGridSquare(position);
        square.setTerrain(terrain);
    }

    /**
     * Update the grid and the explored & visible state of the grid to reflect new position of player.
     */
    public void updatePlayerPosition(Player player) {
        // the grid square with the player on it is now explored...
        Position position = player.getPosition();
        getGridSquare(position).setExplored();
        //... and has the player on it
        getGridSquare(position).setPlayer(player);

        // remove player from previous square
        if (previousPlayerPos != null) {
            getGridSquare(previousPlayerPos).setPlayer(null);
        }

        // add visibility to all new adjacent squares
        setVisible(position.getNewPosition(MoveDirection.NORTH));
        setVisible(position.getNewPosition(MoveDirection.EAST));
        setVisible(position.getNewPosition(MoveDirection.SOUTH));
        setVisible(position.getNewPosition(MoveDirection.WEST));

        // remember the new player position
        previousPlayerPos = position;
    }

    /**
     * Attempts to add an occupant to a specified position on the island.
     *
     * @param position the position to add the occupant
     * @param occupant the occupant to add
     * @return true if occupant was successfully added, false if not
     */
    public boolean addOccupant(Position position, Occupant occupant) {
        boolean success = false;
        if (position.isOnIsland() && occupant != null) {
            GridSquare gridSquare = getGridSquare(position);
            success = gridSquare.addOccupant(occupant);
        }
        if (success) {  // Can fail if square already full or occupant already there
            occupant.setPosition(position); //update the occupants address
        }
        return success;
    }

    /**
     * Attempts to remove an occupant from a specified position on the island.
     *
     * @param position the position where to remove the occupant
     * @param occupant the occupant to remove
     * @return true if occupant was successfully removed, false if not
     */
    public boolean removeOccupant(Position position, Occupant occupant) {
        boolean success = false;
        if (position.isOnIsland() && occupant != null) {
            GridSquare gridSquare = getGridSquare(position);
            success = gridSquare.removeOccupant(occupant);
        }
        if (success) {
            //update the occupants address to the "not on island position"
            occupant.setPosition(Position.NOT_ON_ISLAND);
        }
        return success;
    }

    /**
     * Get the first predator that is in this position
     *
     * @param position which position
     * @return predator or null if there is not one here.
     */
    public Predator getPredator(Position position) {
        GridSquare square = getGridSquare(position);
        CopyOnWriteArraySet<Occupant> occupants = square.getOccupants();
        Predator predator = null;
        if (!occupants.isEmpty()) {
            Iterator<Occupant> it = occupants.iterator();
            while (it.hasNext() && predator == null) {
                Occupant o = it.next();
                if (o instanceof Predator) {
                    predator = (Predator) o;
                }
            }
        }
        return predator;
    }

    /**
     * Produces a textual representation of the island on the console.
     * This exists  for debugging purposes during early development.
     */
    void draw() {
        final int CELL_SIZE = 4;

        // create the horizontal line as a string
        String horizontalLine = "-";
        for (int col = 0; col < this.numColumns; col++) {
            for (int i = 0; i < CELL_SIZE; i++) {
                horizontalLine += "-";
            }
            horizontalLine += "-";
        }

        // print the content
        for (int row = 0; row < this.numRows; row++) {
            String rowOccupant = "|";
            String rowTerrain = "|";
            for (int col = 0; col < this.numColumns; col++) {
                GridSquare g = islandGrid[row][col];
                // create string with occupants
                String cellOccupant = g.hasPlayer() ? "@" : " ";
                cellOccupant += g.getOccupantStringRepresentation();
                for (int i = cellOccupant.length(); i < CELL_SIZE; i++) {
                    cellOccupant += " ";
                }
                rowOccupant += cellOccupant + "|";

                // create string with terrain
                String cellTerrain = "";
                for (int i = 0; i < CELL_SIZE; i++) {
                    cellTerrain += g.getTerrainStringRepresentation();
                }
                rowTerrain += cellTerrain + "|";
            }
            System.out.println(horizontalLine);
            System.out.println(rowOccupant);
            System.out.println(rowTerrain);
        }
        System.out.println(horizontalLine);
    }

    /**
     * Populates the island grid with GridSquare objects
     * Terrain defaults to water. Actual terrain details will be updated later
     * when data from file read.
     */
    private void initialiseIsland() {
        islandGrid = new GridSquare[numRows][numColumns];
        for (int row = 0; row < this.numRows; row++) {
            for (int column = 0; column < this.numColumns; column++) {
                GridSquare square = new GridSquare(Terrain.WATER);
                islandGrid[row][column] = square;
            }
        }
    }

    /**
     * Private convenience method to change the visibility of grid squares.
     *
     * @param position the position to change
     */
    private void setVisible(Position position) {
        if ((position != null) && position.isOnIsland()) {
            islandGrid[position.getRow()][position.getColumn()].setVisible();
        }
    }

    /**
     * Get a grid square with a particular position.
     *
     * @param position of the square
     * @return Square with this position
     */
    private GridSquare getGridSquare(Position position) {
        GridSquare result = null;
        if (position.isOnIsland()) {
            result = islandGrid[position.getRow()][position.getColumn()];
        }
        return result;
    }
}
