package gameModel;
/**
* Subclass designed to provide a different image for a possum
*/
public class Possum extends Predator {
    public Possum(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/possum.png");
    }
}
