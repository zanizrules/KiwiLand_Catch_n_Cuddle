package gameModel;

public class Sandwich extends Food {
    public Sandwich(Position pos, String name, String description, double weight, double size, double energy){
        super(pos,name,description, weight, size, energy);
    }

    @Override
    public void setImage() {
        super.setImage("images/sandwhich.png");
    }
}
