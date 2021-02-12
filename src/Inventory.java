
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inventory implements Serializable {

    @Serial
    private static final long serialVersionUID = -4850508543017840034L;

    GameObject[] objectList;
    int invSize;
    Gui gui;

    public Inventory(int invSize) {
        this.invSize = invSize;
        this.objectList = new GameObject[invSize];
    }

    public void addObject(GameObject inputGameObject) {

        //Find index of first null, and if inventory is full, end method
        if (findIndexOf(null) == -1) {
            gui.setConsoleLog("Inventory is full");
            return;
        }
        this.objectList[findIndexOf(null)] = inputGameObject; //Put inputGameObject into first null value
    }

    public boolean checkExists(GameObject inputObject) {
        return Arrays.stream(this.objectList).filter(Objects::nonNull).anyMatch(x -> x.equals(inputObject));
    }

    public void moveObject(Inventory remoteInventory, GameObject chosenObject) {

        if(findIndexOf(chosenObject) != -1){ //As long as chosenObject exists in inventory
            remoteInventory.addObject(chosenObject); // Add chosenObject to remoteInventory

            if(remoteInventory.findIndexOf(chosenObject) != -1){ //As long as adding object to remote succeeded
                removeObject(chosenObject); // Remove chosenObject from local inventory
            }
        }
    }
    /* Get first object or return null -> Used for NPC trading. */
    public GameObject getFirstObject() {

        return Arrays.stream(this.objectList)
                .filter(Objects::nonNull).findFirst().orElse(null);
    }

    public void removeObject(GameObject removeObject) {

        int firstIndexFound = findIndexOf(removeObject);

        this.objectList = IntStream.range(0, this.objectList.length)
                .peek(i -> {
                    if(i == firstIndexFound){
                        this.objectList[i] = null;
                    }
                }).mapToObj(i -> this.objectList[i]).toArray(GameObject[]::new);
    }

    public String toString() { //Only show objects that exist, not null values.

        String result = Arrays.stream(this.objectList)
                .filter(Objects::nonNull).map(Objects::toString).collect(Collectors.joining(", "));

        return result.length() > 0 ? result : "Empty!"; //If there's anything in inventory, show it - if not show "Empty".
    }

    /* Return first index where object/null exist, else -1 (inventory is full). Finds first index, so no dupes <-- */
    public int findIndexOf(GameObject objectToMatch) {
        return IntStream.range(0, objectList.length)
                .filter(i -> objectList[i] == objectToMatch).findFirst().orElse(-1);
    }
}
