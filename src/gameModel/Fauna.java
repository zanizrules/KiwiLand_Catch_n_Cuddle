
package gameModel;

/**
 * Fauna at this point represents any species that is not a kiwi or a predator on the island.
 * If we need additional endangered species this class should have descendant classes created.
 */
public abstract class Fauna extends Occupant
{
    /**
     * Constructor for objects of class Endangered
     * @param pos the position of the kiwi
     * @param name the name of the kiwi
     * @param description a longer description of the kiwi
     */
    public Fauna(Position pos, String name, String description) 
    {
        super(pos, name, description);
    } 

    @Override
    public String getStringRepresentation() 
    {
          return "F";
    }
}
