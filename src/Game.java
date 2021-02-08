import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    GameObject Shield = new GameObject("Shield", 20, true);
    GameObject Knife = new GameObject("Knife", 21, true);
    GameObject ArmorPlating = new GameObject("Armor plate", 23, true);

    Container box = new Container("Blue box", 55, false, false);

    boolean gameIsOn = true;

    public Game() {
        this.gui = new Gui(this);

        createRooms(); // Create all four rooms.
        createNPC(); // Create the three NPC's according to assignment description.
        addNpcsToRoom(); // Adding the NPC's to different room start locations.
        addObjectsToPlayerInv(); // Add these objects to player inventory at the start


        createItems(box); // Create in game items and keys.

        Room room1 = basementRooms.get(0);
        room1.addObjectToRoom(Shield);
        room1.addObjectToRoom(box);

        npcs.get(1).getNpcInventory().addObject(Shield); //Add shield to Freddy
        System.out.println("Does Room1 have a shield: " + basementRooms.get(0).getRoomInventory().checkExists(Shield));
        System.out.println("At what index does Room1 have a shield: " + basementRooms.get(0).getRoomInventory().findIndexOf(Shield));

        //basementRooms.get(0).getRoomInventory().removeObject(box);

        //player.getPlayerInventory().moveObject(basementRooms.get(0).getRoomInventory(), Knife);
        npcs.get(1).getNpcInventory().moveObject(player.playerInventory, Shield);

        player.getPlayerInventory().moveObject(basementRooms.get(0).getRoomInventory(), Shield);

        // where are npcs???
        /*
        System.out.println("Room 1 npc: " + basementRooms.get(0).getPersons());
        System.out.println("Room 2 npc: " + basementRooms.get(1).getPersons());
        System.out.println("Room 3 npc: " + basementRooms.get(2).getPersons());
        System.out.println("Room 4 npc: " + basementRooms.get(3).getPersons());
        */

        Random random = new Random();
        //basementRooms.get(random.nextInt(4)).addNpc(npcs.get(0));
        int randomNr = random.nextInt(2);
        System.out.println(randomNr);



        //NPC JASON
        //String npcsNewRoom = basementRooms.get(;
        //basementRooms.get(2).changeNPCRoom(basementRooms.get(0), npcs.get(0)); //Flyttar från rum3 > npc0 till rum0
        //basementRooms.get(2).removePerson(npcs.get(0));
        System.out.println(basementRooms.get(2).getPersons());

        //Thread -> Game loop
        Update runThread1 = new Update(gameIsOn, gui, npcs, basementRooms, player, player.getPlayerInventory());
        Thread updateTh = new Thread(runThread1);
        updateTh.start();
        // *****************************
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
        //basementRooms.get(2).addNpc(npcs.get(0));
        //basementRooms.get(0).addNpc(npcs.get(1));
        //basementRooms.get(0).addNpc(npcs.get(2));
    }

    public void createItems(Container box) { // Not needed?
        items.add(new Key("Victory Key", 99, true, box));
        items.add(new Key("Normal Key", 98, true, box));
        //items.add(new Item("Apple", 1));

        //chests.add(new Container("First chest", 98, false, false));
    }
    public void addObjectsToPlayerInv(){
        player.getPlayerInventory().addObject(Shield);
        player.getPlayerInventory().addObject(Knife);
        player.getPlayerInventory().addObject(ArmorPlating);
    }
    public void getFirstItemFromFloor(){

        int currentPlayerRoom = player.getCurrentPlayerRoom();

        /* FROM FLOOR TO PLAYER _ SHOULD WORK THE OTHER WAY AROUND TOO. BUG _ DOESNT WORK IF EMPTY! error

        GameObject firstItem = basementRooms.get(currentPlayerRoom).getRoomInventory().getFirstItem();
        player.getPlayerInventory().addObject(firstItem);

         */

        //Same function with player to floor ->
        //GameObject firstItem = player.getPlayerInventory().getObject(box);

        //basementRooms.get(currentPlayerRoom).addObjectToRoom(firstItem);

    }
    public void dropItem(String tradeObjectName){

        //GameObject

        //player.getPlayerInventory().moveObject(basementRooms.get(player.currentPlayerRoom).roomInventory, GameObject tradeObjectName);

    }


}
