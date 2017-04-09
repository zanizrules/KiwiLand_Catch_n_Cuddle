
package nz.ac.aut.ense701.gameModel;

/**
 * Score keeps track of the total scores in game
 * @author AS
 * @version July 2011d
 */
public class Score  extends Fauna {
    private boolean counted;

    /**
     * Constructor for objects of class Score
     *
     * @param pos         the position of the kiwi object
     * @param name        the name of the kiwi object
     * @param description a longer description of the kiwi
     */
    public Score(Position pos, String name, String description) {
        super(pos, name, description);
        counted = false;


    }

    /**
     * Add score
     */
    public void count() {
        counted = true;
    }

    /**
     * Has this score been counted
     *
     * @return true if counted.
     */
    public boolean counted() {
        return counted;
    }


    @Override
    public String getStringRepresentation() {
        return "S";
    }


}