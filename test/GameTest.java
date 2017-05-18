import gameModel.*;
import gameModel.gameObjects.*;
import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Test: GameTest
 * Related Class: Game
 *
 * @author  Shane Birdsall
 * @version 2.0
 * Status: 14/05/2017, All tests pass.
 * Updates:
 *  1. Test cases for predator and kiwi spawning added
 *  2. User cuddle and capture counts tested for
 *  3. Removed Unit tests which tested accessor methods
 *  4. Score covered in testCountKiwi test
 *  5. Added collectAllOriginalKiwis helper method
 *  6. Edited predator helper method to collect all original predators
 */
public class GameTest {
    private Game game;
    private Player player;
    private Position playerPosition;
    private Island island ;
    private Predator rat;
    private Position position;
    private Food apple;
    private Kiwi kiwi;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        // Create a new game from the data file.
        // Player is in position 2,0 & has 100 units of stamina
        game           = new Game();
        playerPosition = game.getPlayer().getPosition();
        player         = game.getPlayer();
        island = game.getIsland();
        rat = new Predator(position, "Rat", "A norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
        apple = new Food(position, "apple", "A juicy red apple", 1.0, 2.0, 1.5, FOOD_TYPE.APPLE);
        kiwi = new Kiwi(position, "", "", "");
        position = new Position(island, 0,0);
        island.addOccupant(position, rat);
        island.addOccupant(position, apple);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        game = null;
        player = null;
        playerPosition = null;
    }

    @Test
    public void testGetInitialState() {
        Assert.assertEquals("Wrong initial state", game.getState(), GameState.PLAYING);
    }

    @Test
    public void testIsPlayerMovePossibleValidMove() {
        //At start of game player has valid moves EAST, West & South
        Assert.assertTrue("Move should be valid", game.isOccupantMovePossible(MoveDirection.SOUTH));
    }

    @Test
    public void testIsPlayerMovePossibleInvalidMove() {
        //At start of game player has valid moves EAST, West & South
        Assert.assertFalse("Move should not be valid", game.isOccupantMovePossible(MoveDirection.NORTH));
    }

    @Test
    public void testCanCollectCollectable(){
        //Items that are collectible and fit in backpack
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0, FOOD_TYPE.SANDWICH);
        Assert.assertTrue("Should be able to collect", game.canCollect(valid));
    }

    @Test
    public void testCanCollectNotCollectable() {
        //Items with size of '0' cannot be carried
        Item notCollectable = new Food(playerPosition,"Sandwich", "Very Heavy Sandwich",10.0, 0.0,1.0, FOOD_TYPE.SANDWICH);
        Assert.assertFalse("Should not be able to collect", game.canCollect(notCollectable));
    }

    @Test
    public void testCanUseFoodValid() {
        //Food can always be used
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0, FOOD_TYPE.SANDWICH);
        Assert.assertTrue("Should be able to use", game.canUse(valid));
    }

    @Test
    public void testCanUseTrapValid() {
        //Trap can be used if there is a predator here
        Item valid = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        //Add predator
        Predator rat = new Predator(playerPosition,"Rat", "A norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
        island.addOccupant(playerPosition, rat);
        Assert.assertTrue("Should be able to use", game.canUse(valid));
    }

    @Test
    public void testCanUseTrapNoPredator() {
        //Trap can be used if there is a predator here
        Item tool = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        Assert.assertFalse("Should not be able to use", game.canUse(tool));
    }

    @Test
    public void testCanUseTool(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new ScrewDriver(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        trap.setBroken();
        player.collect(trap);

        Assert.assertTrue("Should be able to use", game.canUse(tool));
    }

    @Test
    public void testCanUseToolNoTrap() {
        //Screwdriver can be used if player has a broken trap
        Item tool = new ScrewDriver(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        trap.setBroken();
        Assert.assertFalse("Should not be able to use", game.canUse(tool));
    }

    @Test
    public void testCanUseToolTrapNotBroken() {
        //Screwdriver can be used if player has a broken trap
        Item tool = new ScrewDriver(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0, 1.0);
        Tool trap = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        player.collect(trap);
        Assert.assertFalse("Should not be able to use", game.canUse(tool));
    }

    @Test
    public void testGetKiwiCountInitial() {
        Assert.assertEquals("Shouldn't have cuddled any kiwis yet", game.getKiwisCuddled(), 0);
    }

    @Test
    public void testCollectValid() {
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0, FOOD_TYPE.SANDWICH);
        island.addOccupant(playerPosition, food);
        Assert.assertTrue("Food now on island", island.hasOccupant(playerPosition, food));
        game.collectItem(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        Assert.assertFalse("Food should no longer be on island", island.hasOccupant(playerPosition, food));
    }

    @Test
    public void testCollectNotCollectible() {
        Item notCollectible = new Food(playerPosition,"House", "Can't collect",1.0, 0.0,1.0, FOOD_TYPE.SANDWICH);
        island.addOccupant(playerPosition, notCollectible);
        Assert.assertTrue("House now on island", island.hasOccupant(playerPosition, notCollectible));
        game.collectItem(notCollectible);
        Assert.assertFalse("Player should not have house", player.hasItem(notCollectible));
        Assert.assertTrue("House should be on island", island.hasOccupant(playerPosition, notCollectible));
    }

    @Test
    public void testDropValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0, FOOD_TYPE.SANDWICH);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        game.dropItem(food);
        Assert.assertFalse("Player should no longer have food", player.hasItem(food));
        Assert.assertTrue("Food should be on island", island.hasOccupant(playerPosition, food));
    }

    @Test
    public void testDropNotValidPositionFull(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0, FOOD_TYPE.SANDWICH);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        //Positions can have at most three occupants
        Item dummy = new Trap(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy2 = new Trap(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy3 = new Trap(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        island.addOccupant(playerPosition, dummy);
        island.addOccupant(playerPosition, dummy2);
        island.addOccupant(playerPosition, dummy3);
        game.dropItem(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        Assert.assertFalse("Food should not be on island", island.hasOccupant(playerPosition, food));
    }

    @Test
    public void testUseItemFoodCausesIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3, FOOD_TYPE.SANDWICH);
        player.collect(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        // Will only get a stamina increase if player has less than max stamina
        player.reduceStamina(5.0);
        game.useItem(food);
        Assert.assertFalse("Player should no longer have food", player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 96.3);
    }

    @Test
    public void testUseItemFoodNoIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3, FOOD_TYPE.SANDWICH);
        player.collect(food);
        Assert.assertTrue("Player should have food", player.hasItem(food));
        // Will only get a stamina increase if player has less than max stamina
        game.useItem(food);
        Assert.assertFalse("Player should no longer have food", player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 100.0);
    }

    @Test
    public void testUseItemTrap(){
        Item trap = new Trap(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        Assert.assertTrue("Player should have trap", player.hasItem(trap));
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
        island.addOccupant(playerPosition, predator);
        game.useItem(trap);
        Assert.assertTrue("Player should still have trap", player.hasItem(trap));
        Assert.assertFalse("Predator should be gone.", island.hasPredator(playerPosition));
    }

    @Test
    public void testUseItemBrokenTrap(){
        Tool trap = new Trap(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        Assert.assertTrue("Player should have trap", player.hasItem(trap));
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
        island.addOccupant(playerPosition, predator);
        trap.setBroken();
        game.useItem(trap);
        Assert.assertTrue("Player should still have trap", player.hasItem(trap));
        Assert.assertTrue("Predator should still be there as trap broken.", island.hasPredator(playerPosition));
    }

    @Test
    public void testUseItemToolFixTrap(){
        Tool trap = new Trap(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        trap.setBroken();
        player.collect(trap);
        Assert.assertTrue("Player should have trap", player.hasItem(trap));
        Tool screwdriver = new ScrewDriver(playerPosition,"Screwdriver", "Useful screwdriver",1.0, 1.0);
        player.collect(screwdriver);
        Assert.assertTrue("Player should have screwdriver", player.hasItem(screwdriver));
        game.useItem(screwdriver);
        Assert.assertFalse("Trap should be fixed", trap.isBroken());
    }

    @Test
    public void testPlayerMoveToInvalidPosition(){
        //A move NORTH would be invalid from player's start position
        Assert.assertFalse("Move not valid", game.occupantMove(MoveDirection.NORTH));
    }

    @Test
    public void testPlayerMoveValidNoHazards(){
        double stamina = player.getStaminaLevel();
        Assert.assertTrue("Move valid", game.occupantMove(MoveDirection.SOUTH));
        //Stamina reduced by move
        assertEquals("Wrong stamina", stamina - 3, player.getStaminaLevel());
        Position newPos = game.getPlayer().getPosition();
        Assert.assertEquals("Wrong position", newPos.getRow(), 1);
        Assert.assertFalse("Player should not be here", game.hasPlayer(playerPosition.getRow(), playerPosition.getColumn()));
    }

    @Test
    public void testPlayerMoveFatalHazard(){
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Steep cliff", 1.0);
        island.addOccupant(hazardPosition, fatal);
        Assert.assertTrue("Move valid", game.occupantMove(MoveDirection.SOUTH));
        //Fatal Hazard should kill player
        Assert.assertTrue("Player should be dead.", !player.isAlive());
        Assert.assertTrue("Game should be over", game.getState() == GameState.GAME_OVER);
    }

    @Test
    public void testPlayerMoveDeadPlayer(){
        player.kill();
        Assert.assertFalse(game.occupantMove(MoveDirection.SOUTH));
    }

    @Test
    public void testPlayerMoveNonFatalHazardNotDead(){
        double stamina = player.getStaminaLevel();
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Not so steep cliff", 0.5);
        island.addOccupant(hazardPosition, fatal);
        Assert.assertTrue("Move valid", game.occupantMove(MoveDirection.SOUTH));
        //Non-fatal Hazard should reduce player stamina
        Assert.assertTrue("Player should be alive.", player.isAlive());
        Assert.assertTrue("Game should not be over", game.getState() == GameState.PLAYING);
        assertEquals("Wrong stamina", (stamina - 53), player.getStaminaLevel());
    }

    @Test
    public void testPlayerMoveNonFatalHazardDead(){
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Not so steep cliff", 0.5);
        island.addOccupant(hazardPosition, fatal);
        player.reduceStamina(47.0);
        Assert.assertTrue("Move valid", game.occupantMove(MoveDirection.SOUTH));
        //Non-fatal Hazard should reduce player stamina to less than zero
        Assert.assertFalse("Player should not be alive.", player.isAlive());
        Assert.assertTrue("Game should be over", game.getState() == GameState.GAME_OVER);
        assertEquals("Wrong stamina", 0.0, player.getStaminaLevel());
    }

    @Test
    public void testPlayerMoveNotEnoughStamina(){
        // Reduce player's stamina to less than is needed for the most challenging move
        //Most challenging move is WEST as Terrain is water
        player.reduceStamina(97.0);
        Assert.assertFalse("Player should not have required stamina", game.occupantMove(MoveDirection.WEST));
        //Game not over as there other moves player has enough stamina for
        Assert.assertTrue("Game should not be over", game.getState() == GameState.PLAYING);
    }

    @Test
    public void testCountKiwi() {
        //Need to move to a place where there is a kiwi
        Kiwi kiwi = new Kiwi(player.getPosition(), "kiwi","kiwi", "Random kiwi fact");
        island.addOccupant(player.getPosition(), kiwi);
        int score = game.getScore() + 10;
        game.cuddleKiwi();
        int scoreAfterKiwi = game.getScore();
        Assert.assertEquals("Wrong cuddle", game.getKiwisCuddled(), 1);
        Assert.assertEquals("Wrong score", score, scoreAfterKiwi);
    }

    @Test
    public void testCountKiwiWithPredator(){
        Predator rat = new Predator(player.getPosition(), "rat", "big Rat", "Random rat fact", ANIMAL_TYPE.RAT);
        Kiwi kiwi = new Kiwi(player.getPosition(), "kiwi","kiwi", "Random kiwi fact");
        island.addOccupant(player.getPosition(), rat);
        island.addOccupant(player.getPosition(), kiwi);
        game.cuddleKiwi();
        Assert.assertEquals("Wrong cuddle", game.getKiwisCuddled(), 1);
    }

    @Test
    public void TestPredatorsRespawn() {
        Assert.assertEquals(7, game.getTotalPredators());
        trapAllOriginalPredators();
        Assert.assertNotEquals(0, game.getTotalPredators());
        Assert.assertEquals(7, game.getPredatorsTrapped());
    }

    @Test
    public void TestKiwiRespawn() {
        Assert.assertEquals(10, game.getTotalKiwis());
        collectAllOriginalKiwis();
        Assert.assertNotEquals(0, game.getTotalKiwis());
        Assert.assertEquals(10, game.getKiwisCuddled());
    }

    @Test
    public void testPredatorEatsFoodWhenOnSameTile() {
        game.predConsume(rat);
        Assert.assertFalse(island.hasOccupant(position, apple));
    }

    @Test
    public void testPredatorEatsKiwiWhenOnSameTile() {
        island.addOccupant(position, kiwi);
        game.predConsume(rat);
        Assert.assertFalse(island.hasOccupant(position, kiwi));
    }

    @Test
    public void testPredatorMovesFromTile() {
        Position pos2 = new Position(island, 1, 0);

        Assert.assertFalse(island.hasOccupant(pos2, rat));
        // is he gone
        game.occupantMove(rat, MoveDirection.SOUTH);
        Assert.assertFalse(island.hasOccupant(position, rat));

        // Does he move
        Assert.assertTrue(island.hasOccupant(pos2, rat));
    }

    /**
     * Private helper methods
     */
    private void collectAllOriginalKiwis() {
        // Kiwi 1
        playerMoveEast(5);
        game.cuddleKiwi();
        // Kiwi 2
        playerMoveSouth(2);
        playerMoveEast(1);
        game.cuddleKiwi();
        // Kiwi 3
        playerMoveSouth(4);
        playerMoveWest(2);
        game.cuddleKiwi();
        // Kiwi 4
        playerMoveNorth(1);
        playerMoveWest(4);
        playerMoveSouth(1);
        game.cuddleKiwi();
        // Kiwi 5
        playerMoveWest(2);
        playerMoveSouth(1);
        game.cuddleKiwi();
        // Kiwi 6
        playerMoveEast(3);
        playerMoveSouth(1);
        game.cuddleKiwi();
        // Kiwi 7
        playerMoveSouth(1);
        game.cuddleKiwi();
        // Kiwi 8
        playerMoveEast(1);
        game.cuddleKiwi();
        // Kiwi 9
        playerMoveEast(2);
        game.cuddleKiwi();
        // Kiwi 10
        playerMoveNorth(1);
        playerMoveEast(1);
        playerMoveNorth(1);
        game.cuddleKiwi();
    }

    private void trapAllOriginalPredators() {
        //Firstly player needs a trap
        Tool trap = new Trap(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        game.collectItem(trap);

        //Now player needs to trap all predators
        //Predator 1
        boolean moveOK = playerMoveEast(5);
        game.useItem(trap);
        //Predator 2
        if(moveOK){
            moveOK = playerMoveWest(1);
        }
        if(moveOK){
            moveOK = playerMoveSouth(2);
            game.useItem(trap);
        }
        //Predator 3
        if(moveOK){
            moveOK = playerMoveWest(2);
        }
        if(moveOK){
            moveOK = playerMoveSouth(1);
            game.useItem(trap);
        }
        //Predator 4
        if(moveOK){
            moveOK = playerMoveWest(3);
        }
        if(moveOK){
            moveOK = playerMoveSouth(1);
            game.useItem(trap);
        }
        //Predator 5
        if(moveOK){
            moveOK = playerMoveEast(1);
        }
        if(moveOK){
            moveOK = playerMoveSouth(1);
            game.useItem(trap);
        }
        //Predator 6
        if(moveOK){
            moveOK = playerMoveEast(2);
        }
        if(moveOK){
            moveOK = playerMoveSouth(1);
            game.useItem(trap);
        }
        //Predator 7
        if(moveOK){
            moveOK = playerMoveNorth(1);
        }
        if(moveOK){
            moveOK = playerMoveEast(3);
        }
        if(moveOK){
            playerMoveSouth(4);
            game.useItem(trap);
        }
    }

    private boolean playerMoveNorth(int numberOfMoves) {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.occupantMove(MoveDirection.NORTH);
            if(!success) break;
        } return success;
    }

    private boolean playerMoveSouth(int numberOfMoves) {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.occupantMove(MoveDirection.SOUTH);
            if(!success)break;
        } return success;
    }

    private boolean playerMoveEast(int numberOfMoves) {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.occupantMove(MoveDirection.EAST);
            if(!success) break;
        } return success;
    }

    private boolean playerMoveWest(int numberOfMoves) {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.occupantMove(MoveDirection.WEST);
            if(!success) break;
        } return success;
    }
}
