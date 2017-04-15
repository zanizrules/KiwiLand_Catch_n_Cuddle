package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Sunburn extends Hazard {
    public Sunburn(Position pos, String name, String description, double impact){
        super(pos,name,description, impact);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
