package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Rat extends Predator {


    public Rat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/rat.png").toExternalForm();
    }
}
