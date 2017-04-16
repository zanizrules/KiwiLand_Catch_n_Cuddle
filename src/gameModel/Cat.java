package gameModel;
/**
* Subclass designed to provide a different image for a cat
*/
public class Cat extends Predator {
    public Cat(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/cat.png");
    }
}
