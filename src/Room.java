import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Room Ett Room ska ha ett unikt namn,
ett Inventory och sen showMetod() som beskriver det för spelaren.
 */
public class Room {
    protected String roomName;
    protected String roomDescription;
    protected Inventory roomInventory;
    protected Person[] personList;

    public Room(String roomName, String roomDescription) {
        this.personList = new Person[5]; //Max 5 in the room
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomInventory = new Inventory(10); //Every room can handle invSize of 10.
    }

    public void addNpc(Person inputPerson) {
        if (getFirstEmptyIndex() != -1) { // As long as room's not full.
            personList[getFirstEmptyIndex()] = inputPerson;
        } else {
            System.out.println("Room is full!");
        }
    }

    public String getPersons() { // Return each person as a new line
        return Arrays.stream(this.personList)
                .filter(Objects::nonNull).map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public void addObjectToRoom(GameObject inputObject) {
        roomInventory.addObject(inputObject);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return roomDescription;
    }

    public Inventory getRoomInventory() {
        return roomInventory;
    }

    public void setDescription(String inputDescription) {
        this.roomDescription = inputDescription;
    }

    @Override
    public String toString() { // my Show Method.
        return "You're in the " + roomName + "\n" + roomDescription + "\n\nRoom inventory is:\n" + roomInventory;
    }

    private int getFirstEmptyIndex() {
        return IntStream.range(0, personList.length)
                .filter(i -> personList[i] == null).findFirst().orElse(-1);
    }
}
