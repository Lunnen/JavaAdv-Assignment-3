import java.io.Serializable;

/*
En subklass till GameObject vars objekt används för att låsa
upp Containers. Välj om keyobjektet ska hålla koll på vilken instans
av container den passar till eller tvärtom!

 */
public class Key extends GameObject {
    Container inputContainer;

    public Key(String objectName, int id, boolean canPickUp, Container inputContainer) {
        super(objectName, id, canPickUp);
        this.inputContainer = inputContainer;
    }
    public boolean keyFits(Container compareContainer){
        return this.inputContainer.getId() == id;
    }

    public String toString() {
        return objectName;
    }
}
