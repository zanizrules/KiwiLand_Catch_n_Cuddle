package gameModel;
/**
* Subclass designed to provide a different image for orange juice
*/
public class OrangeJuice extends Food {
    OrangeJuice(Position pos, String name, String description, double weight, double size, double energy){
        super(pos,name,description, weight, size, energy);
    }

    @Override
    public void setImage() {
        super.setImage("images/orangejuice.png");
    }
}
