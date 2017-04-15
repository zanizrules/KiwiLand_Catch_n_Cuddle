package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class Traps extends Tool {
    public Traps(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
