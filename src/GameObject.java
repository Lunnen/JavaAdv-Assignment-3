/*
GameObject
Ska också vara abstract eller interface
hantera alla "ickelevandeöbjekt i spelet (möbler, nycklar etc).
GameObject ska innehålla en boolean som avgör om objektet
går att ta med sig eller är fastmonterat"i rummet.
 */

public class GameObject {
    protected String objectName;
    protected int id;
    protected boolean canPickUp;

    public GameObject(String objectName, int id, boolean canPickUp) {
        this.objectName = objectName;
        this.id = id;
        this.canPickUp = canPickUp;
    }
    public boolean isMovable(){
        return this.canPickUp;
    }
    public String getName(){
        return this.objectName;
    }

    public String toString() {
        return this.objectName;
    }
}
