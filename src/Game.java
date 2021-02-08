import java.util.ArrayList;
import java.util.List;
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

    // All objects in the game


    Container box = new Container("Blue box", 55, false, false);

    boolean gameIsOn = true;

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
        room1.addObjectToRoom(items.get(2));
        room1.addObjectToRoom(box);

        npcs.get(0).getNpcInventory().addObject(items.get(2)); //Add shield to Freddy


        //System.out.println("Does Room1 have a shield: " + basementRooms.get(0).getRoomInventory().checkExists(Shield));
        //System.out.println("At what index does Room1 have a shield: " + basementRooms.get(0).getRoomInventory().findIndexOf(Shield));

        //basementRooms.get(0).getRoomInventory().removeObject(box);

        //player.getPlayerInventory().moveObject(basementRooms.get(0).getRoomInventory(), Knife);
        //npcs.get(1).getNpcInventory().moveObject(player.playerInventory, Shield);

        //player.getPlayerInventory().moveObject(basementRooms.get(0).getRoomInventory(), Shield);

        /* // where are npcs???
        System.out.println("Room 1 npc: " + basementRooms.get(0).getPersons());
        System.out.println("Room 2 npc: " + basementRooms.get(1).getPersons());
        System.out.println("Room 3 npc: " + basementRooms.get(2).getPersons());
        System.out.println("Room 4 npc: " + basementRooms.get(3).getPersons());
        */
/*
        //Thread -> Game loop
        Update runThread1 = new Update(gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());
        Thread updateTh = new Thread(runThread1);
        updateTh.start();
        // *****************************

 */
        startThreadPool(); // Run the threads for Game update to GUI and NPS actions.
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
        npcs.add(new Person("Jason Voorhees", 2));
        npcs.add(new Person("Freddy Krueger", 1));
        npcs.add(new Person("Ture Sventon",0));
    }

    public void addNpcsToRoom() {
        for (Person npc : npcs) { //Find where NPC should be, and add them to that room.
            if(basementRooms.get(npc.getPosition()).findIndexOf(npc) == -1) { //if NPC doesn't exist in room, add.
                basementRooms.get(npc.getPosition()).addNpc(npc);
            }
        }
    }

    public void createItems(Container box) {
        items.add(new Key("Victory Key", 99, true, box));
        items.add(new Key("Normal Key", 98, true, box));
        items.add(new GameObject("Shield", 20, true));
        items.add(new GameObject("Knife", 21, true));
        items.add(new GameObject("Armor plate", 23, true));

    }
    public void addObjectsToPlayerInv(){
        player.getPlayerInventory().addObject(items.get(2)); //Shield
        player.getPlayerInventory().addObject(items.get(3));
        player.getPlayerInventory().addObject(items.get(4));
    }
    public void startThreadPool(){

        Runnable thread1 = new Update(gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());
        Runnable thread2 = new UpdateNpc(items, gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());

        ScheduledExecutorService tPool = Executors.newScheduledThreadPool(2);
        tPool.scheduleAtFixedRate(thread1,0,1, TimeUnit.SECONDS); //input 1, run this runnable, initialDelay, period = wait time in between, TimeUnit.
        tPool.scheduleAtFixedRate(thread2,1, 2, TimeUnit.SECONDS);
    }

}
