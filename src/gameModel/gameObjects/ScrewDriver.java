package gameModel.gameObjects;

import gameModel.Position;
import gameModel.gameObjects.Tool;

/**
* Subclass designed to provide a different image for a screwdriver
*/
public class ScrewDriver extends Tool {
    public ScrewDriver(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    @Override
    public void setImage() {
        super.setImage("images/tool.png");
    }
}
