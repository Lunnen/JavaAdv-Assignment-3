import java.util.List;
import java.util.Random;

public class UpdateNpc implements Runnable {

    Gui gui;
    List<Person> npcs;
    List<Room> basementRooms;
    Player player;
    Inventory inventory;
    List<GameObject> items;
    Random random = new Random();
    int chosenNPC;

    public UpdateNpc(int whichNPC, List<GameObject> items, Gui gui, List<Person> npcs, List<Room> basement, Player player, Inventory playerInventory) {
        this.chosenNPC = whichNPC;
        this.items = items;
        this.gui = gui;
        this.npcs = npcs;
        this.basementRooms = basement;
        this.player = player;
        this.inventory = playerInventory;
    }

    public void run() {
        /* This is where all the NPC AI happens. */

        //NPC Info Settings
        int currentNpcPosition = (npcs.get(chosenNPC).getPosition());
        boolean npcHasStuff = npcs.get(chosenNPC).getNpcInventory() != null;
        boolean floorHasStuff = basementRooms.get(currentNpcPosition).getRoomInventory() != null;

        boolean shouldNpcAct = random.nextBoolean();
        if (shouldNpcAct) { // 50-50 that he's going to do something.
            System.out.println("NPC ACT");

            boolean ifStuffExist = npcHasStuff | floorHasStuff;
            if (ifStuffExist) { //IF Npc or room has stuff to pickup/drop - run option to random if move or pickup/drop.

                boolean moveOrStuff = random.nextBoolean();
                if (moveOrStuff) { //Move to other room or pick/drop stuff

                    /* NPC moving to adjoining rooms */
                    boolean forwardOrBackward = random.nextBoolean();// KEEP THIS, so that both if below are based on the same  value.
                    if (forwardOrBackward  && currentNpcPosition < 3) { //50-50 if go next room or back a room.
                        npcs.get(chosenNPC).setPosition(currentNpcPosition + 1);
                        System.out.println(currentNpcPosition + " > NPC UP");

                    } else if (!forwardOrBackward && (currentNpcPosition > 0)) {
                        npcs.get(chosenNPC).setPosition(currentNpcPosition - 1);
                        System.out.println(currentNpcPosition + " > NPC DOWN");
                    }
                } else {

                    /* NPC grabbing or dropping objects of/to the floor */
                    GameObject wantedObject;
                    boolean grabOrDrop = random.nextBoolean();

                    for (GameObject item : items) { //loop through all items = pick up any type of item
                        wantedObject = item; //check all items

                        if (floorHasStuff && npcHasStuff) {

                            if (grabOrDrop) { // 50-50 grab or drop
                                grabFromFloor(wantedObject, currentNpcPosition);
                            } else {
                                dropToFloor(wantedObject, currentNpcPosition);
                            }
                        }
                        else if(floorHasStuff){
                            grabFromFloor(wantedObject, currentNpcPosition);
                        }
                        else {
                            dropToFloor(wantedObject, currentNpcPosition);
                        }
                    }
                }
            }
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
    public void grabFromFloor(GameObject wantedObject, int currentNpcPosition) {
        if (basementRooms.get(currentNpcPosition).getRoomInventory().findIndexOf(wantedObject) >= 0 && //WantedObject exists on the floor
                npcs.get(chosenNPC).getNpcInventory().findIndexOf(null) >= 0) { //And there's space in npc inventory
            basementRooms.get(currentNpcPosition).getRoomInventory().moveObject(npcs.get(chosenNPC).getNpcInventory(), wantedObject); //Take object
        }
        System.out.println("npc nr " + chosenNPC + "Pick looping " + wantedObject);
    }
    public void dropToFloor(GameObject wantedObject, int currentNpcPosition){
        if (npcs.get(chosenNPC).getNpcInventory().findIndexOf(wantedObject) >= 0 && //WantedObject exists in NPC inv.
                basementRooms.get(currentNpcPosition).getRoomInventory().findIndexOf(null) >= 0) { //And there's space on the floor
            npcs.get(chosenNPC).getNpcInventory().moveObject(basementRooms.get(currentNpcPosition).getRoomInventory(), wantedObject); //Drop object
            System.out.println("Npc nr " + chosenNPC + "Drop looping" + wantedObject + "pos" + currentNpcPosition);
        }
    }



}
