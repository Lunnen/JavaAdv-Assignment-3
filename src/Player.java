import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = -3913565605589328927L;

    protected int currentPlayerRoom;
    protected Inventory playerInventory;

    public Player(int currentPlayerRoom) {
        this.playerInventory = new Inventory(5);
        this.currentPlayerRoom = currentPlayerRoom;
    }

    public Inventory getPlayerInventory(){
        return this.playerInventory;
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public int getCurrentPlayerRoom() {
        return currentPlayerRoom;
    }

    public void setCurrentPlayerRoom(int currentPlayerRoom) {
        this.currentPlayerRoom = currentPlayerRoom;
    }
}

