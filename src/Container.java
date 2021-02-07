/*
En subklass till GameObject som har ett Inventory.
Kan vara låst eller öppet.
 */

public class Container extends GameObject{
    Inventory containerInventory;
    boolean locked;

    public Container(String objectName, int id, boolean movable, boolean locked) {
        super(objectName, id, movable);
        this.containerInventory = new Inventory(3);
        this.locked = locked;
    }
    public Inventory getContainerInventory(){
        return this.containerInventory;
    }

    public boolean isLocked(){ //Returns if container is locked or not
        return locked;
    }
}
