package gameModel;

public class Heron extends Predator {
    public Heron(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/heron.png").toExternalForm();
    }
}
