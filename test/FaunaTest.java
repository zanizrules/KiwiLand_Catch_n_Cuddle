import gameModel.Fauna;
import gameModel.Island;
import gameModel.OysterCatcher;
import gameModel.Position;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class FaunaTest {
    
    public FaunaTest() {}

    /**
     * Test of getStringRepresentation method, of class Fauna.
     */
    @Test
    public void testGetStringRepresentation() {
        Island island = new Island(5,5);
        Position position = new Position(island, 4,4);
        Fauna instance = new OysterCatcher(position, "Oystercatcher", " A nesting oystercatcher");
        String expResult = "F";
        String result = instance.getStringRepresentation();
        assertEquals(expResult, result);
    }
    
}
