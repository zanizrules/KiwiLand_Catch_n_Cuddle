import gameModel.gameObjects.ANIMAL_TYPE;
import gameModel.gameObjects.Fauna;
import gameModel.Island;
import gameModel.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author AS
 * @version 2011
 */
public class FaunaTest {

    public FaunaTest() {
    }

    /**
     * Test of getStringRepresentation method, of class Fauna.
     */
    @Test
    public void testGetStringRepresentation() {
        Island island = new Island(5, 5);
        Position position = new Position(island, 4, 4);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher",
                ANIMAL_TYPE.OYSTER_CATCHER);
        String expResult = "F";
        String result = instance.getStringRepresentation();
        assertEquals(expResult, result);
    }

}
