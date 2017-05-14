import gameModel.*;
import gameModel.gameObjects.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

/**
 * Test: PlayerTest
 * Related Class: Player
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed useless constructor
 *  2. Renamed movement tests to be more descriptive
 */
public class PlayerTest {
    private Player player;
    private Position playerPosition;
    private Island island;
    private Food sandwich;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        island = new Island(5,5);
        playerPosition = new Position(island, 0,0);
        player = new Player(playerPosition,"Lisa Simpson",25.0, 15.0, 20.0);
        sandwich = new Food(playerPosition, "sandwich", "A tasty cheese sandwich", 1.0, 2.0, 1.5, FOOD_TYPE.SANDWICH);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        island = null;
        playerPosition = null;
        player = null;
    }

    // Tests for accessor methods
    @Test
    public void testGetName() {
        Assert.assertEquals("Lisa Simpson", player.getName());
    }

    @Test
    public void testGetPosition() {
        Assert.assertEquals(playerPosition, player.getPosition());
    }

    @Test
    public void testGetMaximumBackpackSize() {
        assertEquals(player.getMaximumBackpackSize(), 20.0);
    }

    @Test
    public void testGetCurrentBackpackSizeEmpty() {
        assertEquals(player.getCurrentBackpackSize(), 0.0);
    }

    @Test
    public void testGetCurrentBackpackSizeNotEmpty() {
        player.collect(sandwich);
        assertEquals(player.getCurrentBackpackSize(), 2.0);
    }

    @Test
    public void testGetMaximumBackpackWeight() {
        assertEquals(player.getMaximumBackpackWeight(), 15.0);
    }

    @Test
    public void testGetCurrentBackpackWeightEmpty() {
        assertEquals(player.getCurrentBackpackWeight(), 0.0);
    }
    @Test
    public void testGetCurrentBackpackWeightNotEmpty() {
        player.collect(sandwich);
        assertEquals(player.getCurrentBackpackWeight(), 1.0);
    }

    @Test
    public void testGetMaximumStaminaLevel() {
        Assert.assertEquals(25.0, player.getMaximumStaminaLevel(), 0.01);
    }

    @Test
    public void testGetStaminaLevel() {
        Assert.assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testReduceStamina() {
        player.reduceStamina(7.0);
        Assert.assertEquals(18.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testReduceStaminaPastZero() {
        player.reduceStamina(26.0);
        Assert.assertEquals(0.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testReduceStaminaByNegative() {
        player.reduceStamina(-1.0);
        Assert.assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testIncreaseStamina() {
        player.reduceStamina(7.0);
        player.increaseStamina(4.0);
        Assert.assertEquals(22.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testIncreaseStaminaPastZero() {
        player.increaseStamina(4.0);
        Assert.assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testIncreaseStaminaByNegative() {
        player.increaseStamina(-1.0);
        Assert.assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testHasEnoughStaminaToMovEnoughStaminaForTerrain() {
        Assert.assertTrue(player.hasStaminaToMove(Terrain.SAND));
    }

    @Test
    public void testHasEnoughStaminaToMoveNotEnoughStaminaForTerrain() {
        player.reduceStamina(23);
        Assert.assertFalse(player.hasStaminaToMove(Terrain.SCRUB));
    }

    @Test
    public void testHasStaminaToMoveNotEnoughStaminaFullBackpack() {
        // reduce stamina so under four required for FOREST with full backpack
        player.reduceStamina(22.0);
        Tool fullLoad = new Trap(playerPosition, "full", "A full load.", 14.5, 1.5);
        player.collect(fullLoad);
        Assert.assertFalse(player.hasStaminaToMove(Terrain.FOREST));
    }

    @Test
    public void testHasStaminaToMoveNotEnoughStaminaPartlyFullBackpack() {
        // reduce stamina so 50% backpack load takes over limit
        player.reduceStamina(23.0);
        Tool partLoad = new Trap(playerPosition, "part", "A part load.", 7.5, 1.5);
        player.collect(partLoad);
        Assert.assertFalse(player.hasStaminaToMove(Terrain.FOREST));
    }

    @Test
    public void testIsAlive() {
        Assert.assertTrue(player.isAlive());
    }

    @Test
    public void testKill() {
        player.kill();
        Assert.assertFalse(player.isAlive());
    }

    @Test
    public void testHasItemWithItem() {
        player.collect(sandwich);
        Assert.assertTrue(player.hasItem(sandwich));
    }

    @Test
    public void testHasItemNoItem() {
        Assert.assertFalse(player.hasItem(sandwich));
    }

    @Test
    public void testHasTrapWithTrap() {
        Trap trap = new Trap(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        Assert.assertTrue(player.hasTrap());
    }

    @Test
    public void testHasTrapNoTrap() {
        Assert.assertFalse(player.hasTrap());
    }

    @Test
    public void testGetTrap() {
        Trap trap = new Trap(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        Assert.assertEquals(player.getTrap(), trap);
    }

    @Test
    public void testGetInventory() {
        player.collect(sandwich);
        Tool trap = new Trap(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        Collection inventory = player.getInventory();
        Assert.assertTrue(inventory.contains(trap));
        Assert.assertTrue(inventory.contains(sandwich));
    }

    @Test
    public void testCollectValidItemThatFits() {
        Assert.assertTrue(player.collect(sandwich));
        Assert.assertTrue(player.hasItem(sandwich));
        Assert.assertEquals(1.0, player.getCurrentBackpackWeight(), 0.01);
        Assert.assertEquals(2.0, player.getCurrentBackpackSize(), 0.01);
        Position newPos = sandwich.getPosition();
        Assert.assertFalse(newPos.isOnIsland());
    }

    @Test
    public void testAllowForDuplicateItems() {
        Assert.assertTrue(player.collect(sandwich));
        Assert.assertTrue(player.collect(sandwich));
    }

    @Test
    public void testCollectItemMaxWeight() {
        Tool maxWeight = new Trap(playerPosition, "weight", "A very heavy weight", 15.0, 1.5);
        Assert.assertTrue(player.collect(maxWeight));
        Assert.assertTrue(player.hasItem(maxWeight));
    }

    @Test
    public void testCollectItemTooHeavy() {
        Tool hugeWeight = new Trap(playerPosition, "weight", "A very heavy weight", 16.0, 1.5);
        Assert.assertFalse(player.collect(hugeWeight));
        Assert.assertFalse(player.hasItem(hugeWeight));
    }

    @Test
    public void testCollectItemTooBig() {
        Tool largeTool = new Trap(playerPosition, "large", "A very large tool", 1.0, 20.5);
        Assert.assertFalse(player.collect(largeTool));
        Assert.assertFalse(player.hasItem(largeTool));
    }

    @Test
    public void testDropValidItem() {
        Assert.assertTrue(player.collect(sandwich));
        Assert.assertTrue(player.hasItem(sandwich));
        Assert.assertTrue(player.drop(sandwich));
        Assert.assertEquals(0.0, player.getCurrentBackpackWeight(), 0.01);
        Assert.assertEquals(0.0, player.getCurrentBackpackSize(), 0.01);
        Assert.assertFalse(player.hasItem(sandwich));
    }

    @Test
    public void testDropInvalidItem() {
        Assert.assertFalse(player.drop(sandwich));
    }

    @Test
    public void testMoveToPositionEnoughStamina() {
        Position newPosition = new Position(island, 0,1);
        player.moveToPosition(newPosition, Terrain.SAND);
        Assert.assertEquals(newPosition, player.getPosition());
        Assert.assertEquals(24.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testMoveToPositionNotEnoughStamina() {
        Position newPosition = new Position(island, 0,1);
        player.reduceStamina(23);
        player.moveToPosition(newPosition, Terrain.SCRUB);
        Assert.assertEquals(playerPosition, player.getPosition());
        Assert.assertEquals(2.0, player.getStaminaLevel(), 0.01);
    }
}