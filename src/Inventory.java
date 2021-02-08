/*
Inventory Ska innehålla en array av GameObjects.

Arrayen vara anpassad efter det maxantal objekt som det kan bära (Npc bör bara
kunna bära ett GameObjekt åt gången medan spelaren kan bära lite
fler och rummen kan innehåla rätt många innan de blir “fulla”.

Givetvis säger programmet ifrån om man försöker placera fler saker än
det finns plats för.

Här ska mekaniken för att plocka upp, byta bort
och lägga ned objekt hanteras. ALL HANTERING AV ARRAYERNA


SKA SKE MED STREAMS - det är alltså INTE tillåtet att använda
ArrayLists/LinkedLists eller andra (smartare) lösningar!
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inventory {

    protected GameObject[] objectList;
    protected int invSize;

    public Inventory(int invSize) {
        this.invSize = invSize;
        this.objectList = new GameObject[invSize];
    }

    public void addObject(GameObject inputGameObject) {

        if (findIndexOf(null) == -1) { //Find index of first null
            System.out.println("Inv is full");
            return; //if inventory is full, end method
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

    public GameObject getObject(GameObject getThisObject) { // first object of this kind in objectList

       return Arrays.stream(this.objectList).filter(x -> x.equals(getThisObject))
                .findFirst().orElse(null);
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

    /*
    Return first index where object/null exist, else -1 (inventory is full)
     */
    public int findIndexOf(GameObject objectToMatch) { //Finds first index, so no dupes <--
        return IntStream.range(0, objectList.length)
                .filter(i -> objectList[i] == objectToMatch).findFirst().orElse(-1);
    }
}
