package gameController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import gameModel.*;
import gameModel.gameObjects.Hazard;
import gameModel.gameObjects.Item;
import gameModel.gameObjects.Occupant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;

import static gameController.HighScoreController.checkIfPlayerScoreIsHighScore;

/**
 * Main Game Screen/UI for KiwiLand Catch n Cuddle.
 * Author: Shane Birdsall
 * Date: 13/04/2017
 */
public class KiwiLandUI_Controller implements Initializable, GameEventListener {

    public BorderPane gameScene;
    @FXML
    private GridPane islandGrid;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private ProgressBar weightProgressBar;
    @FXML
    private ProgressBar staminaProgressBar;
    @FXML
    private ProgressBar sizeProgressBar;
    @FXML
    private ListView<Item> inventoryListView;
    @FXML
    private ListView<Occupant> objectListView;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Button cuddleButton;
    @FXML
    private Button collectButton;
    @FXML
    private Button discardButton;
    @FXML
    private Button useButton;
    @FXML
    private Button exitButton;

    private final Game game;
    private final HashMap<KeyCode, String> keyMappings;
    private final static Image HAZARD_IMAGE
            = new Image(Hazard.class.getResource("images/hazard.png").toExternalForm());
    private static String selectedCharacter; // Used to set player image and name based on user selection

    public KiwiLandUI_Controller() {
        // Default constructor is the first thing to be called.
        game = new Game();
        if(selectedCharacter != null && !selectedCharacter.isEmpty()) {
            game.getPlayer().setImage(selectedCharacter);
            game.getPlayer().setName(selectedCharacter);
        }
        keyMappings = new HashMap<>();
        setUpKeys();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This method is called AFTER the constructor of this class

        // All things to be added is done here, e.g. player name, grid data etc

        // Single selection lists
        inventoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        objectListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Create and set up island grid
        int rows = game.getNumRows();
        int columns = game.getNumColumns();

        System.out.println("rows: " +rows + ", cols: " +columns);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                islandGrid.add(new GridSquarePanel(game, row, col), col, row); // col, row is required order
            }
        }
        setAsGameListener();
        update();
    }

    static void setSelectedCharacter(String imageLoc) {
        selectedCharacter = imageLoc;
    }

    private void setUpKeys() { // Set up the key mappings
        keyMappings.put(KeyCode.W, "north");
        keyMappings.put(KeyCode.A, "west");
        keyMappings.put(KeyCode.S, "south");
        keyMappings.put(KeyCode.D, "east");
        keyMappings.put(KeyCode.F1, "revealMap");
    }

    @FXML
    private void processKeyPressed(KeyEvent keyEvent){ // Called when a key is pressed
        if(keyMappings.containsKey(keyEvent.getCode())) {
            // If the keycode has a stored mapping then execute the associated function
            completeKeyboardAction(keyMappings.get(keyEvent.getCode()));
        }
    }

    @FXML
    private void exitButtonClick() throws IOException { // Called when exit button is clicked
        Main.loadMenu((Stage) exitButton.getScene().getWindow());
    }

    @FXML
    private void restartButtonClick() { // Called when restart button is clicked
        game.createNewGame();
    }

    @FXML
    private void collectButtonClick() { // Called when collect button is clicked
        Occupant occupant = objectListView.getSelectionModel().getSelectedItem();
        game.collectItem(occupant);
    }

    @FXML
    private void discardButtonClick() { // Called when discard button is clicked
        game.dropItem(inventoryListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void useButtonClick() { // Called when use button is clicked
        game.useItem(inventoryListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void cuddleButtonClick() { // Called when cuddle button is clicked
        game.cuddleKiwi();
    }

    @FXML
    private void objectListValueChanged() { // Called when an item is selected in the object list
        Occupant occupant = objectListView.getSelectionModel().getSelectedItem();
        if (occupant != null) {
            collectButton.setDisable(!game.canCollect(occupant));
            cuddleButton.setDisable(!game.canCuddle(occupant));
            descriptionTextField.setText(game.getOccupantDescription(occupant));
        }
    }

    @FXML
    private void inventoryListValueChanged() { // Called when an item is selected in the inventory list
        Item item = inventoryListView.getSelectionModel().getSelectedItem();
        if (item != null) {
            discardButton.setDisable(false);
            useButton.setDisable(!game.canUse(item));
            descriptionTextField.setText(game.getOccupantDescription(item));
        }
    }

    private void completeKeyboardAction(String command) {
        // Based on the command past in determines which way the player should move
        switch (command) {
            case "north":
                game.occupantMove(MoveDirection.NORTH);
                break;
            case "south":
                game.occupantMove(MoveDirection.SOUTH);
                break;
            case "west":
                game.occupantMove(MoveDirection.WEST);
                break;
            case "east":
                game.occupantMove(MoveDirection.EAST);
                break;
            case "revealMap":
                GridSquarePanel.toggleRevealMap();
                update();
                break;
            // Do nothing if invalid command
        }
    }

    /**
     * This method is called by the game model every time something changes.
     * It triggers an update.
     */
    @Override
    public void gameStateChanged() {
        update();

        // check for "game over" or "game won"
        if (game.getState() == GameState.GAME_OVER) {
            game.showPopUpGameOverScreen((Stage) exitButton.getScene().getWindow());
            game.createNewGame();
        } else if (game.messageForPlayer()) {
            System.out.println("Called");
            game.showPopUpInformation(HAZARD_IMAGE, "Important Information", game.getPlayerMessage());
        }
    }

    private void setAsGameListener() {
        game.addGameEventListener(this);
    }

    /**
     * Updates the state of the UI based on the state of the game.
     */
    private void update() {
        descriptionTextField.setText(""); // Clear description
        ObservableList<Node> nodes = islandGrid.getChildren();
        for (Node n : nodes) { // update all grid squares on the island
            if(n instanceof GridSquarePanel) {
                GridSquarePanel panel = (GridSquarePanel) n;
                panel.update();
            }
        }

        // update player information
        double[] playerValues = game.getPlayerValues();
        nameLabel.setText(game.getPlayerName());
        staminaProgressBar.setProgress(playerValues[Game.STAMINA_INDEX]/playerValues[Game.MAX_STAMINA_INDEX]);
        weightProgressBar.setProgress(playerValues[Game.WEIGHT_INDEX]/playerValues[Game.MAX_WEIGHT_INDEX]);
        sizeProgressBar.setProgress(playerValues[Game.SIZE_INDEX]/playerValues[Game.MAX_SIZE_INDEX]);
        scoreLabel.setText("Score: " + Integer.toString(game.getScore()));
        // NOTE: Math used to calculate the value between 0 and 1 to be used on progress bar

        // update inventory list
        ObservableList<Item> inventoryItems = FXCollections.observableArrayList(game.getPlayerInventory());
        inventoryListView.setItems(inventoryItems);
        inventoryListView.getSelectionModel().clearSelection();
        useButton.setDisable(true);
        discardButton.setDisable(true);
        // update list of visible objects
        ObservableList<Occupant> objectItems = FXCollections.observableArrayList(game.getOccupantsPlayerPosition());
        objectListView.setItems(objectItems);
        objectListView.getSelectionModel().clearSelection();
        cuddleButton.setDisable(true);
        collectButton.setDisable(true);
    }
}
