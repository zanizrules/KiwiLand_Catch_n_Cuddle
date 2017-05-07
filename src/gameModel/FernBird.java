package gameModel;
/**
 * Subclass designed to provide a different image for a fern bird
 */
public class FernBird extends Fauna {
    public FernBird(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/fernbird.png");
    }
}