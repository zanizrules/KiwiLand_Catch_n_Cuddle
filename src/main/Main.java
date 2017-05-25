package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Region root = FXMLLoader.load(getClass().getResource("/gameView/mainMenuUI.fxml"));
        primaryStage.setTitle("KiwiLand Catch n Cuddle");

        double sceneWidth = 1280;
        double sceneHeight = 960;

        /*
            Source for the idea behind the following resize code:
            http://gillius.org/blog/2013/02/javafx-window-scaling-on-resize.html
         */
        if(root.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
            root.setPrefWidth(sceneWidth);
        } else sceneWidth = root.getPrefWidth();
        if(root.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
            root.setPrefHeight(sceneHeight);
        } else sceneHeight = root.getPrefHeight();

        Group group = new Group(root);
        StackPane rootPane = new StackPane(group);
        Scene kiwiLandScene = new Scene(rootPane, sceneWidth, sceneHeight);
        group.scaleXProperty().bind(kiwiLandScene.widthProperty().divide(sceneWidth));
        group.scaleYProperty().bind(kiwiLandScene.heightProperty().divide(sceneHeight));
        primaryStage.setScene(kiwiLandScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // start method called
    }
}
