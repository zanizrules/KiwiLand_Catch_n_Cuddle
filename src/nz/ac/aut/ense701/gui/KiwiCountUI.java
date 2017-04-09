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
 * User interface form for Kiwi Island.
 */
public class KiwiCountUI extends JFrame implements GameEventListener {
    private JButton btnCollect;
    private JButton btnCount;
    private JButton btnDrop;
    private JButton btnUse;

    //private JLabel txtKiwisCounted;
    private JLabel txtPlayerName;
    //private JLabel txtPredatorsLeft;
    //private JLabel lblKiwisCounted;
    //private JLabel lblPredators;
    private JLabel lblScore;
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
    private InputMap inputMappings; // = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
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
        // update the grid square panels
        Component[] components = pnlIsland.getComponents();
        for (Component c : components) {
            // all components in the panel are GridSquarePanels,
            // so we can safely cast
            GridSquarePanel gsp = (GridSquarePanel) c;
            gsp.update();
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

        //Update Kiwi and Predator information
        //txtKiwisCounted.setText(Integer.toString(game.getKiwiCount())); TODO : remove Kiwis Counted from GUI
        //txtPredatorsLeft.setText(Integer.toString(game.getPredatorsRemaining())); TODO : remove Predators Left from GUI

        // Update score
        txtScore.setText(Integer.toString(game.getScore()));

        // update inventory list
        listInventory.setListData(game.getPlayerInventory());
        listInventory.clearSelection();
        //listInventory.setToolTipText(null); TODO: remove ToolTip
        btnUse.setEnabled(false);
        btnDrop.setEnabled(false);

        // update list of visible objects
        listObjects.setListData(game.getOccupantsPlayerPosition());
        listObjects.clearSelection();
        // listObjects.setToolTipText(null); TODO: remove ToolTip
        btnCollect.setEnabled(false);
        btnCount.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        JPanel pnlContent = new JPanel();
        pnlIsland = new JPanel();
        JPanel pnlControls = new JPanel();
        JPanel pnlPlayer = new JPanel();
        JPanel pnlPlayerData = new JPanel();
        JLabel lblPlayerName = new JLabel();
        txtPlayerName = new JLabel();
        JLabel lblPlayerStamina = new JLabel();
        progPlayerStamina = new JProgressBar();
        JLabel lblBackpackWeight = new JLabel();
        progBackpackWeight = new JProgressBar();
        JLabel lblBackpackSize = new JLabel();
        progBackpackSize = new JProgressBar();
        //lblPredators = new JLabel();
        //lblKiwisCounted = new JLabel();
        //txtKiwisCounted = new JLabel();
        //txtPredatorsLeft = new JLabel();
        lblScore = new JLabel();
        txtScore = new JLabel();

        JPanel pnlInventory = new JPanel();
        JScrollPane scrlInventory = new JScrollPane();
        listInventory = new JList();
        btnDrop = new JButton();
        btnUse = new JButton();
        JPanel pnlObjects = new JPanel();
        JScrollPane scrlObjects = new JScrollPane();
        listObjects = new JList();
        btnCollect = new JButton();
        btnCount = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kiwi Count");

        pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlContent.setLayout(new java.awt.BorderLayout(10, 0));

        GroupLayout pnlIslandLayout = new GroupLayout(pnlIsland);
        pnlIsland.setLayout(pnlIslandLayout);
        pnlIslandLayout.setHorizontalGroup(pnlIslandLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 540, Short.MAX_VALUE)
        );
        pnlIslandLayout.setVerticalGroup(pnlIslandLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 618, Short.MAX_VALUE));

        pnlContent.add(pnlIsland, java.awt.BorderLayout.CENTER);

        pnlControls.setLayout(new java.awt.GridBagLayout());

        pnlPlayer.setBorder(BorderFactory.createTitledBorder("Player"));
        pnlPlayer.setLayout(new java.awt.BorderLayout());

        pnlPlayerData.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlPlayerData.setLayout(new java.awt.GridBagLayout());

        lblPlayerName.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        pnlPlayerData.add(lblPlayerName, gridBagConstraints);

        txtPlayerName.setText("Player Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        pnlPlayerData.add(txtPlayerName, gridBagConstraints);

        lblPlayerStamina.setText("Stamina:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblPlayerStamina, gridBagConstraints);

        progPlayerStamina.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progPlayerStamina, gridBagConstraints);

        lblBackpackWeight.setText("Backpack Weight:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblBackpackWeight, gridBagConstraints);

        progBackpackWeight.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progBackpackWeight, gridBagConstraints);

        lblBackpackSize.setText("Backpack Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblBackpackSize, gridBagConstraints);

        progBackpackSize.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progBackpackSize, gridBagConstraints);

        /* Remove "Predators Left" from GUI
        lblPredators.setText("Predators Left:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(lblPredators, gridBagConstraints);


        // Remove "Kiwis Counted" section from GUI
        lblKiwisCounted.setText("Kiwis Counted :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(lblKiwisCounted, gridBagConstraints);

        //Remove "Kiwis Counted" count from GUI
        txtKiwisCounted.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(txtKiwisCounted, gridBagConstraints);

        //Remove "Predators Left" count from GUI
        txtPredatorsLeft.setText("P");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(txtPredatorsLeft, gridBagConstraints);
        */

        lblScore.setText("Score :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(lblScore, gridBagConstraints);

        txtScore.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(txtScore, gridBagConstraints);

        pnlPlayer.add(pnlPlayerData, java.awt.BorderLayout.WEST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        pnlControls.add(pnlPlayer, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.add(descriptionArea, gridBagConstraints);
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
        pnlControls.add(descriptionPanel, gridBagConstraints);

        // TODO: Add description panel here : pnlControls.add(pnlDescription)

        pnlInventory.setBorder(BorderFactory.createTitledBorder("Inventory"));
        pnlInventory.setLayout(new GridBagLayout());

        listInventory.setModel(new AbstractListModel() {
            String[] strings = {"Item 1", "Item 2", "Item 3"};

            public int getSize() { return strings.length; }

            public Object getElementAt(int i) { return strings[i]; }
        });
        listInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInventory.setVisibleRowCount(3);
        listInventory.addListSelectionListener(evt2 -> listInventoryValueChanged());
        scrlInventory.setViewportView(listInventory);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(scrlInventory, gridBagConstraints);

        btnDrop.setText("Drop");
        btnDrop.addActionListener(evt1 -> btnDropActionPerformed());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(btnDrop, gridBagConstraints);

        btnUse.setText("Use");
        btnUse.addActionListener(evt1 -> btnUseActionPerformed());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(btnUse, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlControls.add(pnlInventory, gridBagConstraints);

        pnlObjects.setBorder(BorderFactory.createTitledBorder("Objects"));
        java.awt.GridBagLayout pnlObjectsLayout = new java.awt.GridBagLayout();
        pnlObjectsLayout.columnWidths = new int[]{0, 5, 0};
        pnlObjectsLayout.rowHeights = new int[]{0, 5, 0};
        pnlObjects.setLayout(pnlObjectsLayout);

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
        scrlObjects.setViewportView(listObjects);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlObjects.add(scrlObjects, gridBagConstraints);

        btnCollect.setText("Collect");
        btnCollect.setToolTipText("");
        btnCollect.setMaximumSize(new java.awt.Dimension(61, 23));
        btnCollect.setMinimumSize(new java.awt.Dimension(61, 23));
        btnCollect.setPreferredSize(new java.awt.Dimension(61, 23));
        btnCollect.addActionListener(evt -> btnCollectActionPerformed());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlObjects.add(btnCollect, gridBagConstraints);

        btnCount.setText("Count");
        btnCount.addActionListener(evt -> btnCountActionPerformed());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlObjects.add(btnCount, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlControls.add(pnlObjects, gridBagConstraints);

        pnlContent.add(pnlControls, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);
        pack();

        inputMappings = pnlContent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMappings = pnlContent.getActionMap();
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
