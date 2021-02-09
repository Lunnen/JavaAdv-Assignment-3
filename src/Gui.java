import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget.
Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */
    public class Gui extends JFrame {

        private final Border defBorder = BorderFactory.createEmptyBorder(0, 10, 0, 10);

        private JPanel panel;
        private JPanel roomPanel;
        private JPanel invPanel;
        private JPanel buttonPanel;
        private JPanel tradePanel;
        private JPanel tradeButtons;

        private JTextArea showRoom;
        private JTextArea showPersons;
        private JTextArea inventory;
        private JTextField tradeInput;
        private JButton buttonForward;
        private JButton buttonBack;
        private JButton button3;
        private JButton button4;
        private JButton button5;
        Game game;

        public Gui(Game game){
            this.game = game;
            this.setTitle("EC Java Advanced -> The Game");
            this.setIconImage(new ImageIcon("./ico/icon.png").getImage()); //Application icon
            this.setSize(900, 360);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setUpElements();
            setUpPanel();
            this.add(panel);
            this.setVisible(true);
            this.setResizable(false);
        }

        // For changing what's shown in the GUI
        public void setShowRoom(String roomDescription){
            this.showRoom.setText(roomDescription);
         }
        public void setNPC(String inputNPC){
        this.showPersons.setText(inputNPC);
    }
        public void setShowInventory(String inventory){
            this.inventory.setText(inventory);
        }

        private void setUpPanel(){
            this.roomPanel.add(showRoom);
            this.roomPanel.add(showPersons);
            this.invPanel.add(inventory);

            this.buttonPanel.add(buttonBack);
            this.buttonPanel.add(buttonForward);

            this.tradePanel.add(tradeInput);
            this.tradeButtons.add(button3);
            this.tradeButtons.add(button4);
            this.tradeButtons.add(button5);

            //Set order of panels
            this.panel.add(this.roomPanel);
            this.panel.add(this.invPanel);
            this.panel.add(this.buttonPanel);
            this.panel.add(this.tradePanel);
            this.panel.add(this.tradeButtons);
        }
        private void setUpElements(){

            //JPanel settings
            this.panel = new JPanel(null);

            this.roomPanel = new JPanel(new GridLayout(1,1, 5, 5));
            this.roomPanel.setBounds(5, 5, 875, 100);

            this.invPanel = new JPanel(new GridLayout(1,1));
            this.invPanel.setBounds(5, 110, 875, 40);

            this.buttonPanel = new JPanel(new GridLayout(1,2, 5, 5));
            this.buttonPanel.setBounds(5, 155, 875, 50);

            this.tradePanel = new JPanel(new GridLayout(1,1));
            this.tradePanel.setBounds(255, 210, 400, 50);

            this.tradeButtons = new JPanel(new GridLayout(1,3, 5, 5));
            this.tradeButtons.setBounds(5, 265, 875, 50);

            // Variable Settings
            this.showRoom = new JTextArea("Room: ");
            this.showRoom.setBorder(defBorder);
            this.showPersons = new JTextArea("NPC's in the room: ");
            this.showPersons.setBorder(defBorder);
            this.inventory = new JTextArea("Player Inventory: ");
            this.inventory.setBorder(defBorder);
            this.tradeInput = new JTextField("");
            this.tradeInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Name of item to Trade/Drop/Pickup:"));
            this.showPersons.setEditable(false);
            this.showRoom.setEditable(false);
            this.inventory.setEditable(false);

            // ************************************************************************
            this.buttonForward = new JButton("Enter next room");
            ActionListener inputListenerChangeRoom = event -> {

                if(event.getActionCommand().equals("Go back a room")) {
                    game.goBack();
                } else {
                    game.goForward();
                }
            };
            buttonForward.addActionListener(inputListenerChangeRoom);

            this.buttonBack = new JButton("Go back a room");
            buttonBack.addActionListener(inputListenerChangeRoom);
            // ************************************************************************

            this.button3 = new JButton("Trade with NPC");
            ActionListener inputListenerObject = action -> {
                System.out.println("Trade this " + tradeInput.getText());
                System.out.println(action.getActionCommand());

                game.runTrading(action.getActionCommand(), tradeInput.getText()); // Send button pushed & item name to method
            };
            button3.addActionListener(inputListenerObject);

            this.button4 = new JButton("Grab from floor");
            button4.addActionListener(inputListenerObject);

            this.button5 = new JButton("Drop to floor");
            button5.addActionListener(inputListenerObject);
            // ************************************************************************

        }
    }









