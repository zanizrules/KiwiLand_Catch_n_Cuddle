import gameModel.Apple;
import gameModel.Food;
import gameModel.Island;
import gameModel.Position;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class FoodTest.
 *
 * @author  AS
 * @version 2011
 */
public class FoodTest {
    private Food apple;
    private Position position;
    private Island island;
    /**
     * Default constructor for test class FoodTest
     */
    public FoodTest()
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
        apple = new Apple(position, "apple", "A juicy red apple", 1.0, 2.0, 1.5);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        apple = null;
        island = null;
        position = null;
    }

    @Test
    public void testGetEnergy()
    {
        Assert.assertEquals(1.5, apple.getEnergy(), 0.01);
    }
    
    @Test
    public void testGetStringRepresentation() {
        Assert.assertEquals("E", apple.getStringRepresentation());
    }
    
    // Tests for methods in herited from Item
    // These should be testing in one descendant class
    @Test   
    public void testGetWeight()
    {
        Assert.assertEquals(1.0, apple.getWeight(), 0.01);
    }

    @Test
    public void testGetSize()
    {
        Assert.assertEquals(2.0, apple.getSize(), 0.01);
    }

    @Test
    public void testIsOkToCarryCanCarry(){
        Assert.assertTrue("Should be carrable.", apple.isOkToCarry());
    }
    
    @Test
    public void testIsOkToCarryCannotCarry(){
        Food tooBig = new Apple(position, "Roast pig apple", "A roasted giant pig apple", 1.0, 0.0, 1.0);
        Assert.assertFalse("Shouldn't be carrable.", tooBig.isOkToCarry());
    }        
    

}
