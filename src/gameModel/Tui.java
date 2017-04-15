package gameModel;

public class Tui extends Fauna {
    public Tui(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/tui.png");
    }
}
