package gameModel;

/**
 * Kiwi represents a kiwi living on the island
 */
public class Kiwi extends Fauna {
    private boolean cuddled;
    private final String kiwiFact;

    /**
     * Constructor for objects of class Kiwi
     *
     * @param pos         the position of the kiwi object
     * @param name        the name of the kiwi object
     * @param description a longer description of the kiwi
     */
    public Kiwi(Position pos, String name, String description, String fact) {
        super(pos, name, description);
        cuddled = false;
        kiwiFact = fact;
    }

    /**
     * Cuddle this kiwi
     */
    public void cuddle() {
        cuddled = true;
    }

    void reset() {
        cuddled = false;
    }

    String getKiwiFact() {
        return kiwiFact;
    }

    /**
     * Has this kiwi been cuddled
     *
     * @return true if cuddled.
     */
    public boolean cuddled() {
        return cuddled;
    }

    @Override
    public void setImage() {
        super.setImage("images/kiwi.png");
    }

    @Override
    public String getStringRepresentation() {
        return "K";
    }


}