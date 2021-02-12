import java.io.Serial;
import java.io.Serializable;

public class GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 2580542493237374538L;

    String objectName;
    int id;
    boolean canPickUp;

    public GameObject(String objectName, int id, boolean canPickUp) {
        this.objectName = objectName;
        this.id = id;
        this.canPickUp = canPickUp;
    }

    public boolean isMovable() {
        return this.canPickUp;
    }

    public String getName() {
        return this.objectName;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return this.objectName;
    }
}
