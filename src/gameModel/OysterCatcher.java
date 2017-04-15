package gameModel;

public class OysterCatcher extends Fauna{
    public OysterCatcher(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        image = getClass().getResource("images/oystercatcher.png").toExternalForm();
    }
}
