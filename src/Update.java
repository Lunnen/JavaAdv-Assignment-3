/*
Update Ska implementera Runnable och starta en tråd
som Regelbundet updaterar Guit utifrån vad som händer i spelet
 */

import java.util.List;

public class Update implements Runnable {
    boolean gameIsOn;
    Gui gui;
    List<Person> npcs;
    List<Room> basementRooms;
    Player player;
    Inventory inventory;

    public Update(boolean gameIsOn, Gui gui, List<Person> npcs, List<Room> basement, Player player, Inventory playerInventory) {
        this.gameIsOn = gameIsOn;
        this.gui = gui;
        this.npcs = npcs;
        this.basementRooms = basement;
        this.player = player;
        this.inventory = playerInventory;
    }

    @Override
    public void run() {

        while (this.gameIsOn) {

            System.out.println(("Loop is Running")); // ! REMOVE !

            switch (player.getCurrentPlayerRoom()) {
                case 0 -> changeViewedInGui(0);
                case 1 -> changeViewedInGui(1);
                case 2 -> changeViewedInGui(2);
                case 3 -> changeViewedInGui(3);
            }
            try { //Test for NPC moving around
                //Random random = new Random();
                //System.out.println(random.nextInt(4));

                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Main contact between GUI and Game. Shows current values.
    -> gui.setPerson is set by a Ternary operator.
     */
    public void changeViewedInGui(int currentRoom) {
        gui.setShowRoom(basementRooms.get(player.getCurrentPlayerRoom()).toString());

        boolean ifPersonsInRoom = basementRooms.get(currentRoom).getPersons().length() > 0;
        String defaultMsg = "NPCs in the room: \n";
        gui.setNPC(ifPersonsInRoom ? defaultMsg + basementRooms.get(currentRoom).getPersons() : defaultMsg + "There's no one in the room");

        gui.setShowInventory("Player inventory: \n" + inventory.toString());

    }
}
