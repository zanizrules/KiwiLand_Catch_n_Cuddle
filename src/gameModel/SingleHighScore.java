package gameModel;

/**
 * Created by Keith Miller on 5/7/2017.
 *
 */
public class SingleHighScore implements Comparable<SingleHighScore> {
    private final String name;

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getKiwisCuddled() {
        return kiwisCuddled;
    }

    public int getPredatorsCaptured() {
        return predatorsCaptured;
    }

    private int totalScore;
    private int kiwisCuddled;
    private int predatorsCaptured;

    SingleHighScore(String name, int totalScore, int kiwisCuddled, int predatorsCaptured){
        this.name = name;
        this.totalScore = totalScore;
        this.kiwisCuddled = kiwisCuddled;
        this.predatorsCaptured = predatorsCaptured;
    }

    private int getScore(){
        return this.totalScore;
    }
    public String toString(){
        String returnString = "";
        returnString += " Name: " + this.name;
        returnString += " Score: " + this.totalScore;
        returnString += " Kiwi's Cuddled: " + this.kiwisCuddled;
        returnString += " Predators Captured: " + this.predatorsCaptured;
        return returnString;
    }
    @Override
    public int compareTo(SingleHighScore o) {
        return Integer.compare(o.getScore(), this.getScore());
    }
}
