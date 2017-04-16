package gameModel;
/**
* Subclass designed to provide a different image for a tui
*/
public class Tui extends Fauna {
    public Tui(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/tui.png");
    }
}
