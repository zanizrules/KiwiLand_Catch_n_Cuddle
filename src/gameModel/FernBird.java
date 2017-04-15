package gameModel;

public class FernBird extends Fauna {
    public FernBird(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/fernbird.png").toExternalForm();
    }
}
