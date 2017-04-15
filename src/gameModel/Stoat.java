package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Stoat extends Fauna {
    public Stoat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
