import gameModel.Island;
import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.gameObjects.Kiwi;
import gameModel.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test: KiwiTest
 * Related Class: Kiwi
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed useless constructor
 *  2. Added test to check kiwi animal type
 */
public class KiwiTest {
    private Kiwi kiwi;
    private Position position;
    private Island island;

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

    @Test
    public void testStringRepresentation() {
        assertEquals("K", kiwi.getStringRepresentation());
    }

    @Test
    public void testAnimalType() {
        assertEquals(ANIMAL_TYPE.KIWI, kiwi.getAnimalType());
    }
}
