import gameModel.Island;
import gameModel.gameObjects.Kiwi;
import gameModel.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class KiwiTest {
    
    private Kiwi kiwi;
    private Position position;
    private Island island;
    
    public KiwiTest() {
    }
    
    @Before
    public void setUp() {
        island = new Island(5,5);
        position = new Position(island, 4,4);
        kiwi = new Kiwi(position, "Kiwi", "A little spotted kiwi", "Random kiwi fact");
    }
    
    @After
    public void tearDown() {
        island = null;
        position = null;
        kiwi = null;
    }

    @Test
    public void testCountedNotCounted() {
        assertFalse("Should not be cuddled", kiwi.cuddled());
    }
    
    @Test
    public void testCountedIsCounted() {
        assertFalse("Should not be cuddled", kiwi.cuddled());
        kiwi.cuddle();
        assertTrue("Should  be cuddled", kiwi.cuddled());
    }

    /**
     * Test of getStringRepresentation method, of class Kiwi.
     */
    @Test
    public void testGetStringRepresentation() {
        assertEquals("K", kiwi.getStringRepresentation());
    }


    
}
