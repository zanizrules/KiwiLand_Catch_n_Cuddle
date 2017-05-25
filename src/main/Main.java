package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    public static int usersScreenWidth;
    public static int usersScreenHeight;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        usersScreenWidth = graphicsDevice.getDisplayMode().getWidth();
        usersScreenHeight = graphicsDevice.getDisplayMode().getHeight();

        if(usersScreenWidth/16 == usersScreenHeight/9) {
            // User has a 16:9 screen
            usersScreenWidth -= 600;
            usersScreenHeight -= 100;
            System.out.println("16:9");
        } else if(usersScreenWidth/4 == usersScreenHeight/3) {
            // User has a 4:3 screen
            usersScreenWidth -= 100;
            usersScreenHeight -= 100;
            System.out.println("4:3");
        } else {
            // User has a 16:10 screen, or close to it
            usersScreenWidth -= 600;
            usersScreenHeight -= 200;
            System.out.println("16:10");
        }
        loadMenu(primaryStage);
    }


    /* I got help with window rescaling from the following source:
    http://gillius.org/blog/2013/02/javafx-window-scaling-on-resize.html */
    public static void loadMenu(Stage stage) throws IOException {
        Region root = FXMLLoader.load(Main.class.getResource("/gameView/mainMenuUI.fxml"));
        stage.setTitle("KiwiLand Catch n Cuddle");

        double sceneWidth = 1280;
        double sceneHeight = 960;

        Group group = new Group(root);
        StackPane rootPane = new StackPane(group);
        Scene kiwiLandScene = new Scene(rootPane, usersScreenWidth, usersScreenHeight);
        group.scaleXProperty().bind(kiwiLandScene.widthProperty().divide(sceneWidth));
        group.scaleYProperty().bind(kiwiLandScene.heightProperty().divide(sceneHeight));
        stage.setScene(kiwiLandScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // start method called
    }
}
