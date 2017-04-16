package gameController;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.*;

import gameModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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

    private Game game;
    private HashMap<KeyCode, String> keyMappings;

    public KiwiLandUI_Controller() {
        // Default constructor is the first thing to be called.
        game = new Game();
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

    private void setUpKeys() { // Set up the key mappings
        keyMappings.put(KeyCode.W, "north");
        keyMappings.put(KeyCode.A, "west");
        keyMappings.put(KeyCode.S, "south");
        keyMappings.put(KeyCode.D, "east");
    }

    @FXML
    private void processKeyPressed(KeyEvent keyEvent){ // Called when a key is pressed
        if(keyMappings.containsKey(keyEvent.getCode())) {
            // If the keycode has a stored mapping then execute the associated function
            completeKeyboardAction(keyMappings.get(keyEvent.getCode()));
        }
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
        game.countKiwi();
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
                game.playerMove(MoveDirection.NORTH);
                break;
            case "south":
                game.playerMove(MoveDirection.SOUTH);
                break;
            case "west":
                game.playerMove(MoveDirection.WEST);
                break;
            case "east":
                game.playerMove(MoveDirection.EAST);
                break;
            // Do nothing if invalid command
        }
    }

    /**
     * This method is called by the game model every time something changes.
     * It triggers an update.
     * TODO: Change the functionality of this function to implement new game states (Sprint 2)
     */
    @Override
    public void gameStateChanged() {
        update();

        // TODO: Use our own message boxes as JOptionPanes DO NOT WORK ON MAC MACHINES (sprint 2)

        // check for "game over" or "game won"
        if (game.getState() == GameState.LOST) {
            JOptionPane.showMessageDialog(null, game.getLoseMessage(), "Game over!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        } else if (game.getState() == GameState.WON) {
            JOptionPane.showMessageDialog(
                    null, game.getWinMessage(), "Well Done!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        } else if (game.messageForPlayer()) {
            JOptionPane.showMessageDialog(
                    null, game.getPlayerMessage(), "Important Information",
                    JOptionPane.INFORMATION_MESSAGE);
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
        int[] playerValues = game.getPlayerValues();
        nameLabel.setText(game.getPlayerName());
        float stamina = (float) playerValues[Game.STAMINA_INDEX]/(float) playerValues[Game.MAXSTAMINA_INDEX];
        staminaProgressBar.setProgress(stamina);
        float weight = (float) playerValues[Game.WEIGHT_INDEX]/(float) playerValues[Game.MAXWEIGHT_INDEX];
        weightProgressBar.setProgress(weight);
        float size = (float) playerValues[Game.SIZE_INDEX]/(float) playerValues[Game.MAXSIZE_INDEX];
        sizeProgressBar.setProgress(size);
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
