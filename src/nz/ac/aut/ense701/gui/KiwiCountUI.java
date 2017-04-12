package nz.ac.aut.ense701.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameEventListener;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.MoveDirection;

/**
 * Main Game Screen/UI for KiwiLand Catch n Cuddle.
 * Author: Shane Birdsall
 * Date: 13/04/2017
 */
public class KiwiCountUI extends JFrame implements GameEventListener {
    private JButton btnCollect;
    private JButton btnCount;
    private JButton btnDrop;
    private JButton btnUse;

    private JLabel txtPlayerName;
    private JLabel txtScore;

    private JProgressBar progBackpackSize;
    private JProgressBar progBackpackWeight;
    private JProgressBar progPlayerStamina;

    private JList listInventory;
    private JList listObjects;
    private JTextArea descriptionArea;
    private JPanel pnlIsland;
    private Game game;

    // KEY BINDINGS related variables
    private InputMap inputMappings;
    private ActionMap actionMappings;
    private KeyStroke sKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
    private KeyStroke aKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
    private KeyStroke dKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
    private KeyStroke wKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);

    /**
     * Creates a GUI for the KiwiIsland game.
     *
     * @param game the game object to represent with this GUI.
     */
    public KiwiCountUI(Game game) {
        assert game != null : "Make sure game object is created before UI";
        this.game = game;
        setAsGameListener();
        initComponents();
        initIslandGrid();
        update();
    }

    /**
     * This method is called by the game model every time something changes.
     * Trigger an update.
     */
    @Override
    public void gameStateChanged() {
        update();

        // check for "game over" or "game won"
        if (game.getState() == GameState.LOST) {
            JOptionPane.showMessageDialog(this, game.getLoseMessage(), "Game over!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        } else if (game.getState() == GameState.WON) {
            JOptionPane.showMessageDialog(
                    this, game.getWinMessage(), "Well Done!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        } else if (game.messageForPlayer()) {
            JOptionPane.showMessageDialog(
                    this, game.getPlayerMessage(), "Important Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setAsGameListener() { game.addGameEventListener(this); }

    /**
     * Updates the state of the UI based on the state of the game.
     */
    private void update() {
        Component[] components = pnlIsland.getComponents();
        for (Component c : components) {
            // all components in the panel are GridSquarePanels, so we can safely cast
            GridSquarePanel panel = (GridSquarePanel) c;
            panel.update();
        }

        // update player information
        int[] playerValues = game.getPlayerValues();
        txtPlayerName.setText(game.getPlayerName());
        progPlayerStamina.setMaximum(playerValues[Game.MAXSTAMINA_INDEX]);
        progPlayerStamina.setValue(playerValues[Game.STAMINA_INDEX]);
        progBackpackWeight.setMaximum(playerValues[Game.MAXWEIGHT_INDEX]);
        progBackpackWeight.setValue(playerValues[Game.WEIGHT_INDEX]);
        progBackpackSize.setMaximum(playerValues[Game.MAXSIZE_INDEX]);
        progBackpackSize.setValue(playerValues[Game.SIZE_INDEX]);

        // Update score
        txtScore.setText(Integer.toString(game.getScore()));

        // update inventory list
        listInventory.setListData(game.getPlayerInventory());
        listInventory.clearSelection();
        btnUse.setEnabled(false);
        btnDrop.setEnabled(false);

        // update list of visible objects
        listObjects.setListData(game.getOccupantsPlayerPosition());
        listObjects.clearSelection();
        btnCollect.setEnabled(false);
        btnCount.setEnabled(false);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("KiwiLand Catch n Cuddle");

        // Panels + ScrollPanes
        JPanel gamePanel = new JPanel(new BorderLayout());
        JPanel descriptionPanel = new JPanel();
        JPanel rightSidePanel = new JPanel(new BorderLayout());
        pnlIsland = new JPanel();
        JPanel scorePanel = new JPanel();
        JPanel playerDataPanel = new JPanel();
        JPanel pnlControls = new JPanel();
        JPanel inventoryPanel = new JPanel();
        JScrollPane inventoryScrollPane = new JScrollPane();
        JPanel objectPanel = new JPanel();
        JScrollPane objectScrollPane = new JScrollPane();

        // Labels
        JLabel playerNameLabel = new JLabel("Player Name: ");
        txtPlayerName = new JLabel();
        JLabel staminaLabel = new JLabel("Stamina: ");
        progPlayerStamina = new JProgressBar();
        JLabel bagWeightLabel = new JLabel();
        JLabel scoreLabel = new JLabel();
        txtScore = new JLabel();

        // TODO: see if this caN be removed
        JLabel lblBackpackSize = new JLabel();
        progBackpackSize = new JProgressBar();
        // ----

        // ProgressBars
        progBackpackWeight = new JProgressBar();

        // Buttons
        btnDrop = new JButton();
        btnUse = new JButton();
        btnCollect = new JButton();
        btnCount = new JButton();

        // Lists
        listInventory = new JList();
        listObjects = new JList();

        // Frame Settings
        setResizable(true);
        // TODO: set size?

        // Start connecting things

        gamePanel.add(pnlIsland, BorderLayout.CENTER);

        // Score Panel
        scorePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scorePanel.add(scoreLabel);
        txtScore.setText("0");
        scorePanel.add(txtScore);
        rightSidePanel.add(scorePanel, BorderLayout.NORTH);

        // Player Panel
        playerDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        playerDataPanel.add(playerNameLabel);
        playerDataPanel.add(txtPlayerName);
        playerDataPanel.add(staminaLabel);
        progPlayerStamina.setStringPainted(true);
        playerDataPanel.add(progPlayerStamina);
        bagWeightLabel.setText("Backpack Weight:");
        playerDataPanel.add(bagWeightLabel);
        progBackpackWeight.setStringPainted(true);
        playerDataPanel.add(progBackpackWeight);
        rightSidePanel.add(playerDataPanel, BorderLayout.AFTER_LINE_ENDS);

        // Description Panel
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionPanel.add(descriptionArea);
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
        gamePanel.add(descriptionPanel, BorderLayout.SOUTH);

        // Inventory Panel
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));

        listInventory.setModel(new AbstractListModel() { // TODO: do better
            String[] strings = {"Item 1", "Item 2", "Item 3"};

            public int getSize() { return strings.length; }

            public Object getElementAt(int i) { return strings[i]; }
        });

        listInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInventory.setVisibleRowCount(3); // TODO: find out what this does
        listInventory.addListSelectionListener(evt2 -> listInventoryValueChanged());
        inventoryScrollPane.setViewportView(listInventory);
        inventoryPanel.add(inventoryScrollPane);
        btnDrop.setText("Drop");
        btnDrop.addActionListener(evt1 -> btnDropActionPerformed());
        inventoryPanel.add(btnDrop);
        btnUse.setText("Use");
        btnUse.addActionListener(evt1 -> btnUseActionPerformed());
        inventoryPanel.add(btnUse);
        rightSidePanel.add(inventoryPanel, BorderLayout.AFTER_LINE_ENDS);

        // Object Panel
        objectPanel.setBorder(BorderFactory.createTitledBorder("Objects"));

        listObjects.setModel(new AbstractListModel() {
            String[] strings = {"Item 1", "Item 2", "Item 3"};

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });

        listObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listObjects.setVisibleRowCount(3);
        listObjects.addListSelectionListener(evt -> listObjectsValueChanged());
        objectScrollPane.setViewportView(listObjects);
        objectPanel.add(objectScrollPane);
        btnCollect.setText("Collect");
        btnCollect.addActionListener(evt -> btnCollectActionPerformed());
        objectPanel.add(btnCollect);
        btnCount.setText("Count");
        btnCount.addActionListener(evt -> btnCountActionPerformed());
        objectPanel.add(btnCount);
        rightSidePanel.add(objectPanel, BorderLayout.AFTER_LINE_ENDS);

        gamePanel.add(rightSidePanel, BorderLayout.EAST);
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        pack();

        inputMappings = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMappings = gamePanel.getActionMap();
        initKeyBindings();
    }

    private void initKeyBindings() {
        inputMappings.put(sKey, "south");
        inputMappings.put(aKey, "west");
        inputMappings.put(dKey, "east");
        inputMappings.put(wKey, "north");
        actionMappings.put("south", new MoveSouthAction());
        actionMappings.put("east", new MoveEastAction());
        actionMappings.put("west", new MoveWestAction());
        actionMappings.put("north", new MoveNorthAction());
    }

    private void btnCollectActionPerformed() {
        Object obj = listObjects.getSelectedValue();
        game.collectItem(obj);
    }

    private void btnDropActionPerformed() { game.dropItem(listInventory.getSelectedValue()); }

    private void listObjectsValueChanged() {
        Object occ = listObjects.getSelectedValue();
        if (occ != null) {
            btnCollect.setEnabled(game.canCollect(occ));
            btnCount.setEnabled(game.canCount(occ));
            // listObjects.setToolTipText(game.getOccupantDescription(occ)); TODO: remove ToolTip, and instead show in description panel
        }
    }

    private void btnUseActionPerformed() { game.useItem(listInventory.getSelectedValue()); }

    private void listInventoryValueChanged() {
        Object item = listInventory.getSelectedValue();
        btnDrop.setEnabled(true);
        if (item != null) {
            btnUse.setEnabled(game.canUse(item));
            // listInventory.setToolTipText(game.getOccupantDescription(item)); TODO: remove ToolTip, and instead show in description panel
        }
    }

    private void btnCountActionPerformed() { game.countKiwi(); }

    /**
     * Creates and initialises the island grid.
     */
    private void initIslandGrid() {
        // Add the grid
        int rows = game.getNumRows();
        int columns = game.getNumColumns();
        // set up the layout manager for the island grid panel
        pnlIsland.setLayout(new GridLayout(rows, columns));
        // create all the grid square panels and add them to the panel
        // the layout manager of the panel takes care of assigning them to the
        // the right position
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                pnlIsland.add(new GridSquarePanel(game, row, col));
            }
        }
    }

    class MoveSouthAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.playerMove(MoveDirection.SOUTH);
        }
    }
    class MoveNorthAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.playerMove(MoveDirection.NORTH);
        }
    }
    class MoveWestAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.playerMove(MoveDirection.WEST);
        }
    }
    class MoveEastAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.playerMove(MoveDirection.EAST);
        }
    }
}
