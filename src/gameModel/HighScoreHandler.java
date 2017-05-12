package gameModel;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.*;

public class HighScoreHandler {
    private final static String HIGH_SCORE_FILE = "HighScores.txt";
    private PriorityQueue<PlayerScore> highScores;

    public PriorityQueue<PlayerScore> getHighScores() {
        return highScores;
    }

    public HighScoreHandler() {
        highScores = new PriorityQueue<>();
        loadHighScoresFromFile();
    }

    private void loadHighScoresFromFile() {
        try {
            Scanner input = new Scanner(new File(HIGH_SCORE_FILE));
            input.useDelimiter("\\s*,\\s*");
            int numberOfScores = input.nextInt();

            for (int i = 0; i < numberOfScores; i++) {
                String name = input.next();
                int totalScore = input.nextInt();
                int kiwisCuddled = input.nextInt();
                int predatorsCaptured = input.nextInt();
                PlayerScore score = new PlayerScore(name, totalScore, kiwisCuddled, predatorsCaptured);
                highScores.add(score);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find: " + HIGH_SCORE_FILE);
        }
    }

    // Allows the addition of a new high score
    public void addNewScore(PlayerScore newHighScore) {
        highScores.add(newHighScore);
    }

    // Rewrites the text file with any new high scores
    private void rewriteTextFile() {
        int numberOfHighScores = highScores.size();

        // Get content to write to file
        StringBuilder singleScore = new StringBuilder(String.valueOf(numberOfHighScores) + ", \n");
        for (PlayerScore score : highScores) {
            singleScore.append(score.name).append(", ");
            singleScore.append(score.totalScore).append(", ");
            singleScore.append(score.kiwisCuddled).append(", ");
            singleScore.append(score.predatorsCaptured).append(", \n");
        }

        // Write the content to the file
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(HIGH_SCORE_FILE, false));
            printWriter.printf(singleScore.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class PlayerScore implements Comparable<PlayerScore> {
        public final String name;
        public final int totalScore;
        public final int kiwisCuddled;
        public final int predatorsCaptured;

        PlayerScore(String name, int totalScore, int kiwisCuddled, int predatorsCaptured){
            this.name = name;
            this.totalScore = totalScore;
            this.kiwisCuddled = kiwisCuddled;
            this.predatorsCaptured = predatorsCaptured;
        }

        @Override
        public int compareTo(@Nonnull PlayerScore o) {
            return Integer.compare(o.totalScore, this.totalScore);
        }

        public String toString(){
            String returnString = "";
            returnString += " Name: " + this.name;
            returnString += " Score: " + this.totalScore;
            returnString += " Kiwi's Cuddled: " + this.kiwisCuddled;
            returnString += " Predators Captured: " + this.predatorsCaptured;
            return returnString;
        }
    }
}

