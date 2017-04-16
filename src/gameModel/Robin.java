package gameModel;
/**
* Subclass designed to provide a different image for a robin
*/
public class Robin extends Fauna {
    public Robin(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/robin.png");
    }
}
