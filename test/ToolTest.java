import gameModel.*;
import gameModel.gameObjects.Tool;
import gameModel.gameObjects.Trap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test: ToolTest
 * Related Class: Tool
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed useless constructor
 */
public class ToolTest {
    private Tool trap;
    private Position position;
    private Island island;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        island = new Island(5,5);
        position = new Position(island, 2,3);
        trap = new Trap(position, "Trap", "A predator trap", 2.0, 3.0);
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        trap = null;
        island = null;
        position = null;
    }
    
    @Test
    public void testIsBrokenAndBreak() {
        Assert.assertFalse("Should not be broken", trap.isBroken());
        trap.setBroken();
        Assert.assertTrue("Should  be broken", trap.isBroken());
    }
    
    @Test
    public void testIsTrap() {
        Assert.assertTrue("Should  be trap", trap instanceof Trap);
    }

    @Test
    public void testFix() {
        trap.setBroken();
        Assert.assertTrue("Should  be broken", trap.isBroken());
        trap.fix();
        Assert.assertFalse("Should  not be broken", trap.isBroken());
    }

    @Test
    public void testStringRepresentation() {
        Assert.assertEquals("T", trap.getStringRepresentation());
    }
}
