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
        if (firstIndexNull() == -1) { //  When room is full.
            System.out.println("Room is full!");
        } else {
            personList[firstIndexNull()] = inputPerson; //Add person to first null value
        }
        System.out.println("In Room: " + Arrays.toString(personList));
    }

    public void removeNPC(Person removePerson) {


        int firstIndexFound = findIndexOf(removePerson);

        System.out.println("match here: " + firstIndexFound);
        this.personList = IntStream.range(0, this.personList.length)
                .peek(i -> {
                    if(i == firstIndexFound){
                        this.personList[i] = null;
                    }
                }).mapToObj(i -> this.personList[i]).toArray(Person[]::new);

    }

    public void changeNPCRoom(Room remoteRoom, Person chosenPerson) {
        System.out.println("this index found: " + findIndexOf(chosenPerson));
        if(findIndexOf(chosenPerson) != -1){ //As long as chosenPerson does not exist in remoteRoom
            remoteRoom.addNpc(chosenPerson); // Add chosenPerson to remoteRoom

            if(remoteRoom.findIndexOf(chosenPerson) != -1){ //As long as adding Person to remote succeeded
                removeNPC(chosenPerson); // Remove chosenPerson from this room
            }
        }
    }
    public Person getPerson(Person getThisPerson) {

        return Arrays.stream(this.personList).filter(x -> x.equals(getThisPerson))
                .findFirst().orElse(null);
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

    /*
    Return first index where object/null exist, else -1 (inventory is full)
    */
    public int firstIndexNull() { //Finds first index, so no dupes <--
        return IntStream.range(0, personList.length)
                .filter(i -> personList[i] == null).findFirst().orElse(-1);
    }

    public int findIndexOf(Person personToMatch) { //Finds first index, so no dupes <--
        return IntStream.range(0, personList.length)
                .filter(i -> personList[i] == personToMatch).findFirst().orElse(-1);
    }
}
