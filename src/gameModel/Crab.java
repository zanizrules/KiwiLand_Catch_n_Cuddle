package gameModel;

public class Crab extends Fauna {
    public Crab(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/crab.png");
    }
}
