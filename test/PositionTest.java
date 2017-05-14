import gameModel.Island;
import gameModel.MoveDirection;
import gameModel.Position;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test: PositionTest
 * Related Class: Position
 *
 * @author Shane Birdsall
 * @version 2.0
 */
public class PositionTest {
    private Position onIsland;
    private Position notOnIsland;
    private Island island;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        island = new Island(5, 5);
        onIsland = new Position(island, 1, 2);
        notOnIsland = Position.NOT_ON_ISLAND;
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        island = null;
        onIsland = null;
        notOnIsland = null;
    }

    @Test
    public void testPositionValidParametersOnIsland() {
        Assert.assertTrue(onIsland.isOnIsland());
    }

    @Test
    public void testPositionValidParameterNotOnIsland() {
        Assert.assertFalse(notOnIsland.isOnIsland());
    }

    @Test
    public void testIllegalArgumentNoIsland() throws Exception {
        try {
            new Position(null, 0, 0);
            Assert.fail("No exception thrown when island null.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Island"));
        }
    }

    @Test
    public void testIllegalArgumentRowNegative() throws Exception {
        try {
            new Position(island, -1, 0);
            Assert.fail("No exception thrown when row negative.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Invalid row"));
        }
    }

    @Test
    public void testIllegalArgumentRowTooLarge() throws Exception {
        try {
            new Position(island, 5, 0);
            Assert.fail("No exception thrown when row too large.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Invalid row"));
        }
    }

    @Test
    public void testIllegalArgumentColumnNegative() throws Exception {
        try {
            new Position(island, 1, -1);
            Assert.fail("No exception thrown when column negative.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Invalid column"));
        }
    }

    @Test
    public void testIllegalArgumentColumnTooLarge() throws Exception {
        try {
            new Position(island, 0, 5);
            Assert.fail("No exception thrown when column too large.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Invalid column"));
        }
    }

    @Test
    public void testGetColumn() {
        Assert.assertEquals(2, onIsland.getColumn());
    }

    @Test
    public void testGetRow() {
        Assert.assertEquals(1, onIsland.getRow());
    }

    @Test
    public void testRemoveFromIsland() {
        onIsland.removeFromIsland();
        Assert.assertFalse(onIsland.isOnIsland());
    }

    @Test
    public void testGetNewPositionNull() throws Exception {
        try {
            onIsland.getNewPosition(null);
            Assert.fail("No exception thrown when direction null.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue("Not expected exception message", expected.getMessage().contains("Direction parameter"));
        }
    }

    @Test
    public void testGetNewPositionNotOnIsland() {
        Assert.assertEquals(notOnIsland.getNewPosition(MoveDirection.NORTH), null);
    }

    @Test
    public void testGetNewPositionValidDirection() {
        Position newPosition = onIsland.getNewPosition(MoveDirection.WEST);
        Assert.assertEquals(newPosition.getRow(), 1);
        Assert.assertEquals(newPosition.getColumn(), 1);
    }
}
