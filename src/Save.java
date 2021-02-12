import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {

    @Serial
    private static final long serialVersionUID = -8665541935073115276L;

    Player player;
    ArrayList<Room> basementRooms;
    ArrayList<Person> npcs;
    ArrayList<GameObject> items;
    Container box;

    public Save(Player player, ArrayList<Room> basementRooms, ArrayList<Person> npcs, ArrayList<GameObject> items, Container box) {
        this.player = player;
        this.basementRooms = basementRooms;
        this.npcs = npcs;
        this.items = items;
        this.box = box;
    }

    public Container getBox() { return box;}

    public void setBox(Container box) { this.box = box; }

    public ArrayList<GameObject> getItems() { return items; }

    public void setItems(ArrayList<GameObject> items) { this.items = items;}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Room> getBasementRooms() {
        return basementRooms;
    }

    public void setBasementRooms(ArrayList<Room> basementRooms) {
        this.basementRooms = basementRooms;
    }

    public ArrayList<Person> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<Person> npcs) {
        this.npcs = npcs;
    }
}
