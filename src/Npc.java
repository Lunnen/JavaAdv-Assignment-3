import java.io.Serial;
import java.io.Serializable;

/*
• Npc Ska vara antingen ett Interface eller en Abstrakt klass och ge
ramarna för våra Npc:er. I grundstrukturen är det en abstract men
det är okej att byta. Tänk bara på att byta extends mot implements i
subklasserna.
 */
public abstract class Npc implements Serializable {
    @Serial
    private static final long serialVersionUID = 5343006344882116183L;

    String name;
    Inventory npcInventory;

    public Npc(String inputName){
        this.name = inputName;
        this.npcInventory = new Inventory(1);
    }
    public Inventory getNpcInventory(){
        return this.npcInventory;
    }

    public void setNpcInventory(Inventory npcInventory) {
        this.npcInventory = npcInventory;
    }

    public String toString() { return this.name + " with inventory: " + npcInventory; }
}