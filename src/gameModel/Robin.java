package gameModel;

public class Robin extends Fauna {
    public Robin(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/robin.png");
    }
}
