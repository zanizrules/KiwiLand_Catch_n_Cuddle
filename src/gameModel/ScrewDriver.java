package gameModel;

/**
 * Created by michelemiller on 15/04/17.
 */
public class ScrewDriver extends Tool {
    public ScrewDriver(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
