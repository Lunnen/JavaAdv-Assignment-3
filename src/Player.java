import java.io.Serializable;

/*
Player Håller ordning på var spelaren befinner sig (alltså vilket rum
som ska beskrivas), samt spelarens Inventory.

 */
public class Player implements Serializable {

    protected int currentPlayerRoom;
    protected Inventory playerInventory;

    public Player(int currentPlayerRoom) {
        this.playerInventory = new Inventory(5);
        this.currentPlayerRoom = currentPlayerRoom;
    }

    public Inventory getPlayerInventory(){
        return this.playerInventory;
    }

    public int getCurrentPlayerRoom() {
        return currentPlayerRoom;
    }

    public void setCurrentPlayerRoom(int currentPlayerRoom) {
        this.currentPlayerRoom = currentPlayerRoom;
    }
}

