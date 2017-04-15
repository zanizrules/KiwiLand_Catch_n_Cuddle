package gameModel;

public class ScrewDriver extends Tool {
    public ScrewDriver(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    @Override
    public void setImage() {
        super.setImage("images/tool.png");
    }
}
