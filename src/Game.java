import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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

public class Game {

    Gui gui;
    Player player = new Player(0); //Player starts at first room (zero).
    List<Room> basementRooms = new ArrayList<>();
    List<Person> npcs = new ArrayList<>();
    List<Container> chests = new ArrayList<>();
    List<GameObject> items = new ArrayList<>();

    GameObject chosenItem; // create a temporary object for comparison. Used by runTrading method.
    Inventory temp = new Inventory(1); // Create a temporary inventory for comparison. Used for NPC trading.
    boolean gameIsOn = true;

    // All containers
    Container box = new Container("Blue box", 55, false, false);

    public Game() {
        this.gui = new Gui(this);

        createRooms(); // Create all four rooms.
        createNPC(); // Create the three NPC's according to assignment description.
        createItems(box); // Create in game items and keys.
        addNpcsToRoom(); // Adding the NPC's to different room start locations.
        addObjectsToPlayerInv(); // Add these objects to player inventory at the start


        Room room1 = basementRooms.get(0);
        room1.addObjectToRoom(items.get(2));
        room1.addObjectToRoom(items.get(2));
        room1.addObjectToRoom(items.get(2)); //add shields
        room1.addObjectToRoom(box);

        npcs.get(0).getNpcInventory().addObject(items.get(2)); //Add shield to Jason
        npcs.get(1).getNpcInventory().addObject(items.get(2)); //Add shield to Freddy
        npcs.get(2).getNpcInventory().addObject(items.get(2)); //Add shield to Ture


        startThreadPool(); // Run the threads for Game update to GUI and NPC actions.
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
        basementRooms.add(new Room("Fourth room", "Actually kind of clean. I wonder why ...\nThis seems to be the last room in the basement."));
    }

    public void createNPC() {
        npcs.add(new Person("Jason Voorhees", 1)); //2
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

    public void addObjectsToPlayerInv() {
        player.getPlayerInventory().addObject(items.get(2)); //Shield
        player.getPlayerInventory().addObject(items.get(3));
        player.getPlayerInventory().addObject(items.get(4));
    }

    public void startThreadPool() {

        Runnable thread1 = new Update(gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());
        Runnable thread2 = new UpdateNpc(items, gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());

        ScheduledExecutorService tPool = Executors.newScheduledThreadPool(5);
        tPool.scheduleAtFixedRate(thread1, 0, 700, TimeUnit.MILLISECONDS); //input 1, run this runnable, initialDelay, period = wait time in between, TimeUnit.
        tPool.scheduleAtFixedRate(thread2, 1, 2, TimeUnit.SECONDS);
    }

    public void runTrading(String inputButton, String inputObjectName) {

        switch (inputObjectName.toLowerCase()) { //Check if inputObjectName is a valid object name
            // ******************************************
            case "victory key":
                System.out.println("victory key");
                chosenItem = items.get(0);
                break;
            case "normal key":
                System.out.println("normal key");
                chosenItem = items.get(1);
                break;
            case "shield":
                System.out.println("Wants to trade a shield!!!");
                chosenItem = items.get(2);
                break;
            case "knife":
                System.out.println("A knife");
                chosenItem = items.get(3);
                break;
            case "bread":
                System.out.println("Bread");
                chosenItem = items.get(4);
                break;
            default:
                System.out.println("WRONG/NO INPUT");
                break;
            // ******************************************
        }


        switch (inputButton) {
            // ******************************************
            case "Trade with NPC":
                System.out.println("Gonna trade some shit -> " + inputObjectName);

                System.out.println("this: " + npcs.get(0).getNpcInventory().findIndexOf(null));
                System.out.println("this: " + npcs.get(0).getNpcInventory());

                for (Person npc : npcs) { // Run through all npcs
                    if (npc.getPosition() == player.currentPlayerRoom) { //If NPC is in player's room
                        if (npc.getNpcInventory().getFirstObject() != null) { //If NPC has objects in his inventory

                            npc.getNpcInventory().moveObject(temp, npc.getNpcInventory().getFirstObject()); // trade npc item to temp.
                            player.getPlayerInventory().moveObject(npc.getNpcInventory(), chosenItem); // move chosen item from player to npc inv.
                            temp.moveObject(player.getPlayerInventory(), temp.getFirstObject()); // move object from temp to player.inv.
                            break; //End loop - just trade with first NPC.
                        }
                        else {
                            System.out.println("NPC has no objects");
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
        }

    }

}
