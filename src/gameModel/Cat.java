package gameModel;

public class Cat extends Predator {
    public Cat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/cat.png").toExternalForm();
    }
}
