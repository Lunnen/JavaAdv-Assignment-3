/*
Update Ska implementera Runnable och starta en tråd
som Regelbundet updaterar Guit utifrån vad som händer i spelet
 */

import java.util.List;
import java.util.Random;

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

            //System.out.println(("Loop is Running")); // ! REMOVE !

            changeViewedInGui(player.getCurrentPlayerRoom());

            for (int i = 0; i < basementRooms.size(); i++){ // Call NPC to do stuff
                Random random = new Random();
                //basementRooms.get(random.nextInt(4)).addNpc(npcs.get(0));
                int randomNr = random.nextInt(3);
                boolean randomBool = random.nextBoolean();
                System.out.println(randomNr);

                //Jason moving around
                if(randomBool) {
                    npcs.get(0).setPosition(randomNr);
                }

                //int chosenNPC = 0
                if((basementRooms.get(i).findIndexOf(npcs.get(0)) != -1) && (i != npcs.get(0).getPosition())){ //if NPC is in the room, and should NOT be there
                    basementRooms.get(i).changeNPCRoom(basementRooms.get(npcs.get(0).getPosition()), npcs.get(0)); // in this room, move to where he should be.
                    System.out.println("NPC RUNNING");
                }
            }

        try { //Test for NPC moving around
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
