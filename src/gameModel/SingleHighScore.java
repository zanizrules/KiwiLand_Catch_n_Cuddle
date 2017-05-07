package gameModel;

/**
 * Created by Keith Miller on 5/7/2017.
 */
public class SingleHighScore implements Comparable<SingleHighScore> {
    public String name;
    public int totalScore;
    public int kiwisCuddled;
    public int predatorsCaptured;

    public SingleHighScore(String name, int totalScore, int kiwisCuddled, int predatorsCaptured){
        this.name = name;
        this.totalScore = totalScore;
        this.kiwisCuddled = kiwisCuddled;
        this.predatorsCaptured = predatorsCaptured;
    }

    public int getScore(){
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
