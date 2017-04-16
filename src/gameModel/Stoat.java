package gameModel;
/**
* Subclass designed to provide a different image for a stoat
*/
public class Stoat extends Predator {
    public Stoat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/stoat.png");
    }
}
