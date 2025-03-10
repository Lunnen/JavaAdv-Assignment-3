import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private final Border defBorder = BorderFactory.createEmptyBorder(0, 10, 0, 10);

    private JPanel panel;
    private JPanel roomPanel;
    private JPanel invInfoPanel;
    private JPanel buttonPanel;
    private JPanel actionItem;
    private JPanel actionButtons;

    private JTextArea showRoom;
    private JTextArea showPersons;
    private JTextArea inventory;
    private JTextArea infoConsole;
    private JTextField tradeInput;

    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonForward;
    private JButton buttonBack;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    Game game;

    public Gui(Game game) {
        this.game = game;
        this.setTitle("EC Java Advanced -> The Game");
        this.setIconImage(new ImageIcon("./ico/icon.png").getImage()); //Application icon
        this.setSize(900, 410);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUpElements();
        setUpPanel();
        this.add(panel);
        this.setVisible(true);
        this.setResizable(false);
    }

    // For changing what's shown in the GUI
    public void setShowRoom(String roomDescription) {
        this.showRoom.setText(roomDescription);
    }

    public void setNPC(String inputNPC) {
        this.showPersons.setText("NPCs in the room: \n" + inputNPC);
    }

    public void setShowInventory(String inventory) {
        this.inventory.setText("Player inventory: \n" + inventory);
    }

    public void setConsoleLog(String consoleLog) {
        this.infoConsole.setText("Game console: \n" + consoleLog);
    }

    private void setUpPanel() {
        this.roomPanel.add(showRoom);
        this.roomPanel.add(showPersons);

        this.invInfoPanel.add(inventory);
        this.invInfoPanel.add(infoConsole);

        this.buttonPanel.add(buttonBack);
        this.buttonPanel.add(buttonForward);
        this.buttonPanel.add(buttonSave);
        this.buttonPanel.add(buttonLoad);

        this.actionItem.add(tradeInput);
        this.actionButtons.add(button3);
        this.actionButtons.add(button4);
        this.actionButtons.add(button5);
        this.actionButtons.add(button6);

        //Set order of panels
        this.panel.add(this.roomPanel);
        this.panel.add(this.invInfoPanel);
        this.panel.add(this.buttonPanel);
        this.panel.add(this.actionItem);
        this.panel.add(this.actionButtons);
    }

    private void setUpElements() {

        //JPanel settings
        this.panel = new JPanel(null);

        this.roomPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        this.roomPanel.setBounds(5, 5, 875, 100);

        this.invInfoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        this.invInfoPanel.setBounds(5, 110, 875, 80);

        this.buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        this.buttonPanel.setBounds(5, 195, 875, 80);

        this.actionItem = new JPanel(new GridLayout(1, 1));
        this.actionItem.setBounds(255, 280, 400, 40);

        this.actionButtons = new JPanel(new GridLayout(1, 4, 5, 5));
        this.actionButtons.setBounds(5, 325, 875, 40);

        // Variable Settings
        this.showRoom = new JTextArea("");
        this.showRoom.setBorder(defBorder);

        this.showPersons = new JTextArea("");
        this.showPersons.setBorder(defBorder);

        this.inventory = new JTextArea("");
        this.inventory.setBorder(defBorder);

        this.infoConsole = new JTextArea("");
        this.infoConsole.setBorder(defBorder);
        this.infoConsole.setForeground(Color.red);

        this.tradeInput = new JTextField("");
        this.tradeInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name of item to interact with: "));

        this.showPersons.setEditable(false);
        this.showRoom.setEditable(false);
        this.inventory.setEditable(false);
        this.infoConsole.setEditable(false);

        // ************************************************************************
        this.buttonForward = new JButton("Enter next room");
        ActionListener inputListenerChangeRoom = event -> {

            switch (event.getActionCommand()) {
                case "Enter next room" -> game.goForward();
                case "Go back a room" -> game.goBack();
                case "Save Game" -> game.save();
                case "Load Game" -> game.load();
            }

        };
        buttonForward.addActionListener(inputListenerChangeRoom);

        this.buttonBack = new JButton("Go back a room");
        buttonBack.addActionListener(inputListenerChangeRoom);
        this.buttonSave = new JButton("Save Game");
        buttonSave.addActionListener(inputListenerChangeRoom);
        this.buttonLoad = new JButton("Load Game");
        buttonLoad.addActionListener(inputListenerChangeRoom);
        // ************************************************************************
        this.button3 = new JButton("Trade with NPC");
        ActionListener inputListenerObject = action -> {

            // Send button pushed & item name to method
            game.itemActions(action.getActionCommand(), tradeInput.getText());
        };
        button3.addActionListener(inputListenerObject);

        this.button4 = new JButton("Grab from floor");
        button4.addActionListener(inputListenerObject);

        this.button5 = new JButton("Drop to floor");
        button5.addActionListener(inputListenerObject);

        this.button6 = new JButton("Open door/chest");
        button6.addActionListener(inputListenerObject);
        // ************************************************************************

    }
}









