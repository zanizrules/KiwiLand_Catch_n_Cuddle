import gameModel.*;
import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.gameObjects.Kiwi;
import gameModel.gameObjects.Player;
import gameModel.gameObjects.Predator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test: IslandTest
 * Related Class: Island
 *
 * @author Shane Birdsall
 * @version 3.0
 * Updates:
 *  * Updated predator specific tests to test animals
 */
public class IslandTest {
    private Island testIsland;
    private Position onIsland;
    private Position notOnIsland;
    private Predator cat;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        testIsland = new Island(6,5);
        onIsland = new Position(testIsland, 1,0); 
        notOnIsland = Position.NOT_ON_ISLAND;
        cat = new Predator(onIsland, "cat", "A hunting cat", "Random cat fact", ANIMAL_TYPE.CAT);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
      testIsland  = null;
    }
     
    /**
     * Tests of methods which are wrappers for GridSquare or Position are omitted as
     * methods are tested in those test classes
     */
    @Test
    public void testGetNumRows() {
        Assert.assertEquals(6, testIsland.getNumRows());
    }  
    
    @Test
    public void testGetNumColumns() {
        Assert.assertEquals(5, testIsland.getNumColumns());
    }

    @Test
    public void testHasAnimalWithAnimal() {
        testIsland.addOccupant(onIsland, cat);
        Assert.assertTrue(testIsland.hasAnimal(onIsland));
    }
    
    @Test
    public void testAddOccupantOnIslandValidOccupant() {
        Assert.assertTrue(testIsland.addOccupant(onIsland, cat));
        Assert.assertTrue(testIsland.hasOccupant(onIsland, cat));
    }
    
    @Test
    public void testAddOccupantNotOnIsland() {
        Assert.assertFalse(testIsland.addOccupant(notOnIsland, cat));
    } 
    
    @Test
    public void testAddOccupantNull() {
        Assert.assertFalse(testIsland.addOccupant(onIsland, null));
    }

    @Test
    public void testRemoveOccupantOnIslandValidOccupant() {
        Assert.assertTrue(testIsland.addOccupant(onIsland, cat));
        Assert.assertTrue(testIsland.hasOccupant(onIsland, cat));
        Assert.assertTrue(testIsland.removeOccupant(onIsland, cat));
        Assert.assertFalse(cat.getPosition().isOnIsland());
    }

    @Test
    public void testRemoveOccupantPositionNotOnIsland() {
        Assert.assertTrue(testIsland.addOccupant(onIsland, cat));
        Assert.assertFalse(testIsland.removeOccupant(notOnIsland, cat));
    }

    @Test
    public void testRemoveOccupantNotAtPosition() {
        Position another = new Position(testIsland, 0,0);
        Predator rat = new Predator(another, "Rat", "A norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
        Assert.assertFalse(testIsland.removeOccupant(onIsland, rat));
    }
    
    @Test
    public void testUpdatePlayerPosition() {
        Position newPos = new Position(testIsland, 2,3);
        Assert.assertFalse(testIsland.isExplored(newPos));
        Player player = new Player(newPos ,"Ada Lovelace",25.0, 15.0, 20.0);
        player.moveToPosition(newPos, Terrain.SAND);
        
        testIsland.updateOccupantPosition(player, player.getPosition());
        //new position should now be explored
        Assert.assertTrue("Should be explored.", testIsland.isExplored(newPos));
        //Surrounding positions should be visible
        //North
        Position north = new Position(testIsland,1,3);
        Assert.assertTrue("Should be visible.", testIsland.isVisible(north));
        
        //South
        Position south = new Position(testIsland,3,3);
        Assert.assertTrue("Should be visible.", testIsland.isVisible(south));

        //East
        Position east = new Position(testIsland,2,2);
        Assert.assertTrue("Should be visible.", testIsland.isVisible(east));
        
        //West
        Position west = new Position(testIsland,2,4);
        Assert.assertTrue("Should be visible.", testIsland.isVisible(west));
    }
    
    @Test
    public void testGetPredator() {
        testIsland.addOccupant(onIsland, cat);
        Assert.assertEquals(testIsland.getFauna(onIsland), cat);
    }
}
