import java.util.List;
import java.util.Random;

public class UpdateNpc implements Runnable {

    Gui gui;
    List<Person> npcs;
    List<Room> basementRooms;
    Player player;
    List<GameObject> items;
    Random random = new Random();
    int chosenNPC;

    public UpdateNpc(int whichNPC, List<GameObject> items, Gui gui, List<Person> npcs, List<Room> basement, Player player) {
        this.chosenNPC = whichNPC;
        this.items = items;
        this.gui = gui;
        this.npcs = npcs;
        this.basementRooms = basement;
        this.player = player;
    }

    public void run() {
        /* This is where all the NPC AI happens. */

        //NPC Info Settings
        int currentNpcPosition = (npcs.get(chosenNPC).getPosition());
        boolean npcHasStuff = npcs.get(chosenNPC).getNpcInventory() != null;
        boolean floorHasStuff = basementRooms.get(currentNpcPosition).getRoomInventory() != null;
        GameObject wantedObject;

        /*
        NPC Talking - sometimes */
        int ifNpcTalk = random.nextInt(5);
        if(ifNpcTalk == 0 && player.getCurrentPlayerRoom() == npcs.get(chosenNPC).getPosition()){
            gui.setConsoleLog(npcs.get(chosenNPC).getName() + " says: " + returnQuote(npcs.get(chosenNPC).getName()));
        }

        boolean shouldNpcAct = random.nextBoolean();
        if (shouldNpcAct) { // 50-50 that he's going to do something.

            boolean ifStuffExist = (npcHasStuff | floorHasStuff);
            if (ifStuffExist) { //IF Npc or room has stuff to pickup/drop - run option to random if move or pickup/drop.

                boolean moveOrStuff = random.nextBoolean();
                if (moveOrStuff) { //Move to other room or pick/drop stuff

                    changeRoom(currentNpcPosition);
                } else {

                    for (GameObject item : items) { //loop through all items = pick up any type of item
                        wantedObject = item; //check all items

                        if (floorHasStuff && npcHasStuff) {

                            boolean grabOrDrop = random.nextBoolean();
                            if (grabOrDrop) { // 50-50 grab or drop
                                grabFromFloor(wantedObject, currentNpcPosition);
                            } else {
                                dropToFloor(wantedObject, currentNpcPosition);
                            }
                        } else if (floorHasStuff) {
                            grabFromFloor(wantedObject, currentNpcPosition);
                        } else {
                            dropToFloor(wantedObject, currentNpcPosition);
                        }
                    }
                }
            } else {
                changeRoom(currentNpcPosition);
            }
        }

        // Keep track of NPC, and update GUI accordingly.
        for (int i = 0; i < basementRooms.size(); i++) {

            if ((basementRooms.get(i).findIndexOf(npcs.get(chosenNPC)) != -1) && (i != npcs.get(chosenNPC).getPosition())) { //if NPC is in the room, and should NOT be there

                basementRooms.get(i).changeNPCRoom(basementRooms.get(npcs.get(chosenNPC).getPosition()), npcs.get(chosenNPC)); // move NPC to where he should be.
            }
        }
    }

    public void changeRoom(int currentNpcPosition) {

        /* NPC moving to adjoining rooms */
        boolean forwardOrBackward = random.nextBoolean();// KEEP THIS, so that both if below are based on the same  value.
        if (forwardOrBackward && currentNpcPosition < 3) { //50-50 if go next room or back a room.
            npcs.get(chosenNPC).setPosition(currentNpcPosition + 1);

        } else if (!forwardOrBackward && (currentNpcPosition > 0)) {
            npcs.get(chosenNPC).setPosition(currentNpcPosition - 1);
        }
    }

    public void grabFromFloor(GameObject wantedObject, int currentNpcPosition) {

        Inventory currentRoomInv = basementRooms.get(currentNpcPosition).getRoomInventory();
        Inventory currentNpcInv = npcs.get(chosenNPC).getNpcInventory();

        if (currentRoomInv.findIndexOf(wantedObject) >= 0 && //WantedObject exists on the floor
                currentNpcInv.findIndexOf(null) >= 0) { //And there's space in npc inventory
            currentRoomInv.moveObject(currentNpcInv, wantedObject); //Take object
        }
    }

    public void dropToFloor(GameObject wantedObject, int currentNpcPosition) {

        Inventory currentRoomInv = basementRooms.get(currentNpcPosition).getRoomInventory();
        Inventory currentNpcInv = npcs.get(chosenNPC).getNpcInventory();

        if (currentNpcInv.findIndexOf(wantedObject) >= 0 && //WantedObject exists in NPC inv.
                currentRoomInv.findIndexOf(null) >= 0) { //And there's space on the floor
            currentNpcInv.moveObject(currentRoomInv, wantedObject); //Drop object
        }

    }
    public String returnQuote(String chosenNpcName){

        String[] tureQuotes = { "Ständigt denna Vessla", "Använd bara pitolerna i nödfall", "Ett intressant fall på det hela taget"};
        String[] jasonQuotes = { "Doomed... You're all doomed.", "Jason?", ".... [Muttering] ...", };
        String[] freddyQuotes = { "Wanna Suck Face?", "What A Rush!", "How Sweet, Fresh Meat!", "I'll Get You My Pretty, And Your Little Soul Too!", "Every Town Has An Elm Street!" };

        switch(chosenNpcName){
            case "Jason Voorhees" -> {
                return jasonQuotes[random.nextInt(jasonQuotes.length)];
            }
            case "Freddy Krueger" -> {
                return freddyQuotes[random.nextInt(freddyQuotes.length)];
            }
            case "Ture Sventon" -> {
                return tureQuotes[random.nextInt(tureQuotes.length)];
            }
        }
        return "";
    }

}
