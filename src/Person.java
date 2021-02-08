/*
En person är Npc - dessa ska lagras i lista av något slag och
ha ett eget liv så till vida att de ska hanteras concurrent. Npc:ernas
beteende bestäms av slumptal. Det rör sig mellan rummen, plockar upp,
lägger ned saker. Det ska finnas en showPerson, som visar personens
namn och vad denne bär på. Vill man ha något som personen bär på så
kan man antingen följa efter och vänta på att objekten läggs ned eller
be om att byta mot ett objekt i det egna inventory.
 */

public class Person extends Npc {
    protected int position;

    public Person(String personName, int roomStart){
        super(personName);
        this.position = roomStart;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
