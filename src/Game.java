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
    List<Container> chests = new ArrayList<>();
    List<GameObject> items = new ArrayList<>();

    GameObject chosenItem; // create a temporary object for comparison. Used by runTrading method.
    Inventory temp = new Inventory(1); // Create a temporary inventory for comparison. Used for NPC trading.

    boolean gameIsOn = true; // <--- remove?

    private final String fileName = "./mySave.bin";

    // All containers
    Container box = new Container("Blue box", 55, false, false);

    public Game() {
        this.gui = new Gui(this);

        createRooms(); // Create all four rooms.
        createNPC(); // Create the three NPC's according to assignment description.
        createItems(box); // Create in game items and keys.
        addNpcsToRoom(); // Adding the NPC's to different room start locations.

        /*
        The game only has a few items.
        You need to trade with NPC
        */
        npcs.get(0).getNpcInventory().addObject(items.get(0)); //Add victory key to Jason
        npcs.get(1).getNpcInventory().addObject(items.get(3)); //Add knife to Freddy
        basementRooms.get(3).addObjectToRoom(items.get(4)); //add Bread to last room

        gui.setConsoleLog("You've woken up inside a basement with several weird men. Try finding a way out of here!");
        /*
         Run the threads for Game update to GUI and NPC actions.
         */
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
        items.add(new Key("Normal Key", 98, true, box));
        items.add(new GameObject("Shield", 20, true));
        items.add(new GameObject("Knife", 21, true));
        items.add(new GameObject("Bread", 23, true));
    }

    public void startThreadPool() {

        Runnable thread1 = new Update(gui, basementRooms, player, player.getPlayerInventory());
        Runnable thread2 = new UpdateNpc(2, items, gui, npcs, basementRooms, player, player.getPlayerInventory());
        Runnable thread3 = new UpdateNpc(1, items, gui, npcs, basementRooms, player, player.getPlayerInventory());

        ScheduledExecutorService tPool = Executors.newScheduledThreadPool(5);
        tPool.scheduleAtFixedRate(thread1, 0, 700, TimeUnit.MILLISECONDS); //input 1, run this runnable, initialDelay, period = wait time in between, TimeUnit.
        tPool.scheduleAtFixedRate(thread2, 1, 3, TimeUnit.SECONDS); //Ture -> Quicker
        tPool.scheduleAtFixedRate(thread3, 5, 5, TimeUnit.SECONDS); //Freddy -> a bit slower
    }

    public void runTrading(String inputButton, String inputObjectName) {

        //Check if inputObjectName is a valid object name
        switch (inputObjectName.toLowerCase()) {
            case "victory key" -> chosenItem = items.get(0);
            case "normal key" -> chosenItem = items.get(1);
            case "shield" -> chosenItem = items.get(2);
            case "knife" -> chosenItem = items.get(3);
            case "bread" -> chosenItem = items.get(4);
            default -> gui.setConsoleLog("WRONG/NO INPUT");
        }

        switch (inputButton) {
            // ******************************************
            case "Trade with NPC": // ONLY POSSIBLE TO TRADE WITH FIRST NPC IN A ROOM

                for (Person npc : npcs) { // Run through all npcs
                    if (npc.getPosition() == player.currentPlayerRoom) { //If NPC is in player's room
                        if (npc.getNpcInventory().getFirstObject() != null && chosenItem != null) { //If NPC has objects in his inv. and player has chosen his item.

                            npc.getNpcInventory().moveObject(temp, npc.getNpcInventory().getFirstObject()); // trade npc item to temp.
                            player.getPlayerInventory().moveObject(npc.getNpcInventory(), chosenItem); // move chosen item from player to npc inv.
                            temp.moveObject(player.getPlayerInventory(), temp.getFirstObject()); // move object from temp to player.inv.
                            break; //End loop - just trade with first NPC.
                        }
                        else {
                            gui.setConsoleLog("Pick item to trade and/or NPC has no objects"); }
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
            case "Open door":
                System.out.println("open with -> " + inputObjectName);
                if(player.getCurrentPlayerRoom() == 3) {
                    if (inputObjectName.equalsIgnoreCase("Victory key")) {
                        gui.setConsoleLog("YOU'VE BEEN SET FREE! VICTORY! GAME END!");

                        new Timer().schedule(new TimerTask() {  //Show end message for 10 seconds, then exit app
                            public void run () { System.exit(0); }
                        }, 10000);

                    } else {
                        gui.setConsoleLog("That's not the correct key");
                    }
                }
                else {
                    gui.setConsoleLog("Your room doesn't have a door");
                }

                break;
            // ******************************************
        }

    }
    public void save(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this); //write 'this' object -> Game
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Game load(){ //NOT WORKING !! DISABLED IN GUI

        Game thisGame = this;
        try {
            FileInputStream fis = new FileInputStream(this.fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            thisGame = (Game) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

        }
        return thisGame;
    }
}
