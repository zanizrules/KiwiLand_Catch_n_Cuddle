package gameController;

import gameModel.Game;
import gameModel.GridSquare;
import gameModel.Terrain;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

class GridSquarePanel extends Label {
    private Game game;
    private int row, column;
    private ImageView imageView;
    private static Image playerImage = null;
    private static final Image DEFAULT_TERRAIN = new Image(GridSquarePanel.class.getResource("../gameModel/images/wetland.png").toExternalForm());
    private static final Image WETLAND_TERRAIN = DEFAULT_TERRAIN;
    private static final Image SCRUB_TERRAIN = new Image(GridSquarePanel.class.getResource("../gameModel/images/scrub.png").toExternalForm());
    private static final Image WATER_TERRAIN =  new Image(GridSquarePanel.class.getResource("../gameModel/images/water.png").toExternalForm());
    private static final Image FOREST_TERRAIN = new Image(GridSquarePanel.class.getResource("../gameModel/images/forest.png").toExternalForm());
    private static final Image SAND_TERRAIN = new Image(GridSquarePanel.class.getResource("../gameModel/images/sand.png").toExternalForm());

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

    void update() { // Update the representation of the grid square panel
        // get the GridSquare object from the world
        Terrain terrain = game.getTerrain(row, column);
        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);

        Image terrainImage;

        switch (terrain) {
            case SAND:
                terrainImage = SAND_TERRAIN;
                break;
            case FOREST:
                terrainImage = FOREST_TERRAIN;
                break;
            case WETLAND:
                terrainImage = WETLAND_TERRAIN;
                break;
            case SCRUB:
                terrainImage = SCRUB_TERRAIN;
                break;
            case WATER:
                terrainImage = WATER_TERRAIN;
                break;
            default:
                terrainImage = DEFAULT_TERRAIN;
                break;
        }

        if (squareExplored || squareVisible) {
            if (game.getPlayer().getPosition().getRow() == row &&
                    game.getPlayer().getPosition().getColumn() == column) {
                imageView.setImage(playerImage);
            } else if (game.getOccupantImages(row, column) != null) {
                ArrayList<String> imageList = game.getOccupantImages(row, column);
                if (!imageList.isEmpty()) {
                    if (!imageList.get(imageList.size() - 1).isEmpty()) {
                        imageView.setImage(new Image(imageList.get(imageList.size() - 1)));
                    }
                } else {
                    imageView.setImage(null); // No image
                }
            } else {
                imageView.setImage(null); // No image
            }
            setGraphic(imageView);

            // Set the terrain.
            setBackground(new Background(new BackgroundImage(terrainImage, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        } else {
            imageView.setImage(null); // No image
            setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        requestLayout();
    }
}
