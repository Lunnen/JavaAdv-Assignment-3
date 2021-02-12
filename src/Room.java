import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 181368449519616788L;

    protected String roomName;
    protected String roomDescription;
    protected Inventory roomInventory;
    protected Person[] personList;
    protected Gui gui;

    public Room(String roomName, String roomDescription) {
        this.personList = new Person[5]; //Max 5 in the room
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomInventory = new Inventory(10); //Every room can handle invSize of 10.
    }
    // Add person to first null value, unless room is full.
    public void addNpc(Person inputPerson) {
        if (findIndexOf(null) == -1) {
            gui.setConsoleLog("Room is full of people!");
        } else {
            personList[findIndexOf(null)] = inputPerson;
        }
    }

    public void removeNPC(Person removePerson) {

        int firstIndexFound = findIndexOf(removePerson);
        // Where right index was found, make this value null.
        this.personList = IntStream.range(0, this.personList.length)
                .peek(i -> {
                    if(i == firstIndexFound){
                        this.personList[i] = null;
                    }
                }).mapToObj(i -> this.personList[i]).toArray(Person[]::new);
    }

    public void changeNPCRoom(Room remoteRoom, Person chosenPerson) {
        if(findIndexOf(chosenPerson) != -1){ //As long as chosenPerson does not exist in remoteRoom
            remoteRoom.addNpc(chosenPerson); // Add chosenPerson to remoteRoom

            if(remoteRoom.findIndexOf(chosenPerson) != -1){ //As long as adding Person to remote succeeded
                removeNPC(chosenPerson); // Remove chosenPerson from this room
            }
        }
    }
    public Person getPerson(Person getThisPerson) {
        return Arrays.stream(this.personList)
                .filter(x -> x.equals(getThisPerson)).findFirst().orElse(null);
    }

    // Return each person as a new line
    public String getPersons() {
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

    public void setDescription(String inputDescription) {
        this.roomDescription = inputDescription;
    }

    public Inventory getRoomInventory() {
        return roomInventory;
    }

    public void setRoomInventory(Inventory roomInventory) {
        this.roomInventory = roomInventory;
    }

    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }

    public Person[] getPersonList() {
        return personList;
    }

    @Override
    public String toString() {
        return "You're in the " + roomName + "\n" + roomDescription + "\n\nRoom inventory is:\n" + roomInventory;
    }

    //Return first index where object/null exist, else -1 (inventory is full). Finds first index, so no dupes <--
    public int findIndexOf(Person personToMatch) {
        return IntStream.range(0, personList.length)
                .filter(i -> personList[i] == personToMatch).findFirst().orElse(-1);
    }
}
