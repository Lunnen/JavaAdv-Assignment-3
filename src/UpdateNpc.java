/*
Update Ska implementera Runnable och starta en tråd
som Regelbundet updaterar Guit utifrån vad som händer i spelet
 */

import java.util.List;
import java.util.Random;

public class UpdateNpc implements Runnable {
    boolean gameIsOn;
    Gui gui;
    List<Person> npcs;
    List<Room> basementRooms;
    Player player;
    Inventory inventory;
    List<GameObject> items;
    Random random = new Random();

    public UpdateNpc(List<GameObject> items, boolean gameIsOn, Gui gui, List<Person> npcs, List<Room> basement, Player player, Inventory playerInventory) {
        this.items = items;
        this.gameIsOn = gameIsOn;
        this.gui = gui;
        this.npcs = npcs;
        this.basementRooms = basement;
        this.player = player;
        this.inventory = playerInventory;
    }

    public void run() {
        /*
        This is where all the NPC AI happens.
        */

        //NPC Info
        int chosenNPC = 2; // 0 - Jason, 1 - Freddy, 2 - Ture
        int currentNpcPosition = (npcs.get(chosenNPC).getPosition());

        //NPC actions
        boolean shouldNpcAct = random.nextBoolean();

        if (shouldNpcAct) { // 50-50 that he's going to do something.

            GameObject wantedObject = items.get(2); //Grab shield from floor


            if(basementRooms.get(0).getRoomInventory().findIndexOf(wantedObject) >=0 && //WantedObject exists on the floor
                    npcs.get(chosenNPC).getNpcInventory().findIndexOf(null) >= 0){ //And there's space in npc inventory
                basementRooms.get(0).getRoomInventory().moveObject(npcs.get(chosenNPC).getNpcInventory(), wantedObject); //Take object
            }


            /*
            boolean forwardOrBackward = random.nextBoolean();// KEEP THIS, so that both if below are based on the same  value.

            if (forwardOrBackward && (currentNpcPosition >= 0 && currentNpcPosition < 3)) { //50-50 if go next room or back a room.
                npcs.get(chosenNPC).setPosition(currentNpcPosition + 1);
                System.out.println(currentNpcPosition + " > NPC UP");

            } else if (!forwardOrBackward && (currentNpcPosition > 0 && currentNpcPosition < 4)) {
                npcs.get(chosenNPC).setPosition(currentNpcPosition - 1);
                System.out.println(currentNpcPosition + " > NPC DOWN");
            }

             */
        }
        // Keep track of NPC, and update GUI accordingly.
        for (int i = 0; i < basementRooms.size(); i++) {

            if ((basementRooms.get(i).findIndexOf(npcs.get(chosenNPC)) != -1) && (i != npcs.get(chosenNPC).getPosition())) { //if NPC is in the room, and should NOT be there

                basementRooms.get(i).changeNPCRoom(basementRooms.get(npcs.get(chosenNPC).getPosition()), npcs.get(chosenNPC)); // move NPC to where he should be.
                System.out.println("NPC > GUI UPDATED");
            }

        }
        // **************************************************************************

    }

}
