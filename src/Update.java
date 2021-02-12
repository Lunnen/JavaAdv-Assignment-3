/*
Update Ska implementera Runnable och starta en tråd
som Regelbundet updaterar Guit utifrån vad som händer i spelet
 */

import java.io.Serializable;
import java.util.List;

public class Update implements Runnable {
    Gui gui;
    List<Room> basementRooms;
    Player player;

    public Update(Gui gui, List<Room> basement, Player player) {
        this.gui = gui;
        this.basementRooms = basement;
        this.player = player;
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

        gui.setShowInventory(player.getPlayerInventory().toString());
    }
}
