package gameModel;

public class OrangeJuice extends Food {
    public OrangeJuice(Position pos, String name, String description, double weight, double size, double energy){
        super(pos,name,description, weight, size, energy);
    }

    @Override
    public void setImage() {
        super.setImage("images/orangejuice.png");
    }
}
