package gameModel.gameObjects;

import gameModel.Position;

import java.util.Random;

/**
* Subclass designed to provide a different image for a trap
*/
public class Trap extends Tool {
    public static final int MAX_DURABILITY = 4;
    private static final Random rand = new Random();
    private int durability;
    private static String ORIGINAL_DESCRIPTION;

    public Trap(Position pos, String name, String description, double weight, double size){
        super(pos,name,description, weight, size);
        setDurability(0);
        ORIGINAL_DESCRIPTION = description;
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
        setDurability(0);
    }

    @Override
    public void setImage() {
        if(isBroken()) {
            super.setImage("images/broken_trap.png");
        } else super.setImage("images/trap.png");
    }

    public void setDurability(int durability){
        this.durability = durability;
        updateDescription();
    }

    public void calculateChanceOfBreaking() {
        int chanceOfBreak = rand.nextInt(MAX_DURABILITY);
        if(chanceOfBreak < durability) {
            setBroken();
        }
        durability++;
        updateDescription();
    }

    private void updateDescription() {
        if(!isBroken()) {
            float percentage = (float) durability/MAX_DURABILITY *100;
            description = ORIGINAL_DESCRIPTION + " , there is a " + percentage + "% chance of this trap breaking on use";
            System.out.println(description);
        }
    }

    public int getDurability(){
        return durability;
    }
}
