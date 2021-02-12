import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    Gui gui;
    Player player = new Player(0); //Player starts at first room (zero).
    ArrayList<Room> basementRooms = new ArrayList<>();
    ArrayList<Person> npcs = new ArrayList<>();
    ArrayList<GameObject> items = new ArrayList<>();

    protected Save save = new Save(player, basementRooms, npcs, items);
    protected Container box = new Container("The Chest", 55, false, false);

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
        npcs.add(new Person("Jason Voorhees", 1));
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
        ScheduledExecutorService tPool = Executors.newScheduledThreadPool(6);

        Runnable thread1 = new Update(gui, basementRooms, player);
        Runnable thread2 = new UpdateNpc(2, items, gui, npcs, basementRooms, player);
        Runnable thread3 = new UpdateNpc(1, items, gui, npcs, basementRooms, player);
        Runnable thread4 = new UpdateNpc(0, items, gui, npcs, basementRooms, player);

        tPool.scheduleAtFixedRate(thread1, 0, 700, TimeUnit.MILLISECONDS);
        tPool.scheduleAtFixedRate(thread2, 1, 3, TimeUnit.SECONDS); //Ture -> Quicker
        tPool.scheduleAtFixedRate(thread3, 5, 5, TimeUnit.SECONDS); //Freddy -> a bit slower
        tPool.scheduleAtFixedRate(thread4, 10, 4, TimeUnit.SECONDS); //Jason -> slow starter.
    }

    public void itemActions(String inputButton, String inputObjectName) {

        GameObject chosenItem = null; // create a temporary object for comparison.
        Inventory temp = new Inventory(1); // Create a temporary inventory for comparison.

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

                /*
                Run through all npcs, and If NPC is in player's room.
                If NPC has objects in his inv. and player has chosen his item & has it in inv.
                trade npc item to temp and move chosen item from player to npc inv. Then move object from temp to player.inv. */
                for (Person npc : npcs) {
                    if (npc.getPosition() == player.currentPlayerRoom) { //
                        if (npc.getNpcInventory().getFirstObject() != null && chosenItem != null && player.getPlayerInventory().checkExists(chosenItem)) {

                            npc.getNpcInventory().moveObject(temp, npc.getNpcInventory().getFirstObject());
                            player.getPlayerInventory().moveObject(npc.getNpcInventory(), chosenItem);
                            temp.moveObject(player.getPlayerInventory(), temp.getFirstObject());
                            break; //End loop - just trade with first NPC.
                        } else if (!player.getPlayerInventory().checkExists(chosenItem)) {

                            gui.setConsoleLog("You don't own that object yet!");
                        } else {
                            gui.setConsoleLog("Pick item to trade and/or NPC has no objects");
                        }
                    }
                }
                break;
            // ******************************************
            case "Grab from floor":
                System.out.println("Gonna grab me some -> " + inputObjectName + chosenItem);
                System.out.println("player: " + player.getPlayerInventory());
                System.out.println("roomInv " + basementRooms.get(player.currentPlayerRoom).getRoomInventory());

                basementRooms.get(player.currentPlayerRoom).getRoomInventory().moveObject(player.getPlayerInventory(), chosenItem);
                break;
            // ******************************************
            case "Drop to floor":
                System.out.println("Dropping stuff... -> " + inputObjectName);
                System.out.println("player: " + player.getPlayerInventory());
                System.out.println("roomInv " + basementRooms.get(player.currentPlayerRoom).getRoomInventory());

                player.getPlayerInventory().moveObject(basementRooms.get(player.currentPlayerRoom).getRoomInventory(), chosenItem);

                break;
            // ******************************************
            case "Open door/chest":
                System.out.println("open with -> " + inputObjectName);

                switch (player.getCurrentPlayerRoom()) {
                    case 0 -> {

                        if (player.getPlayerInventory().checkExists(items.get(1))) { //If it's a normal key
                            Key key = (Key) chosenItem;
                            if (key.keyFits(box)) { // If it's the correct key, open chest.
                                gui.setConsoleLog("Chest has been opened. Loot transferred to player inventory.");

                                box.getContainerInventory().moveObject(player.getPlayerInventory(), items.get(0)); //Move Victory key from chest to player.inv.
                                player.getPlayerInventory().removeObject(items.get(1)); //Remove key from player.inv.
                                basementRooms.get(player.currentPlayerRoom).getRoomInventory().removeObject(box); //Remove box from room.

                            }
                        } else if (player.getPlayerInventory().checkExists(items.get(0))) { //If its victory key
                            gui.setConsoleLog("That's not the correct key");
                        } else {
                            gui.setConsoleLog("You don't have a key");
                        }
                    }
                    case 3 -> {

                        if (player.getPlayerInventory().checkExists(items.get(0))) {
                            gui.setConsoleLog("YOU'VE BEEN SET FREE! VICTORY! GAME END!");

                            new Timer().schedule(new TimerTask() {
                                public void run() {
                                    System.exit(0);
                                }
                            }, 10000);

                        } else if (player.getPlayerInventory().checkExists(items.get(1))) {
                            gui.setConsoleLog("That's not the correct key");
                        } else {
                            gui.setConsoleLog("You don't have a key");
                        }
                    }
                    default -> gui.setConsoleLog("Your room doesn't have a door or chest");
                }
                break;
        }
    }

    public void save() {

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("./mySave.bin"));
            objectOutputStream.writeObject(save);
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
        //Restoring all needed values from the Save class
        player.setCurrentPlayerRoom(save.getPlayer().getCurrentPlayerRoom());
        player.setPlayerInventory(save.getPlayer().getPlayerInventory());
        System.out.println("this is set: " + player.getPlayerInventory());

        for (int i = 0; i < basementRooms.size(); i++) {
            basementRooms.get(i).setRoomInventory(save.getBasementRooms().get(i).getRoomInventory());
            basementRooms.get(i).setPersonList(save.getBasementRooms().get(i).getPersonList());
        }
        for (int i = 0; i < npcs.size(); i++) {
            npcs.get(i).setNpcInventory(save.npcs.get(i).getNpcInventory());
            npcs.get(i).setPosition(save.npcs.get(i).getPosition());
        }
        this.items = new ArrayList<>();
        items = save.getItems();

    }
}
