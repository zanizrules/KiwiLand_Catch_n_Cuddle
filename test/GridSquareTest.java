import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gameModel.*;
import gameModel.gameObjects.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test: GridSquareTest
 * Related Class: GridSquare
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed useless constructor
 */
public class GridSquareTest {
    private GridSquare emptySquare;
    private GridSquare occupiedSquare;
    private Island island;
    private Position position;
    private Food apple;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        emptySquare = new GridSquare(Terrain.SAND);
        occupiedSquare = new GridSquare(Terrain.FOREST);
        island = new Island(5,5);
        position = new Position(island, 0,0);
        apple = new Food(position, "apple", "A juicy red apple", 1.0, 2.0, 1.5, FOOD_TYPE.APPLE);
        occupiedSquare.addOccupant(apple);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        emptySquare = null;
        occupiedSquare = null;
        island = null;
        position = null;
        apple = null;
    }
    
    @Test
    public void testIsVisibleNewSquare() {
        Assert.assertFalse(emptySquare.isVisible());
    } 
    
    @Test
    public void testIsExploredNewSquare() {
        Assert.assertFalse(emptySquare.isExplored());
    }
    
    
    @Test
    public void testSetVisible() {
        emptySquare.setVisible(true);
        Assert.assertTrue(emptySquare.isVisible());
    } 
    
    @Test
    public void testSetExplored() {
        emptySquare.setExplored();
        Assert.assertTrue(emptySquare.isExplored());
    } 
    
    @Test   
    public void testGetTerrain() {
        Assert.assertEquals(Terrain.SAND, emptySquare.getTerrain());
    }
    
    @Test
    public void testSetTerrain() {
        emptySquare.setTerrain(Terrain.FOREST);
        Assert.assertEquals(Terrain.FOREST, emptySquare.getTerrain());
    }
    
    @Test
    public void testGetTerrainString(){
        Assert.assertEquals(emptySquare.getTerrainStringRepresentation(), ".");
    }
    @Test
    public void testHasPlayerNoPlayer() {
        Assert.assertFalse(emptySquare.hasPlayer());
    }
    
    @Test
    public void testHasPlayerWithPlayer() {
        Player player = new Player(position,"",25.0, 10.0, 12.0);
        occupiedSquare.setPlayer(player);
        Assert.assertTrue(occupiedSquare.hasPlayer());
    }  
    
    @Test
    public void testHasOccupantPresent(){
        Assert.assertTrue(occupiedSquare.hasOccupant(apple));
    }
    
    @Test
    public void testHasOccupantNotPresent(){
        Assert.assertFalse(emptySquare.hasOccupant(apple));
    } 
    
    @Test
    public void testGetOccupantStringRepresentationNoOccupants(){         
        Assert.assertEquals("", emptySquare.getOccupantStringRepresentation());
    }

    @Test
    public void testGetOccupantStringRepresentationSingle(){
        Assert.assertEquals("E", occupiedSquare.getOccupantStringRepresentation());
    }
    
    @Test
    public void testGetOccupantStringRepresentationMultipleOccupants(){
        // Add another occupant
        Tool trap = new Trap(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum", "Random possum fact", ANIMAL_TYPE.POSSUM);
        occupiedSquare.addOccupant(possum);          
        String stringRep = occupiedSquare.getOccupantStringRepresentation();
        Assert.assertEquals(3, stringRep.length());
        Assert.assertTrue(stringRep.contains("E"));
        Assert.assertTrue(stringRep.contains("T"));
        Assert.assertTrue(stringRep.contains("P"));
    } 
    
    @Test
    public void testGetOccupants(){
        // Add another occupant
        Tool trap = new Trap(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum", "Random possum fact", ANIMAL_TYPE.POSSUM);
        occupiedSquare.addOccupant(possum);          
        Collection<Occupant> occupants = occupiedSquare.getOccupants();
        Assert.assertEquals(3, occupants.size());
        Set<Occupant> occupantSet = new HashSet<>(occupants);
        Assert.assertTrue(occupantSet.contains(trap));
        Assert.assertTrue(occupantSet.contains(apple));
        Assert.assertTrue(occupantSet.contains(possum));
    }
    
        
    @Test
    public void testAddOccupantWhenNotFull() {
        Tool trap = new Trap(position, "Trap", "A predator trap", 1.0, 2.0);
        Assert.assertTrue(occupiedSquare.addOccupant(trap));
        Assert.assertTrue(occupiedSquare.hasOccupant(trap));
    }
    
    @Test
    public void testAddOccupantNull() {
        Assert.assertFalse(occupiedSquare.addOccupant(null));
    }  
    
    @Test
    public void testAddOccupantWhenFull() {  
        // Add another occupant
        Tool trap = new Trap(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum", "Random possum fact", ANIMAL_TYPE.POSSUM);
        occupiedSquare.addOccupant(possum);        
        //Now the cave has three occupants it should not be possible to add another
        Predator rat = new Predator(position, "Rat", "A  ship rat", "Random rat fact", ANIMAL_TYPE.RAT);
        Assert.assertFalse(occupiedSquare.addOccupant(rat));
        Assert.assertFalse(occupiedSquare.hasOccupant(rat));
    } 
    
    @Test
    public void testAddOccupantDuplicate() {  
        // Attempt to add again
        Assert.assertFalse(occupiedSquare.addOccupant(apple));

    }
    
    @Test
    public void testRemoveOccupantWhenPresent() {
        Assert.assertTrue(occupiedSquare.removeOccupant(apple));
        Assert.assertFalse(occupiedSquare.hasOccupant(apple));
    }
    
    @Test
    public void testRemoveOccupantWhenNotPresent() {
        Assert.assertFalse(emptySquare.removeOccupant(apple));
    }

    @Test
    public void testRemoveOccupantWhenNull() {
        Assert.assertFalse(occupiedSquare.removeOccupant(null));
    }
}

