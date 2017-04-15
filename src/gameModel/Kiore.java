package gameModel;

public class Kiore extends Predator {
    public Kiore(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/rat.png");
    }
}
