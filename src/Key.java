/*
En subklass till GameObject vars objekt används för att låsa
upp Containers. Välj om keyobjektet ska hålla koll på vilken instans
av container den passar till eller tvärtom!

 */
public class Key extends GameObject{
    Container container;

    public Key(String objectName, int id, boolean canPickUp, Container inputContainer) {
        super(objectName, id, canPickUp);
        this.container = inputContainer;
    }
    public boolean keyFits(Container compareContainer){
        return compareContainer.equals(this.container);
        //if( this.container.getName().equals(compareContainer).getName()){ true else false.
    }

    public String toString() {
        return objectName;
    }
}
