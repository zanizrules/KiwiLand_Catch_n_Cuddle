package gameModel;

public class MuseliBar extends Food {
    public MuseliBar(Position pos, String name, String description, double weight, double size, double energy){
        super(pos,name,description, weight, size, energy);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("").toExternalForm();
    }
}
