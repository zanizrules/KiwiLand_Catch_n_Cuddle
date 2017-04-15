package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Rock extends Hazard {
    public Rock(Position pos, String name, String description, double impact){
        super(pos,name,description, impact);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
