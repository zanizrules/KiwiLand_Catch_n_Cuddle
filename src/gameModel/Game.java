package gameModel;

import gameController.HighScoreController.*;
import gameController.GameOverPopUpUI_Controller;
import gameController.InformationPopUpUI_Controller;
import gameModel.gameObjects.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The Game class holds player statistics, and is the connection between Player and Island classes.
 * The functionality to read in a game
 */
public class Game {
    private Island island;
    private Player player;
    private GameState state;
    private int turnCount;
    private int kiwisCuddled;
    private int score;
    private int totalPredators;
    private int totalKiwis;
    private int totalFood;
    private int predatorsTrapped;

    private ConcurrentLinkedQueue<Predator> predatorQueue;
    private ConcurrentLinkedQueue<Food> foodQueue;
    private ConcurrentLinkedQueue<Kiwi> kiwiQueue;

    private final Set<GameEventListener> eventListeners;

    private String loseMessage = "";
    private String playerMessage = "";

    // Constants shared with UI to provide player data
    public static final int STAMINA_INDEX = 0;
    public static final int MAX_STAMINA_INDEX = 1;
    public static final int MAX_WEIGHT_INDEX = 2;
    public static final int WEIGHT_INDEX = 3;
    public static final int MAX_SIZE_INDEX = 4;
    public static final int SIZE_INDEX = 5;
    private static final int MIN_NUM_OF_KIWIS_ON_BOARD = 4;
    private static final int MIN_NUM_OF_FOOD_ON_BOARD = 2;
    private static final int MIN_NUM_OF_PREDATOR_ON_BOARD = 2;
    private static final int SPAWN_LOOP_TIMEOUT_LIMIT = 6;
    private static final int TURNS_BETWEEN_SPAWNS = 2;
    private static final int TURNS_BETWEEN_PREDATOR_AI_MOVEMENT = 3;

    public Game() {
        eventListeners = new HashSet<>();
        createNewGame();
    }

    public void predatorConsumeOccupantOnSameTile(Predator predator){
        for(Occupant itemToRemove : island.getOccupants(predator.getPosition())) {
            if (itemToRemove instanceof Food) {
                predatorConsumeFood((Food) itemToRemove);
            } else if (itemToRemove instanceof Kiwi) {
                predatorConsumeKiwi((Kiwi) itemToRemove);
            }
        }
    }

    private void predatorConsumeKiwi(Kiwi kiwi) {
        island.removeOccupant(kiwi.getPosition(), kiwi);
    }

    private void predatorConsumeFood(Food food) {
        island.removeOccupant(food.getPosition(), food);
    }

    private void movePredatorToNewPosition(Predator predator) {
        if(isOccupantMoveWithinTheIsland(predator, MoveDirection.NORTH) != null) {
            occupantMove(predator, MoveDirection.NORTH);
        } else if(isOccupantMoveWithinTheIsland(predator, MoveDirection.EAST) != null) {
            occupantMove(predator, MoveDirection.EAST);
        } else if(isOccupantMoveWithinTheIsland(predator, MoveDirection.SOUTH) != null) {
            occupantMove(predator, MoveDirection.SOUTH);
        } else if(isOccupantMoveWithinTheIsland(predator, MoveDirection.WEST) != null) {
            occupantMove(predator, MoveDirection.WEST);
        }
    }

    public int getTotalPredators() {
        return totalPredators;
    }

    public int getPredatorsTrapped() {
        return predatorsTrapped;
    }

    public int getTotalKiwis() {
        return totalKiwis;
    }

    public void createNewGame() {
        kiwiQueue = new ConcurrentLinkedQueue<>();
        foodQueue = new ConcurrentLinkedQueue<>();
        predatorQueue = new ConcurrentLinkedQueue<>();
        turnCount = 0;
        score = 0;
        totalPredators = 0;
        totalKiwis = 0;
        totalFood = 0;
        predatorsTrapped = 0;
        kiwisCuddled = 0;
        initialiseIslandFromFile("IslandData.txt");
        drawIsland();
        state = GameState.PLAYING;
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
        Position newPosition = isOccupantMoveWithinTheIsland(player, direction);
        // is that a valid position?
        boolean enoughStamina = false;
        if (newPosition != null) {
            // what is the terrain at that new position?
            Terrain newTerrain = island.getTerrain(newPosition);
            // can the player do it?
            enoughStamina = player.hasStaminaToMove(newTerrain) &&
                    player.isAlive();
        }
        return enoughStamina;
    }

    private Position isOccupantMoveWithinTheIsland(Occupant occupant, MoveDirection direction) {
        return occupant.getPosition().getNewPosition(direction);
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

    public CopyOnWriteArraySet<Occupant> getOccupantsPlayerPosition() {
        return island.getOccupants(player.getPosition());
    }

    public ArrayList<Image> getOccupantImages(int row, int column) {
        return island.getOccupantImage(new Position(island, row, column));
    }

    public double[] getPlayerValues() {
        double[] playerValues = new double[6];
        playerValues[STAMINA_INDEX] = player.getStaminaLevel();
        playerValues[MAX_STAMINA_INDEX] = player.getMaximumStaminaLevel();
        playerValues[MAX_WEIGHT_INDEX] = player.getMaximumBackpackWeight();
        playerValues[WEIGHT_INDEX] = player.getCurrentBackpackWeight();
        playerValues[MAX_SIZE_INDEX] = player.getMaximumBackpackSize();
        playerValues[SIZE_INDEX] = player.getCurrentBackpackSize();

        return playerValues;
    }

    public int getKiwisCuddled() {
        return kiwisCuddled;
    }

    public int getScore() {
        return score;
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
            result = !kiwi.cuddled();
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
                    result = island.hasAnimal(player.getPosition());
                }
                //Screwdriver can only be used if player has a broken trap
                else if(tool instanceof ScrewDriver) {
                    result = player.hasTrap() && player.getTrap().isBroken();
                }
            }
        }
        return result;
    }

    private String getLoseMessage() {
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
     */
    public void collectItem(Object item) {
        boolean success = (item instanceof Item) && (player.collect((Item) item));
        if (success) {
            // player has picked up an item: remove from grid square
            island.removeOccupant(player.getPosition(), (Item) item);
            if(item instanceof Food) {
                foodQueue.offer((Food) item);
            }

            // everybody has to know about the change
            notifyGameEventListeners();
        }
    }

    public void dropItem(Object what) {
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
                success = trapAnimal((Trap) item);
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

    public void cuddleKiwi() {
        //check if there are any kiwis here
        for (Occupant occupant : island.getOccupants(player.getPosition())) {
            if (occupant instanceof Kiwi) {
                Kiwi kiwi = (Kiwi) occupant;
                island.removeOccupant(player.getPosition(), kiwi); // Remove kiwi
                kiwi.cuddle();
                kiwisCuddled++;
                totalKiwis--;
                addToScore(10);
                kiwi.reset();
                kiwiQueue.offer(kiwi);
                showPopUpFact(kiwi.getImage(), "You Cuddled: " + kiwi.getDescription(), kiwi.getKiwiFact());
                break;
            }
        }
        updateGameState();
    }

    public void showPopUpFact(Image image, String name, String description) {
        try {
            InformationPopUpUI_Controller.setValues(image, name, description);
            Region root = FXMLLoader.load(getClass().getResource("/gameView/InformationPopUpUI.fxml"));
            showPopUpScene(root, name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError | NoClassDefFoundError ignore) {
            // Method is used without user interface.
        }
    }

    public void showPopUpGameOverScreen() {
        PlayerScore score = new PlayerScore("", getScore(), kiwisCuddled, predatorsTrapped);
        try {
            GameOverPopUpUI_Controller.setValues(score, getLoseMessage());
            Region root = FXMLLoader.load(getClass().getResource("/gameView/GameOverPopUpUI.fxml"));
            showPopUpScene(root, "Game Over!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError | NoClassDefFoundError ignore) {
            // Method is used without user interface.
        }
    }

    private void showPopUpScene(Region root, String name) {
        double sceneWidth = 900;
        double sceneHeight = 400;

        Stage newStage = new Stage();
        Group group = new Group(root);
        StackPane rootPane = new StackPane(group);
        Scene scene = new Scene(rootPane, Main.usersScreenWidth/1.42, Main.usersScreenHeight/2.4);

        group.scaleXProperty().bind(scene.widthProperty().divide(sceneWidth));
        group.scaleYProperty().bind(scene.heightProperty().divide(sceneHeight));

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle(name);
        newStage.show();
    }

    public boolean occupantMove(MoveDirection direction) {
        return occupantMove(player, direction);
    }

    public boolean occupantMove(Occupant occupant, MoveDirection direction) {
        // what terrain is the player moving on currently
        boolean successfulMove = false;
        if (isPlayerMovePossible(direction)) {
            Position newPosition = occupant.getPosition().getNewPosition(direction);
            Terrain terrain = island.getTerrain(newPosition);

            // move the occupant to new position
            Position oldPosition = occupant.getPosition();
            occupant.moveToPosition(newPosition, terrain);
            island.updateOccupantPosition(occupant, oldPosition);

            if(occupant instanceof Player) {
                turnCount++;
                if(turnCount % TURNS_BETWEEN_SPAWNS == 0) {
                    spawnOccupants();
                }
                if(turnCount % TURNS_BETWEEN_PREDATOR_AI_MOVEMENT == 0) {
                    // todo: in here we want to call movePredatorToNewPosition(predator);
                    // We want to do this for every predator on the island
                    // We could store reference to all predators, or we could loop through the island.
                }

                // Is there a hazard?
                checkForHazard();
            }
            successfulMove = true;
            updateGameState();
        }
        return successfulMove;
    }

    private void spawnOccupants() {
        if(totalKiwis < MIN_NUM_OF_KIWIS_ON_BOARD) {
            if(!kiwiQueue.isEmpty()) {
                spawnItem(kiwiQueue.poll());
                totalKiwis++;
            }
        }
        if(totalPredators < MIN_NUM_OF_PREDATOR_ON_BOARD) {
            if(!predatorQueue.isEmpty()) {
                spawnItem(predatorQueue.poll());
                totalPredators++;
            }
        }
        if(totalFood < MIN_NUM_OF_FOOD_ON_BOARD) {
            if(!foodQueue.isEmpty()) {
                spawnItem(foodQueue.poll());
                totalFood++;
            }
        }
    }

    // Random spawning functions

    private void spawnItem(Occupant occupant) {
        Random rand = new Random();
        int row, col;
        Position randomPosition;
        int count = 0;
        do {
            row = rand.nextInt(island.getNumRows());
            col = rand.nextInt(island.getNumColumns());
            randomPosition = new Position(island, row, col);
            count++;
        } while(island.hasOccupantWithinArea(randomPosition, occupant) && count < SPAWN_LOOP_TIMEOUT_LIMIT);

        if(count < SPAWN_LOOP_TIMEOUT_LIMIT) {
            island.addOccupant(randomPosition, occupant);
        }
    }

    public void addGameEventListener(GameEventListener listener) {
        eventListeners.add(listener);
    }

    private void updateGameState() {
        String message;
        if (!player.isAlive()) {
            state = GameState.GAME_OVER;
            message = "Sorry, you have lost the game. " + this.getLoseMessage();
            this.setLoseMessage(message);
        } else if (!playerCanMove()) {
            state = GameState.GAME_OVER;
            message = "Sorry, you have lost the game. You do not have sufficient stamina to move.";
            this.setLoseMessage(message);
        }
        // notify listeners about changes
        notifyGameEventListeners();
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

    private boolean trapAnimal(Trap trap) {
        Position current = player.getPosition();
        boolean hadAnimal = island.hasAnimal(current);
        if (hadAnimal) { //can trap it
            // Calculate chance of trap breaking
            trap.calculateChanceOfBreaking();

            Fauna fauna = island.getFauna(current);
            //Animal has been trapped so remove
            island.removeOccupant(current, fauna);
            if(fauna instanceof Predator) {
                System.out.println("Pred removed at: " + current.getRow() + ", " + current.getColumn());
                Predator predator = (Predator) fauna;
                predatorQueue.offer(predator);
                predatorsTrapped++;
                totalPredators--;
                addToScore(10);
                showPopUpFact(predator.getImage(), "You Captured: " + predator.getDescription(),
                        predator.getPredatorFact());
            } else if(fauna instanceof Kiwi) {
                resetScore();
                showPopUpFact(fauna.getImage(), "You Captured: " + fauna.getDescription(),
                        "What have you done!? You are damaging the kiwi population!  ");
            } else { // Ordinary Fauna
                addToScore(-10);
                System.out.println("YOU CAUGHT INNOCENT FAUNA :'(");
                showPopUpFact(fauna.getImage(), "You Captured: " + fauna.getName(),
                        "You captured an animal which is not a threat to Kiwis! How cruel!");
            }

        }
        return hadAnimal;
    }

    private void resetScore() {
        if(score > 0) {
            score = 0;
        }
    }

    public void addToScore(int amount) { score += amount; }

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
        island.updateOccupantPosition(player, player.getPosition());
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

            String fact = "";
            if(occType.equals("K") || occType.equals("P")) {
                fact = input.next();
            }

            Occupant occupant = null;

            switch (occType) {
                case "T": {
                    double weight = input.nextDouble();
                    double size = input.nextDouble();
                    if (occName.equalsIgnoreCase("Screwdriver")) {
                        occupant = new ScrewDriver(occPos, occName, occDesc, weight, size);
                    } else if (occName.equalsIgnoreCase("Trap")) {
                        occupant = new Trap(occPos, occName, occDesc, weight, size, occDesc.contains("broken"));
                    }
                    break;
                }
                case "E": {
                    totalFood++;
                    double weight = input.nextDouble();
                    double size = input.nextDouble();
                    double energy = input.nextDouble();
                    if (occName.equalsIgnoreCase("Sandwich")) {
                        occupant = new Food(occPos, occName, occDesc, weight, size, energy, FOOD_TYPE.SANDWICH);
                    } else if (occName.equalsIgnoreCase("Muesli Bar")) {
                        occupant = new Food(occPos, occName, occDesc, weight, size, energy, FOOD_TYPE.MUESLI_BAR);
                    } else if (occName.equalsIgnoreCase("Apple")) {
                        occupant = new Food(occPos, occName, occDesc, weight, size, energy, FOOD_TYPE.APPLE);
                    } else if (occName.equalsIgnoreCase("Orange Juice")) {
                        occupant = new Food(occPos, occName, occDesc, weight, size, energy, FOOD_TYPE.ORANGE_JUICE);
                    }
                    break;
                }
                case "H":
                    double impact = input.nextDouble();
                    occupant = new Hazard(occPos, occName, occDesc, impact);
                    break;
                case "K":
                    occupant = new Kiwi(occPos, occName, occDesc, fact);
                    totalKiwis++;
                    break;
                case "P":
                    if (occName.equalsIgnoreCase("Rat")) {
                        occupant = new Predator(occPos, occName, occDesc, fact, ANIMAL_TYPE.RAT);
                    } else if (occName.equalsIgnoreCase("Kiore")) {
                        occupant = new Predator(occPos, occName, occDesc, fact, ANIMAL_TYPE.KIORE);
                    } else if (occName.equalsIgnoreCase("Cat")) {
                        occupant = new Predator(occPos, occName, occDesc, fact, ANIMAL_TYPE.CAT);
                    } else if (occName.equalsIgnoreCase("Stoat")) {
                        occupant = new Predator(occPos, occName, occDesc, fact, ANIMAL_TYPE.STOAT);
                    } else if (occName.equalsIgnoreCase("Possum")) {
                        occupant = new Predator(occPos, occName, occDesc, fact, ANIMAL_TYPE.POSSUM);
                    }
                    totalPredators++;
                    break;
                case "F":
                    if (occName.equalsIgnoreCase("Oystercatcher")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.OYSTER_CATCHER);
                    } else if (occName.equalsIgnoreCase("Crab")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.CRAB);
                    } else if (occName.equalsIgnoreCase("Fernbird")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.FERNBIRD);
                    } else if (occName.equalsIgnoreCase("Heron")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.HERON);
                    } else if (occName.equalsIgnoreCase("Robin")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.ROBIN);
                    } else if (occName.equalsIgnoreCase("Tui")) {
                        occupant = new Fauna(occPos, occName, occDesc, ANIMAL_TYPE.TUI);
                    }
                    break;
            }
            if (occupant != null) island.addOccupant(occPos, occupant);
        }
    }
}
