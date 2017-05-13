package gameModel;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.*;

public class HighScoreHandler {
    private final static String HIGH_SCORE_FILE = "HighScores.txt";
    private final static ArrayList<PlayerScore> highScores = new ArrayList<>();

    public static ArrayList<PlayerScore> getHighScores() {
        if(highScores.isEmpty()) {
            loadHighScoresFromFile();
        }
        return highScores;
    }

    private static void loadHighScoresFromFile() {
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

    public static boolean checkIfPlayerScoreIsHighScore(PlayerScore score) {
        PlayerScore lowestHighScore = (PlayerScore) getHighScores().toArray()[highScores.size()-1];
        System.out.println("Lowest Score is: " + lowestHighScore.totalScore);
        System.out.println("Player Score is: " + score.totalScore);
        System.out.println("Is player score higher? " + (lowestHighScore.compareTo(score) < 0));
        return (lowestHighScore.compareTo(score) > 0);
    }

    // Allows the addition of a new high score
    public static void addNewScore(PlayerScore newHighScore) {
        getHighScores().add(newHighScore);
        Collections.sort(highScores);
        rewriteTextFile();
    }

    // Rewrites the text file with any new high scores
    private static void rewriteTextFile() {
        int numberOfHighScores = getHighScores().size();

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

    public static class PlayerScore implements Comparable<PlayerScore> {
        private String name;
        public final int totalScore;
        public final int kiwisCuddled;
        public final int predatorsCaptured;

        PlayerScore(String name, int totalScore, int kiwisCuddled, int predatorsCaptured){
            this.name = name;
            this.totalScore = totalScore;
            this.kiwisCuddled = kiwisCuddled;
            this.predatorsCaptured = predatorsCaptured;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        @Override
        public int compareTo(@Nonnull PlayerScore o) {
            return Integer.compare(o.totalScore, this.totalScore);
        }

        public String toString(){
            return "Cuddling " + kiwisCuddled + " kiwis, and catching "
                    + predatorsCaptured + " predators has scored you " + totalScore + " points";
        }
    }
}

