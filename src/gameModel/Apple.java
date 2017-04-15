package gameModel;

public class Apple extends Food {
    public Apple(Position pos, String name, String description, double weight, double size, double energy){
        super(pos,name,description, weight, size, energy);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/apple.png").toExternalForm();
    }
}
