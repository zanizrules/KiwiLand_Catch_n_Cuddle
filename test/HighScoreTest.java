import gameController.HighScoreController.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static gameController.HighScoreController.checkIfPlayerScoreIsHighScore;
import static gameController.HighScoreController.loadHighScoresFromFile;

/**
 * Test: HighScoreTest
 * Related Class: HighScoreController
 *
 * @author Shane Birdsall
 * @version 2.0
 * This class was added in Version 2.0 of KiwiLand
 */
public class HighScoreTest {
    private PlayerScore largeScore;
    private PlayerScore smallScore;

    @Before
    public void setUp() {
        largeScore = new PlayerScore("", 999999999, 0, 0);
        smallScore = new PlayerScore("", 0, 0, 0);
    }

    @Test
    public void testScoreIsBigEnoughForHighScore() {
        Assert.assertTrue(checkIfPlayerScoreIsHighScore(largeScore));
    }

    @Test
    public void testHighScoreIsNotBigEnoughForHighScore() {
        Assert.assertFalse(checkIfPlayerScoreIsHighScore(smallScore));
    }

    @Test
    public void testTextFileLoads() {
        Assert.assertTrue(loadHighScoresFromFile());
    }
}
