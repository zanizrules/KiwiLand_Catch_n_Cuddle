package gameController;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScoreController {
    private final static String HIGH_SCORE_FILE = "HighScores.txt";
    private final static ArrayList<PlayerScore> highScores = new ArrayList<>();
    private final static int MAX_NUMBER_OF_HIGHSCORES = 10;

    static ArrayList<PlayerScore> getHighScores() {
        if (highScores.isEmpty()) {
            loadHighScoresFromFile();
        }
        return highScores;
    }

    public static boolean loadHighScoresFromFile() {
        try {
            Scanner input = new Scanner(new File(HIGH_SCORE_FILE));
            input.useDelimiter("\\s*,\\s*");
            int numberOfScores = input.nextInt();

            if(numberOfScores > MAX_NUMBER_OF_HIGHSCORES) {
                numberOfScores = MAX_NUMBER_OF_HIGHSCORES;
            }

            for (int i = 0; i < numberOfScores; i++) {
                String name = input.next();
                int totalScore = input.nextInt();
                int kiwisCuddled = input.nextInt();
                int predatorsCaptured = input.nextInt();
                PlayerScore score = new PlayerScore(name, totalScore, kiwisCuddled, predatorsCaptured);
                highScores.add(score);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean checkIfPlayerScoreIsHighScore(PlayerScore score) {
        PlayerScore lowestHighScore = (PlayerScore) getHighScores().toArray()[highScores.size() - 1];
        return (lowestHighScore.compareTo(score) > 0);
    }

    // Allows the addition of a new high score
    static void addNewScore(PlayerScore newHighScore) {
        getHighScores().add(newHighScore);
        Collections.sort(highScores);

        // Limit the amount of high scores to the maximum limit
        while (highScores.size() > MAX_NUMBER_OF_HIGHSCORES) {
            highScores.remove(highScores.size() - 1);
        }
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
        final int totalScore;
        final int kiwisCuddled;
        final int predatorsCaptured;

        public PlayerScore(String name, int totalScore, int kiwisCuddled, int predatorsCaptured) {
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
        public int compareTo(PlayerScore o) {
            return Integer.compare(o.totalScore, this.totalScore);
        }

        public String toString() {
            return "Cuddling " + kiwisCuddled + " kiwis, and catching "
                    + predatorsCaptured + " predators has scored you " + totalScore + " points";
        }
    }
}

