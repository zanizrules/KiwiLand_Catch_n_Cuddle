package gameModel;

import java.io.*;
import java.util.*;

public class HighScoresHandler {
    private String fileName = "HighScores.txt";
    private ArrayList<SingleHighScore> highScores = new ArrayList<>();

    public ArrayList<SingleHighScore> getHighScores() {
        return highScores;
    }

    public HighScoresHandler() {
        getHighScoresFromFile(fileName);
        printHighScoresToConsole();
        sortHighScores();
        printHighScoresToConsole();
    }

    private void getHighScoresFromFile(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            input.useLocale(Locale.US);
            input.useDelimiter("\\s*,\\s*");
            setUpHighScores(input);


        } catch (FileNotFoundException e) {
            System.out.println("Unable to find data file " + fileName);
        }
    }

    //Allows the addition of a new highscore into the map.
    public void addNewScore(SingleHighScore newHighScore) {
        highScores.add(newHighScore);
        sortHighScores();
    }

    //Updates the text file based on the map.
    private void sortHighScores() {
        Collections.sort(highScores);
        rewriteTextFile();
    }

    //Rewrites the text file so that next time the program is opened it stays in order.
    private void rewriteTextFile() {
        int numberOfHighScores = highScores.size();
        SingleHighScore score;
        StringBuilder singleScore = new StringBuilder(String.valueOf(numberOfHighScores) + ", \n");
        for (SingleHighScore highScore : highScores) {
            score = highScore;
            singleScore.append(score.getName()).append(", ");
            singleScore.append(score.getTotalScore()).append(", ");
            singleScore.append(score.getKiwisCuddled()).append(", ");
            singleScore.append(score.getPredatorsCaptured()).append(", \n");
            //Print this to the document
        }
        WriteFile writer = new WriteFile(fileName);
        try {
            writer.writeToFile(singleScore.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(singleScore);
    }

    //Intially sets up the highscores based on the text file
    private void setUpHighScores(Scanner input) {
        int numberOfScores = input.nextInt();
        for (int i = 0; i < numberOfScores; i++) {
            String name = input.next();
            int totalScore = input.nextInt();
            int kiwisCuddled = input.nextInt();
            int predatorsCaptured = input.nextInt();
            SingleHighScore highscore = new SingleHighScore(name, totalScore, kiwisCuddled, predatorsCaptured);
            highScores.add(highscore);
        }

    }

    //Used for testing purposes
    private void printHighScoresToConsole() {
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println("Rank " + (i + 1) + " : " + highScores.get(i).toString());
        }

    }

    class WriteFile {
        private String path;
        private boolean append = false;

        WriteFile(String pathway) {
            this.path = pathway;
        }

        void writeToFile(String textLine) throws IOException {
            FileWriter writer = new FileWriter(path, append);
            PrintWriter print_line = new PrintWriter(writer);
            print_line.printf(textLine);
            print_line.close();
        }
    }
}

