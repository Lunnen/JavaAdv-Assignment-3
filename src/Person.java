import java.io.Serial;

public class Person extends Npc {

    @Serial
    private static final long serialVersionUID = -253910105964884969L;

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
