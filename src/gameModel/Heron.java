package gameModel;

/**
* Subclass designed to provide a different image for a heron
*/
public class Heron extends Fauna {
    public Heron(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/heron.png");
    }
}
