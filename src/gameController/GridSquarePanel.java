package gameController;

import gameModel.Game;
import gameModel.Terrain;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class is used to represent a single grid square within the island grid.
 * A grid square can contain a terrain, and images of the occupants on that square.
 * Author: Shane Birdsall
 */
class GridSquarePanel extends Label {
    private final Game game;
    private final int row, column;
    private ImageView imageView;
    private static Image playerImage = null;

    // Terrain Images
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
        setMaxWidth(86); // Set size of grid square
        setMaxHeight(86);
        setStyle("-fx-border-color: white"); // Set border of grid to white
        imageView = new ImageView();
        imageView.setFitHeight(80); // (Images resized based on this)
        imageView.setFitWidth(80);
    }

    void update() { // Update the representation of the grid square panel
        // get the GridSquare object from the world
        Terrain terrain = game.getTerrain(row, column);
        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);

        Image terrainImage;

        switch (terrain) { // Set image depending on the terrain for this grid square
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

        // Show images if the square is explored and visible
        if (squareExplored || squareVisible) {
            if (game.getPlayer().getPosition().getRow() == row &&
                    game.getPlayer().getPosition().getColumn() == column) {
                imageView.setImage(playerImage);
            } else if (game.getOccupantImages(row, column) != null) {
                ArrayList<Image> imageList = game.getOccupantImages(row, column);
                if (!imageList.isEmpty()) {
                    imageView.setImage(imageList.get(imageList.size() - 1));
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
