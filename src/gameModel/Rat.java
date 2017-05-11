package gameModel;
/**
* Subclass designed to provide a different image for a rat
*/
public class Rat extends Predator {

    public Rat(Position pos, String name, String description, String fact){
        super(pos,name,description, fact);
    }

    @Override
    public void setImage() {
        super.setImage("images/rat2.png");
    }
}
