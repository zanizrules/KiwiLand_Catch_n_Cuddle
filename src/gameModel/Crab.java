package gameModel;
/**
* Subclass designed to provide a different image for a crab
*/
public class Crab extends Fauna {
    public Crab(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/crab.png");
    }
}
