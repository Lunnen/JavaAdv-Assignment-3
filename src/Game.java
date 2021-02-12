import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Game Game kommer att vara motorn"i spelet.
I konstruktorn kickar vi gång GUI:s och trådar.
Därefter startas spelloopen som tar in och
hanterar kommandon ur ett Textfield.
Updaterar spelet utifrån spelarens instruktioner.
Notera att Npc kommer att röra sig oavsett vad
spelaren gör. I Npc-TextArean ska man kunna
se vilka Npc:er som kommer och går i rummet.
 */

public class Game implements Serializable {

    Gui gui;
    Player player = new Player(0); //Player starts at first room (zero).
    List<Room> basementRooms = new ArrayList<>();
    List<Person> npcs = new ArrayList<>();
    List<GameObject> items = new ArrayList<>();

    Save save = new Save(player, basementRooms, npcs);

    boolean gameIsOn = true; // <--- remove?

    private final String fileName = "./mySave.bin";

    // All containers
    Container box = new Container("The Chest", 55, false, false);

    public Game() {
        this.gui = new Gui(this);

        createRooms(); // Create all four rooms.
        createNPC(); // Create the three NPC's according to assignment description.
        createItems(box); // Create in game items and keys.
        addNpcsToRoom(); // Adding the NPC's to different room start locations.

        /*
        The game only has a few items.
        You need to trade with NPC */
        npcs.get(0).getNpcInventory().addObject(items.get(1)); //Add normal key to Jason
        npcs.get(1).getNpcInventory().addObject(items.get(3)); //Add knife to Freddy
        basementRooms.get(3).addObjectToRoom(items.get(4)); //add Bread to last room
        basementRooms.get(0).addObjectToRoom(box); //add 'The chest' to first room.
        box.getContainerInventory().addObject(items.get(0)); //Add Victory key inside chest.

        gui.setConsoleLog("You've woken up inside a basement with several weird men. Try finding a way out of here!");

        /*
         Run the threads for Game update to GUI and NPC actions. */
        startThreadPool();
    }

    public void goForward() {
        if (player.getCurrentPlayerRoom() < 3) {
            player.setCurrentPlayerRoom(1 + (player.getCurrentPlayerRoom()));
        }
    }

    public void goBack() {
        if (player.getCurrentPlayerRoom() > 0) {
            player.setCurrentPlayerRoom(player.getCurrentPlayerRoom() - 1);
        }
    }

    public void createRooms() {
        basementRooms.add(new Room("First room", "A room with a large, wet patch in the corner."));
        basementRooms.add(new Room("Second room", "Lots of dirt on the walls. It looks a bit like charcoal..."));
        basementRooms.add(new Room("Third room", "Lots of blood stains around the room. What's happened here?"));
        basementRooms.add(new Room("Fourth room", "Actually kind of clean. I wonder why ...\nThere's a locked door at the end."));
    }

    public void createNPC() {
        npcs.add(new Person("Jason Voorhees", 1)); // <-- should be at 2
        npcs.add(new Person("Freddy Krueger", 1));
        npcs.add(new Person("Ture Sventon", 0));
    }

    public void addNpcsToRoom() {
        for (Person npc : npcs) { //Find where NPC should be, and add them to that room.
            if (basementRooms.get(npc.getPosition()).findIndexOf(npc) == -1) { //if NPC doesn't exist in room, add.
                basementRooms.get(npc.getPosition()).addNpc(npc);
            }
        }
    }

    public void createItems(Container box) {
        items.add(new Key("Victory Key", 99, true, box));
        items.add(new Key("Normal Key", 55, true, box));
        items.add(new GameObject("Shield", 20, true));
        items.add(new GameObject("Knife", 21, true));
        items.add(new GameObject("Bread", 23, true));
    }

    public void startThreadPool() {
        ScheduledExecutorService tPool = Executors.newScheduledThreadPool(5);

        Runnable thread1 = new Update(gui, basementRooms, player);
        Runnable thread2 = new UpdateNpc(2, items, gui, npcs, basementRooms, player);
        Runnable thread3 = new UpdateNpc(1, items, gui, npcs, basementRooms, player);

        tPool.scheduleAtFixedRate(thread1, 0, 700, TimeUnit.MILLISECONDS); //input 1, run this runnable, initialDelay, period = wait time in between, TimeUnit.
        tPool.scheduleAtFixedRate(thread2, 1, 3, TimeUnit.SECONDS); //Ture -> Quicker
        tPool.scheduleAtFixedRate(thread3, 5, 5, TimeUnit.SECONDS); //Freddy -> a bit slower
    }

    public void runTrading(String inputButton, String inputObjectName) {

        GameObject chosenItem = null; // create a temporary object for comparison. Used by runTrading method.
        Inventory temp = new Inventory(1); // Create a temporary inventory for comparison. Used for NPC trading

        switch (inputObjectName.toLowerCase()) {
            case "victory key" -> chosenItem = items.get(0);
            case "normal key" -> chosenItem = items.get(1);
            case "shield" -> chosenItem = items.get(2);
            case "knife" -> chosenItem = items.get(3);
            case "bread" -> chosenItem = items.get(4);
            default -> gui.setConsoleLog("WRONG/NO INPUT");
        }

        switch (inputButton) {
            case "Trade with NPC": // <--- ONLY POSSIBLE TO TRADE WITH FIRST NPC IN A ROOM

                for (Person npc : npcs) { // Run through all npcs
                    if (npc.getPosition() == player.currentPlayerRoom) { //If NPC is in player's room
                        if (npc.getNpcInventory().getFirstObject() != null && chosenItem != null && player.getPlayerInventory().checkExists(chosenItem)) { //If NPC has objects in his inv. and player has chosen his item & has it in inv.

                            npc.getNpcInventory().moveObject(temp, npc.getNpcInventory().getFirstObject()); // trade npc item to temp.
                            player.getPlayerInventory().moveObject(npc.getNpcInventory(), chosenItem); // move chosen item from player to npc inv.
                            temp.moveObject(player.getPlayerInventory(), temp.getFirstObject()); // move object from temp to player.inv.
                            break; //End loop - just trade with first NPC.
                        } else if(!player.getPlayerInventory().checkExists(chosenItem)){

                            gui.setConsoleLog("You don't own that object yet!");
                        } else {
                            gui.setConsoleLog("Pick item to trade and/or NPC has no objects");
                        }
                    }
                }
                break;
            // ******************************************
            case "Grab from floor":
                System.out.println("Gonna grab me some -> " + inputObjectName);
                basementRooms.get(player.currentPlayerRoom).getRoomInventory().moveObject(player.getPlayerInventory(), chosenItem); // move chosenItem from room.inv to player.inv.
                break;
            // ******************************************
            case "Drop to floor":
                System.out.println("Dropping stuff... -> " + inputObjectName);
                player.getPlayerInventory().moveObject(basementRooms.get(player.currentPlayerRoom).getRoomInventory(), chosenItem); // move chosenItem from player.inv to currentRoom.inv.

                break;
            // ******************************************
            case "Open door/chest":
                System.out.println("open with -> " + inputObjectName);

                Inventory playerInv = player.getPlayerInventory();

                switch (player.getCurrentPlayerRoom()) {
                    case 0 -> {

                        if (playerInv.checkExists(items.get(1))) {
                            Key key = (Key) chosenItem;
                            if (key.keyFits(box)) { // If it's the correct key, open chest.
                                gui.setConsoleLog("Chest has been opened. Loot transferred to player inventory.");

                                box.getContainerInventory().moveObject(playerInv, items.get(0)); //Move Victory key from chest to player.inv.
                                playerInv.removeObject(items.get(1)); //Remove key from player.inv.
                                basementRooms.get(player.currentPlayerRoom).getRoomInventory().removeObject(box); //Remove box from room.

                            }
                        } else if(playerInv.checkExists(items.get(0))){
                            gui.setConsoleLog("That's not the correct key");
                        } else {
                            gui.setConsoleLog("You don't have a key");
                        }
                    }
                    case 3 -> {

                        if (playerInv.checkExists(items.get(0))) {
                            gui.setConsoleLog("YOU'VE BEEN SET FREE! VICTORY! GAME END!");

                            //tPool.shutdownNow(); // Shutdown all threads
                            new Timer().schedule(new TimerTask() {
                                public void run() {
                                    System.exit(0);
                                }
                            }, 10000);

                        } else if(playerInv.checkExists(items.get(1))){
                            gui.setConsoleLog("That's not the correct key");
                        } else {
                            gui.setConsoleLog("You don't have a key");
                        }
                    }
                    default -> {
                        gui.setConsoleLog("Your room doesn't have a door or chest");
                    }

                }
                break;
        }

        // ******************************************
    }

    public void save() {


        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./mySave.bin"));
            objectOutputStream.writeObject(save); //write 'this' object -> Game
            objectOutputStream.close();

            System.out.println("SAVED object");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./mySave.bin"));
            save = (Save) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Restoring all needed values:
        player.setCurrentPlayerRoom(save.getPlayer().getCurrentPlayerRoom());
        player.setPlayerInventory(save.getPlayer().getPlayerInventory());

        for(int i = 0; i < basementRooms.size(); i++){
            basementRooms.get(i).setRoomInventory(save.getBasementRooms().get(i).getRoomInventory());
            basementRooms.get(i).setPersonList(save.getBasementRooms().get(i).getPersonList());
        }
        for(int i = 0; i < npcs.size(); i++){
            npcs.get(i).setNpcInventory(save.npcs.get(i).getNpcInventory());
            npcs.get(i).setPosition(save.npcs.get(i).getPosition());
        }
    }
}
