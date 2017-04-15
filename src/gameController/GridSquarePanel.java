package gameController;

import gameModel.Game;
import gameModel.Terrain;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.util.ArrayList;


class GridSquarePanel extends Label {

    private Game game;
    private int row, column;
    private ImageView imageView;
    private static Image playerImage = null;

    GridSquarePanel(Game game, int row, int column) {
        this.game = game;
        if (playerImage == null) {
            playerImage = new Image(game.getPlayer().getImage());
        }
        this.row = row;
        this.column = column;
        initComponents();
    }

    private void initComponents() {
        setMaxWidth(86);
        setMaxHeight(86);
        setStyle("-fx-border-color: white");
        imageView = new ImageView();
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
    }

    /**
     * Updates the representation of the grid square panel.
     */
    void update() {
        // get the GridSquare object from the world
        Terrain terrain = game.getTerrain(row, column);
        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);

        Color color;

        switch (terrain) {
            case SAND:
                color = Color.YELLOW;
                break;
            case FOREST:
                color = Color.GREEN;
                break;
            case WETLAND:
                color = Color.BLUE;
                break;
            case SCRUB:
                color = Color.DARKGRAY;
                break;
            case WATER:
                color = Color.CYAN;
                break;
            default:
                color = Color.LIGHTGRAY;
                break;
        }

        if (squareExplored || squareVisible) {
            if (game.getPlayer().getPosition().getRow() == row &&
                    game.getPlayer().getPosition().getColumn() == column) {
                imageView.setImage(playerImage);
            } else if (game.getOccupantImages(row, column) != null) {
                ArrayList<String> imageList = game.getOccupantImages(row, column);
                if (!imageList.isEmpty()) {
                    if(!imageList.get(imageList.size() - 1).isEmpty()) {
                        imageView.setImage(new Image(imageList.get(imageList.size() - 1)));
                    }
                } else {
                    imageView.setImage(null); // No image
                }
            } else {
                imageView.setImage(null); // No image
            }
            setGraphic(imageView);

            // Set the colour.
            setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            imageView.setImage(null); // No image
            setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        requestLayout();
    }
}
