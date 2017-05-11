package gameModel;
/**
* Subclass designed to provide a different image for a Kiore
*/
public class Kiore extends Predator {
    public Kiore(Position pos, String name, String description, String fact){
        super(pos,name,description, fact);
    }

    @Override
    public void setImage() {
        super.setImage("images/rat.png");
    }
}
