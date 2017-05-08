package gameModel;

import java.io.*;
import java.util.*;

public class HighScoresHandler {
    String fileName = "HighScores.txt";
    ArrayList<SingleHighScore> highScores = new ArrayList<>();

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
    public void sortHighScores() {
        Collections.sort(highScores);
        rewriteTextFile();
    }

    //Rewrites the text file so that next time the program is opened it stays in order.
    public void rewriteTextFile(){
        int numberOfHighScores = highScores.size();
        SingleHighScore score;
        String singleScore = String.valueOf(numberOfHighScores) + ", \n";
        for(int i = 0; i < numberOfHighScores; i++){
            score = highScores.get(i);
            singleScore += score.name + ", ";
            singleScore += score.totalScore + ", ";
            singleScore += score.kiwisCuddled + ", ";
            singleScore += score.predatorsCaptured + ", \n";
            //Print this to the document
        }
        WriteFile writer = new WriteFile(fileName);
        try {
            writer.writeToFile(singleScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(singleScore);
    }

    //Intially sets up the highscores based on the text file
    public void setUpHighScores(Scanner input) {
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
    public void printHighScoresToConsole() {
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println("Rank " + (i + 1) + " : " + highScores.get(i).toString());
        }

    }

}

class WriteFile{
    private String path;
    private boolean append = false;

    public WriteFile(String pathway){
        this.path = pathway;
    }

    public void writeToFile(String textLine) throws IOException {
        FileWriter writer = new FileWriter(path, append);
        PrintWriter print_line = new PrintWriter( writer);
        print_line.printf(textLine);
        print_line.close();

    }
}


