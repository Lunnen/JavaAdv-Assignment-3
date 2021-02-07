import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/*Extremt enkelt Gui för att kunna komma igång.
Snygga gärna till/gör ett eget.
Men tänk på att gör GUI:s INTE är ett kursmoment - så fastna inte här!
 */
    public class Gui extends JFrame {

        private JPanel panel;
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
            this.setTitle("EC JavaAdv - The Game");
            this.setIconImage(new ImageIcon("./ico/icon.png").getImage()); //Application icon
            this.setSize(900, 750);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setUpElements();
            setUpPanel();
            this.add(panel);
            this.setVisible(true);
            this.setResizable(false);
        }

        //Här kan man updatera respektive fält eller Add person to room
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

            this.panel.add(showRoom);
            this.panel.add(showPersons);
            this.panel.add(inventory);
            this.panel.add(tradeInput);

            this.panel.add(buttonBack);
            this.panel.add(buttonForward);
            this.panel.add(button3);
            this.panel.add(button4);
            this.panel.add(button5);

        }
        private void setUpElements(){

            this.panel = new JPanel(new GridLayout(7,1, 5, 5));

            this.showRoom = new JTextArea("Room: ");
            this.showPersons = new JTextArea("NPC's in the room: ");
            this.inventory = new JTextArea("Player Inventory: ");
            this.tradeInput = new JTextField("Name of item to Trade/Drop/Pickup: ");
            this.showPersons.setEditable(false);
            this.showRoom.setEditable(false);
            this.inventory.setEditable(false);

            // ************************************************************************
            this.buttonForward = new JButton("Enter next room");
            ActionListener inputListener1 = e -> {
                game.goForward();
            };
            buttonForward.addActionListener(inputListener1);

            // ************************************************************************
            this.buttonBack = new JButton("Go back a room");
            ActionListener inputListener2 = e -> {
                game.goBack();
            };
            buttonBack.addActionListener(inputListener2);

            // ************************************************************************

            this.button3 = new JButton("Trade TEST");
            ActionListener inputListener3 = actionEvent -> {
                game.setTradeObjectName(tradeInput.getText());
            };
            button3.addActionListener(inputListener3);

            // ************************************************************************

            this.button4 = new JButton("Test ITEM-FLOOR");
            ActionListener inputListener4 = f ->  {
                game.getFirstItemFromFloor();
            };
            button4.addActionListener(inputListener4);

            // ************************************************************************
            this.button5 = new JButton("Drop object (not implemented yet)");
            ActionListener inputListener5 = g ->  {

                System.out.println("Trade this" + tradeInput.getText());
                //game.testCheckThis(tradeInput.getText())
            };
            button5.addActionListener(inputListener5);
            // ************************************************************************

        }
    }









