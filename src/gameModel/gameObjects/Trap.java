package gameModel.gameObjects;

import gameModel.Position;

/**
* Subclass designed to provide a different image for a trap
*/
public class Trap extends Tool {
    public Trap(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
    }

    public Trap(Position pos, String name, String description, double weight, double size, boolean broken){
        this(pos,name,description, weight, size);
        if(broken) {
            setBroken();
        }
    }
    @Override
    public void setBroken() {
        super.setBroken();
        description = "A broken predator trap";
    }
    @Override
    public void fix() {
        super.fix();
        description = "A trap for predators";
    }

    @Override
    public void setImage() {
        if(isBroken()) {
            super.setImage("images/broken_trap.png");
        } else super.setImage("images/trap.png");
    }
}
