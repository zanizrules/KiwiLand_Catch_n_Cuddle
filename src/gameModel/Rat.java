package gameModel;

public class Rat extends Predator {


    public Rat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/rat2.png");
    }
}
