package gameModel;

public class Heron extends Fauna {
    public Heron(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/heron.png");
    }
}
