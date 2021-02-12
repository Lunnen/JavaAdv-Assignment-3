import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Save implements Serializable {

    @Serial
    private static final long serialVersionUID = -8665541935073115276L;

    Player player = new Player(0); //Player starts at first room (zero).
    List<Room> basementRooms = new ArrayList<>();
    List<Person> npcs = new ArrayList<>();

    public Save(Player player, List<Room> basementRooms, List<Person> npcs) {
        this.player = player;
        this.basementRooms = basementRooms;
        this.npcs = npcs;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Room> getBasementRooms() {
        return basementRooms;
    }

    public void setBasementRooms(List<Room> basementRooms) {
        this.basementRooms = basementRooms;
    }

    public List<Person> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<Person> npcs) {
        this.npcs = npcs;
    }
}
