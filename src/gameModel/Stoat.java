package gameModel;

public class Stoat extends Fauna {
    public Stoat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/stoat.png");
    }
}
