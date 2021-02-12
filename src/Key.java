import java.io.Serial;

public class Key extends GameObject {

    @Serial
    private static final long serialVersionUID = -2380175100857022500L;

    Container inputContainer;

    public Key(String objectName, int id, boolean canPickUp, Container inputContainer) {
        super(objectName, id, canPickUp);
        this.inputContainer = inputContainer;
    }
    public boolean keyFits(Container compareContainer){
        return compareContainer.getId() == this.id;
    }

    public String toString() {
        return objectName;
    }
}
