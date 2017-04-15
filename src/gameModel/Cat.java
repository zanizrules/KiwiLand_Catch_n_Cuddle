package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Cat extends Predator {
    public Cat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
