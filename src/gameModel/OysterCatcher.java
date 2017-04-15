package gameModel;

import javafx.scene.image.Image;

public class OysterCatcher extends Fauna{
    public OysterCatcher(Position pos, String name, String description){
        super(pos,name,description);
    }

    @Override
    public void setImage() {
        super.setImage("images/oystercatcher.png");
    }
}
