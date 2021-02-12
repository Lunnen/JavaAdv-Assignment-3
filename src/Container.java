import java.io.Serial;

public class Container extends GameObject {

    @Serial
    private static final long serialVersionUID = 7045595233639048847L;

    Inventory containerInventory;
    boolean locked;

    public Container(String objectName, int id, boolean movable, boolean locked) {
        super(objectName, id, movable);
        this.containerInventory = new Inventory(3);
        this.locked = locked;
    }

    public Inventory getContainerInventory() {
        return this.containerInventory;
    }

    public boolean isLocked() { //Returns if container is locked or not
        return locked;
    }

    public String toString() {
        return objectName;
    }
}
