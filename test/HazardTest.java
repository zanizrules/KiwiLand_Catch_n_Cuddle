import gameModel.gameObjects.Hazard;
import gameModel.Island;
import gameModel.Position;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


/**
 * The test class HazardTest.
 *
 * @author  AS
 * @version 2011
 */
public class HazardTest {
    private Hazard fatal;
    private Hazard nonFatal;
    private Position position, position2;
    private Island island;
    /**
     * Default constructor for test class AnimalTest
     */
    public HazardTest()
    {
    }

     /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        island = new Island(5,5);
        position = new Position(island, 4,4);
        fatal = new Hazard(position, "Hole", "A very deep hole",  1.0);
        position2 = new Position(island, 1,3);
        nonFatal = new Hazard(position2, "Cliff", "A small cliff", 0.5);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        fatal = null;
        nonFatal =  null;
        island = null;
        position = null;
    }

    @Test
    public void testGetImpact() {
        assertEquals(1.0, fatal.getImpact());
    }
    
    @Test
    public void testIsFatal()
    {
        Assert.assertTrue("Should be fatal", fatal.isFatal());
        Assert.assertFalse("Should not be fatal", nonFatal.isFatal());
    }
    
    @Test
    public void testIsBreakTrap(){
        Hazard trapBreak = new Hazard(position2, "Broken trap", "Your trap breaks", 0.0);
        
        Assert.assertTrue("Shoyuld be trap break hazard", trapBreak.isBreakTrap());
    }
            
    
    /**
     * Tests of Occupant methods
     * Need to test these in one of the descendants of abstract class Occupant
     */
    @Test
    public void testGetPosition(){
        Assert.assertEquals("Wrong position", position, fatal.getPosition());
    }
    
    @Test
    public void testSetPosition() {
        Position newPosition = new Position(island,1,2);
        fatal.setPosition(newPosition);
        Assert.assertEquals("Check position", newPosition, fatal.getPosition());
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Check name", "Hole", fatal.getName());
    }

    @Test
    public void testGetDescription()
    {
        Assert.assertEquals("Check description", "A very deep hole", fatal.getDescription());
    }

    @Test
    public void testGetStringRepresentationDangerous() {
        Assert.assertEquals("H", fatal.getStringRepresentation());
    }
    


}
