import gameModel.Game;
import gameModel.MoveDirection;
import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.gameObjects.Fauna;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test: FaunaTest
 * Related Class: Fauna
 *
 * @author Shane Birdsall
 * @version 2.0
 * Updates:
 *  1. Removed old accessor method test
 *  2. Changed the test so that it uses actual game scenario
 *  3. Added test case for string representation
 *  4. Added test case for correct animal state of an oystercatcher (sample test)
 */
public class FaunaTest {
    private Game game;
    private Fauna instance;

    @Before
    public void setUp() {
        game = new Game();
        game.playerMove(MoveDirection.EAST);
        instance = (Fauna) game.getOccupantsPlayerPosition().toArray()[0];
    }

    @Test
    public void testStringRepresentation() {
        String expResult = "F";
        String result = instance.getStringRepresentation();
        assertEquals(expResult, result);
    }

    @Test
    public void testFaunaAnimalState() {
        ANIMAL_TYPE expResult = ANIMAL_TYPE.OYSTER_CATCHER;
        ANIMAL_TYPE result = instance.getAnimalType();
        assertEquals(expResult, result);
    }
}
