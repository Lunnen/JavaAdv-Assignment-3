/*
Update Ska implementera Runnable och starta en tråd
som Regelbundet updaterar Guit utifrån vad som händer i spelet
 */

import java.util.List;

public class Update implements Runnable {
    Gui gui;
    List<Room> basementRooms;
    Player player;
    Inventory inventory;

    public Update(Gui gui, List<Room> basement, Player player, Inventory playerInventory) {
        this.gui = gui;
        this.basementRooms = basement;
        this.player = player;
        this.inventory = playerInventory;
    }

    public void run() {
        /*
         Main contact between GUI and Game.
         Keeps a record of what to show in the GUI, based on currentPlayerRoom.
         -> gui.setPerson is set by a Ternary operator.
        */
        int currentRoom = player.getCurrentPlayerRoom();

        gui.setShowRoom(basementRooms.get(player.getCurrentPlayerRoom()).toString());

        boolean ifPersonsInRoom = basementRooms.get(currentRoom).getPersons().length() > 0;
        gui.setNPC(ifPersonsInRoom ? basementRooms.get(currentRoom).getPersons() : "There's no one in the room");

        gui.setShowInventory(inventory.toString());
    }
}
