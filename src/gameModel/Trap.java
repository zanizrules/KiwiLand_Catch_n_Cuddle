package gameModel;

public class Trap extends Tool {
    public Trap(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    @Override
    public void setImage() {
        super.setImage("images/trap.png");
    }
}
