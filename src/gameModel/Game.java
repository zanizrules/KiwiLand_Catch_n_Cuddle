package gameModel;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the class that knows the Kiwi Island game rules and state
 * and enforces those rules.
 */
// TODO: re comment
public class Game {
    private Island island;
    private Player player;
    private GameState state;
    private int kiwiCount; // Change to keep track of score instead
    private int score;
    private int totalPredators;
    private int totalKiwis;
    private int predatorsTrapped;
    private Set<GameEventListener> eventListeners;

    private final double MIN_REQUIRED_CATCH = 0.8;

    private String winMessage = "";
    private String loseMessage = "";
    private String playerMessage = "";

    //Constants shared with UI to provide player data
    public static final int STAMINA_INDEX = 0;
    public static final int MAXSTAMINA_INDEX = 1;
    public static final int MAXWEIGHT_INDEX = 2;
    public static final int WEIGHT_INDEX = 3;
    public static final int MAXSIZE_INDEX = 4;
    public static final int SIZE_INDEX = 5;

    public Game() {
        eventListeners = new HashSet<>();

        createNewGame();
    }

    public void createNewGame() {
        score = 0;
        totalPredators = 0;
        totalKiwis = 0;
        predatorsTrapped = 0;
        kiwiCount = 0;
        initialiseIslandFromFile("IslandData.txt");
        drawIsland();
        state = GameState.PLAYING;
        winMessage = "";
        loseMessage = "";
        playerMessage = "";
        notifyGameEventListeners();
    }

    public int getNumRows() {
        return island.getNumRows();
    }

    public int getNumColumns() {
        return island.getNumColumns();
    }

    public GameState getState() {
        return state;
    }

    public String getOccupantDescription(Object whichOccupant) {
        String description = "";
        if (whichOccupant != null && whichOccupant instanceof Occupant) {
            Occupant occupant = (Occupant) whichOccupant;
            description = occupant.getDescription();
        }
        return description;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPlayerMovePossible(MoveDirection direction) {
        boolean isMovePossible = false;
        // what position is the player moving to?
        Position newPosition = player.getPosition().getNewPosition(direction);
        // is that a valid position?
        if ((newPosition != null) && newPosition.isOnIsland()) {
            // what is the terrain at that new position?
            Terrain newTerrain = island.getTerrain(newPosition);
            // can the playuer do it?
            isMovePossible = player.hasStaminaToMove(newTerrain) &&
                    player.isAlive();
        }
        return isMovePossible;
    }

    public Terrain getTerrain(int row, int column) {
        return island.getTerrain(new Position(island, row, column));
    }

    public boolean isVisible(int row, int column) {
        return island.isVisible(new Position(island, row, column));

    }

    public boolean isExplored(int row, int column) {
        return island.isExplored(new Position(island, row, column));
    }

    public ConcurrentLinkedQueue<Occupant> getOccupantsPlayerPosition() {
        return island.getOccupants(player.getPosition());
    }

    public String getOccupantStringRepresentation(int row, int column) {
        return island.getOccupantStringRepresentation(new Position(island, row, column));
    }

    public ArrayList<Image> getOccupantImages(int row, int column) {
        return island.getOccupantImage(new Position(island, row, column));
    }

    public int[] getPlayerValues() {
        int[] playerValues = new int[6];
        playerValues[STAMINA_INDEX] = (int) player.getStaminaLevel();
        playerValues[MAXSTAMINA_INDEX] = (int) player.getMaximumStaminaLevel();
        playerValues[MAXWEIGHT_INDEX] = (int) player.getMaximumBackpackWeight();
        playerValues[WEIGHT_INDEX] = (int) player.getCurrentBackpackWeight();
        playerValues[MAXSIZE_INDEX] = (int) player.getMaximumBackpackSize();
        playerValues[SIZE_INDEX] = (int) player.getCurrentBackpackSize();

        return playerValues;

    }

    public int getKiwiCount() {
        return kiwiCount;
    }

    public int getScore() {
        return score;
    }

    public int getPredatorsRemaining() {
        return totalPredators - predatorsTrapped;
    }

    public Collection<Item> getPlayerInventory() {
        return player.getInventory();
    }

    public String getPlayerName() {
        return player.getName();
    }

    public boolean hasPlayer(int row, int column) {
        return island.hasPlayer(new Position(island, row, column));
    }

    public Island getIsland() {
        return island;
    }

    private void drawIsland() {
        island.draw();
    }

    public boolean canCollect(Object itemToCollect) {
        boolean result = (itemToCollect != null) && (itemToCollect instanceof Item);
        if (result) {
            Item item = (Item) itemToCollect;
            result = item.isOkToCarry();
        }
        return result;
    }

    public boolean canCuddle(Object itemToCount) {
        boolean result = (itemToCount != null) && (itemToCount instanceof Kiwi);
        if (result) {
            Kiwi kiwi = (Kiwi) itemToCount;
            result = !kiwi.counted();
        }
        return result;
    }

    public boolean canUse(Occupant itemToUse) {
        boolean result = (itemToUse != null) && (itemToUse instanceof Item);
        if (result) {
            //Food can always be used (though may be wasted) so no need to change result

            if (itemToUse instanceof Tool) {
                Tool tool = (Tool) itemToUse;
                //Traps can only be used if there is a predator to catch
                if (tool instanceof Trap) {
                    result = island.hasPredator(player.getPosition());
                }
                //Screwdriver can only be used if player has a broken trap
                else if(tool instanceof ScrewDriver) {
                    result = player.hasTrap() && player.getTrap().isBroken();
                }
            }
        }
        return result;
    }

    public String getWinMessage() {
        return winMessage;
    }

    public String getLoseMessage() {
        return loseMessage;
    }

    public String getPlayerMessage() {
        String message = playerMessage;
        playerMessage = ""; // Already told player.
        return message;
    }

    public boolean messageForPlayer() {
        return !("".equals(playerMessage));
    }

    /**
     * Picks up an item at the current position of the player
     * Ignores any objects that are not items as they cannot be picked up
     *
     * @param item the item to pick up
     * @return true if item was picked up, false if not
     */
    public boolean collectItem(Object item) {
        boolean success = (item instanceof Item) && (player.collect((Item) item));
        if (success) {
            // player has picked up an item: remove from grid square
            island.removeOccupant(player.getPosition(), (Item) item);


            // everybody has to know about the change
            notifyGameEventListeners();
        }
        return success;
    }

    public boolean dropItem(Object what) {
        boolean success = player.drop((Item) what);
        if (success) {
            // player has dropped an what: try to add to grid square
            Item item = (Item) what;
            success = island.addOccupant(player.getPosition(), item);
            if (success) {
                // drop successful: everybody has to know that
                notifyGameEventListeners();
            } else {
                // grid square is full: player has to take what back
                player.collect(item);
            }
        }
        return success;
    }

    public boolean useItem(Object item) {
        boolean success = false;
        if (item instanceof Food && player.hasItem((Food) item))
        //Player east food to increase stamina
        {
            Food food = (Food) item;
            // player gets energy boost from food
            player.increaseStamina(food.getEnergy());
            // player has consumed the food: remove from inventory
            player.drop(food);
            // use successful: everybody has to know that
            notifyGameEventListeners();
        } else if (item instanceof Tool) {
            Tool tool = (Tool) item;
            if (tool instanceof Trap && !tool.isBroken()) {
                success = trapPredator();
            } else if (tool instanceof ScrewDriver)// Use screwdriver (to fix trap)
            {
                if (player.hasTrap()) {
                    Tool trap = player.getTrap();
                    trap.fix();
                }
            }
        }
        updateGameState();
        return success;
    }

    public void countKiwi() {
        //check if there are any kiwis here
        for (Occupant occupant : island.getOccupants(player.getPosition())) {
            if (occupant instanceof Kiwi) {
                Kiwi kiwi = (Kiwi) occupant;
                island.removeOccupant(player.getPosition(), kiwi); // Remove kiwi
                kiwi.count();
                kiwiCount++;
                addToScore(10);
            }
        }
        updateGameState();
    }

    public boolean playerMove(MoveDirection direction) {
        // what terrain is the player moving on currently
        boolean successfulMove = false;
        if (isPlayerMovePossible(direction)) {
            Position newPosition = player.getPosition().getNewPosition(direction);
            Terrain terrain = island.getTerrain(newPosition);

            // move the player to new position
            player.moveToPosition(newPosition, terrain);
            island.updatePlayerPosition(player);
            successfulMove = true;

            // Is there a hazard?
            checkForHazard();

            updateGameState();
        }
        return successfulMove;
    }

    public void addGameEventListener(GameEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        eventListeners.remove(listener);
    }

    private void updateGameState() {
        String message;
        if (!player.isAlive()) {
            state = GameState.LOST;
            message = "Sorry, you have lost the game. " + this.getLoseMessage();
            this.setLoseMessage(message);
        } else if (!playerCanMove()) {
            state = GameState.LOST;
            message = "Sorry, you have lost the game. You do not have sufficient stamina to move.";
            this.setLoseMessage(message);
        } else if (predatorsTrapped == totalPredators) {
            state = GameState.WON;
            message = "You win! You have done an excellent job and trapped all the predators.";
            this.setWinMessage(message);
        } else if (kiwiCount == totalKiwis) {
            if (predatorsTrapped >= totalPredators * MIN_REQUIRED_CATCH) {
                state = GameState.WON;
                message = "You win! You have counted all the kiwi and trapped at least 80% of the predators.";
                this.setWinMessage(message);
            }
        }
        // notify listeners about changes
        notifyGameEventListeners();
    }

    private void setWinMessage(String message) {
        winMessage = message;
    }

    private void setLoseMessage(String message) {
        loseMessage = message;
    }

    private void setPlayerMessage(String message) {
        playerMessage = message;

    }

    private boolean playerCanMove() {
        return (isPlayerMovePossible(MoveDirection.NORTH) || isPlayerMovePossible(MoveDirection.SOUTH)
                || isPlayerMovePossible(MoveDirection.EAST) || isPlayerMovePossible(MoveDirection.WEST));

    }

    private boolean trapPredator() {
        Position current = player.getPosition();
        boolean hadPredator = island.hasPredator(current);
        if (hadPredator) //can trap it
        {
            Occupant occupant = island.getPredator(current);
            //Predator has been trapped so remove
            island.removeOccupant(current, occupant);
            predatorsTrapped++;
            addToScore(10);
        }

        return hadPredator;
    }
    private void addToScore(int amount) { score += amount; }

    private void checkForHazard() {
        //check if there are hazards
        for (Occupant occupant : island.getOccupants(player.getPosition())) {
            if (occupant instanceof Hazard) {
                handleHazard((Hazard) occupant);
            }
        }
    }

    private void handleHazard(Hazard hazard) {
        if (hazard.isFatal()) {
            player.kill();
            this.setLoseMessage(hazard.getDescription() + " has killed you.");
        } else if (hazard.isBreakTrap()) {
            Tool trap = player.getTrap();
            if (trap != null) {
                trap.setBroken();
                this.setPlayerMessage("Sorry your predator trap is broken. You will need to find tools to fix it before you can use it again.");
            }
        } else // hazard reduces player's stamina
        {
            double impact = hazard.getImpact();
            // Impact is a reduction in players energy by this % of Max Stamina
            double reduction = player.getMaximumStaminaLevel() * impact;
            player.reduceStamina(reduction);
            // if stamina drops to zero: player is dead
            if (player.getStaminaLevel() <= 0.0) {
                player.kill();
                this.setLoseMessage(" You have run out of stamina");
            } else // Let player know what happened
            {
                this.setPlayerMessage(hazard.getDescription() + " has reduced your stamina.");
            }
        }
    }

    private void notifyGameEventListeners() {
        for (GameEventListener listener : eventListeners) {
            listener.gameStateChanged();
        }
    }

    private void initialiseIslandFromFile(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            // make sure decimal numbers are read in the form "123.23"
            input.useLocale(Locale.US);
            input.useDelimiter("\\s*,\\s*");

            // create the island
            int numRows = input.nextInt();
            int numColumns = input.nextInt();
            island = new Island(numRows, numColumns);

            // read and setup the terrain
            setUpTerrain(input);

            // read and setup the player
            setUpPlayer(input);

            // read and setup the occupants
            setUpOccupants(input);

            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find data file '" + fileName + "'");
        }
    }

    private void setUpTerrain(Scanner input) {
        for (int row = 0; row < island.getNumRows(); row++) {
            String terrainRow = input.next();
            for (int col = 0; col < terrainRow.length(); col++) {
                Position pos = new Position(island, row, col);
                String terrainString = terrainRow.substring(col, col + 1);
                Terrain terrain = Terrain.getTerrainFromStringRepresentation(terrainString);
                island.setTerrain(pos, terrain);
            }
        }
    }

    private void setUpPlayer(Scanner input) {
        String playerName = input.next();
        int playerPosRow = input.nextInt();
        int playerPosCol = input.nextInt();
        double playerMaxStamina = input.nextDouble();
        double playerMaxBackpackWeight = input.nextDouble();
        double playerMaxBackpackSize = input.nextDouble();

        Position pos = new Position(island, playerPosRow, playerPosCol);
        player = new Player(pos, playerName,
                playerMaxStamina,
                playerMaxBackpackWeight, playerMaxBackpackSize);
        island.updatePlayerPosition(player);
    }

    private void setUpOccupants(Scanner input) {
        int numItems = input.nextInt();
        for (int i = 0; i < numItems; i++) {
            String occType = input.next();
            String occName = input.next();
            String occDesc = input.next();
            int occRow = input.nextInt();
            int occCol = input.nextInt();
            Position occPos = new Position(island, occRow, occCol);
            Occupant occupant = null;

            if (occType.equals("T")) {
                double weight = input.nextDouble();
                double size = input.nextDouble();
                if (occName.equalsIgnoreCase("Screwdriver")) {
                    occupant = new ScrewDriver(occPos, occName, occDesc, weight, size);
                } else if (occName.equalsIgnoreCase("Trap")) {
                    occupant = new Trap(occPos, occName, occDesc, weight, size);
                }
            } else if (occType.equals("E")) {
                double weight = input.nextDouble();
                double size = input.nextDouble();
                double energy = input.nextDouble();
                if (occName.equalsIgnoreCase("Sandwich")) {
                    occupant = new Sandwich(occPos, occName, occDesc, weight, size, energy);
                } else if (occName.equalsIgnoreCase("Muesli Bar")) {
                    occupant = new MuesliBar(occPos, occName, occDesc, weight, size, energy);
                } else if (occName.equalsIgnoreCase("Apple")) {
                    occupant = new Apple(occPos, occName, occDesc, weight, size, energy);
                } else if (occName.equalsIgnoreCase("Orange Juice")) {
                    occupant = new OrangeJuice(occPos, occName, occDesc, weight, size, energy);
                }
            } else if (occType.equals("H")) {
                double impact = input.nextDouble();
                occupant = new Hazard(occPos, occName, occDesc, impact);
            } else if (occType.equals("K")) {
                occupant = new Kiwi(occPos, occName, occDesc);
                totalKiwis++;
            } else if (occType.equals("P")) {
                if (occName.equalsIgnoreCase("Rat")) {
                    occupant = new Rat(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Kiore")) {
                    occupant = new Kiore(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Cat")) {
                    occupant = new Cat(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Stoat")) {
                    occupant = new Stoat(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Possum")) {
                    occupant = new Possum(occPos, occName, occDesc);
                }
                totalPredators++;
            } else if (occType.equals("F")) {
                if (occName.equalsIgnoreCase("Oystercatcher")) {
                    occupant = new OysterCatcher(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Crab")) {
                    occupant = new Crab(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Fernbird")) {
                    occupant = new FernBird(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Heron")) {
                    occupant = new Heron(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Robin")) {
                    occupant = new Robin(occPos, occName, occDesc);
                } else if (occName.equalsIgnoreCase("Tui")) {
                    occupant = new Tui(occPos, occName, occDesc);
                }
            }
            if (occupant != null) island.addOccupant(occPos, occupant);
        }
    }
}
