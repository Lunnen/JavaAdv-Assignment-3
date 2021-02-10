import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serializable;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget.
Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */
public class Gui extends JFrame implements Serializable {

    private final Border defBorder = BorderFactory.createEmptyBorder(0, 10, 0, 10);

    private JPanel panel;
    private JPanel roomPanel;
    private JPanel invInfoPanel;
    private JPanel buttonPanel;
    private JPanel tradePanel;
    private JPanel tradeButtons;

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

        this.tradePanel.add(tradeInput);
        this.tradeButtons.add(button3);
        this.tradeButtons.add(button4);
        this.tradeButtons.add(button5);
        this.tradeButtons.add(button6);

        //Set order of panels
        this.panel.add(this.roomPanel);
        this.panel.add(this.invInfoPanel);
        this.panel.add(this.buttonPanel);
        this.panel.add(this.tradePanel);
        this.panel.add(this.tradeButtons);
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

        this.tradePanel = new JPanel(new GridLayout(1, 1));
        this.tradePanel.setBounds(255, 280, 400, 40);

        this.tradeButtons = new JPanel(new GridLayout(1, 4, 5, 5));
        this.tradeButtons.setBounds(5, 325, 875, 40);

        // Variable Settings
        this.showRoom = new JTextArea("");
        this.showRoom.setBorder(defBorder);

        this.showPersons = new JTextArea("");
        this.showPersons.setBorder(defBorder);

        this.inventory = new JTextArea("");
        this.inventory.setBorder(defBorder);

        this.infoConsole = new JTextArea("");
        this.infoConsole.setBorder(defBorder);
        this.infoConsole.setForeground(Color.red); //Important info the player needs to be visible

        this.tradeInput = new JTextField("");
        this.tradeInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name of item to Trade/Drop/Pickup/Open door with: "));

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
                //case "Load Game" -> game = game.load(); <--- Disabled
            }

        };
        buttonForward.addActionListener(inputListenerChangeRoom);

        this.buttonBack = new JButton("Go back a room");
        buttonBack.addActionListener(inputListenerChangeRoom);
        this.buttonSave = new JButton("Save Game");
        buttonSave.addActionListener(inputListenerChangeRoom);
        this.buttonLoad = new JButton("Load Game (Disabled)");
        buttonLoad.addActionListener(inputListenerChangeRoom);
        // ************************************************************************
        this.button3 = new JButton("Trade with NPC");
        ActionListener inputListenerObject = action -> {

            game.runTrading(action.getActionCommand(), tradeInput.getText()); // Send button pushed & item name to method
        };
        button3.addActionListener(inputListenerObject);

        this.button4 = new JButton("Grab from floor");
        button4.addActionListener(inputListenerObject);

        this.button5 = new JButton("Drop to floor");
        button5.addActionListener(inputListenerObject);

        this.button6 = new JButton("Open door");
        button6.addActionListener(inputListenerObject);
        // ************************************************************************

    }
}









