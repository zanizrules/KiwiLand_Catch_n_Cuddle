import gameModel.Island;
import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.Position;
import gameModel.gameObjects.FOOD_TYPE;
import gameModel.gameObjects.Food;
import gameModel.gameObjects.Predator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test: PredatorTest
 * Related Class: Predator
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed useless constructor
 */
public class PredatorTest {
    private Predator rat;
    private Position position;
    private Island island;

    @Before
    public void setUp() {
        island = new Island(5,5);
        position = new Position(island, 4,4);
        rat = new Predator(position, "Rat", "A norway rat", "Random rat fact", ANIMAL_TYPE.RAT);
    }
    
    @After
    public void tearDown() {
        island = null;
        position = null;
        rat = null;   
    }

    @Test
    public void testStringRepresentation() {
        String expResult = "P";
        String result = rat.getStringRepresentation();
        assertEquals(expResult, result);
    }


}
