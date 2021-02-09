/*
• Npc Ska vara antingen ett Interface eller en Abstrakt klass och ge
ramarna för våra Npc:er. I grundstrukturen är det en abstract men
det är okej att byta. Tänk bara på att byta extends mot implements i
subklasserna.
 */
public abstract class Npc {
    String name;
    Inventory npcInventory;

    public Npc(String inputName){
        this.name = inputName;
        this.npcInventory = new Inventory(1);
    }
    public Inventory getNpcInventory(){
        return this.npcInventory;
    }
    public String toString() { return this.name + " with inventory: " + npcInventory; }
}