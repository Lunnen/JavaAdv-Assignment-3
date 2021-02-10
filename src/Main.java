import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/*
Main ska endast starta spelet
genom att skapa en instans av Game!
 */
public class Main {
    public static void main(String[] args) {
        //new Game();
        // NO CODE ALLOWED

        Game game = null;

        game = load();
    }

    public static Game load(){ //NOT WORKING !! DISABLED IN GUI

        Game thisGame = new Game();
        try {
            FileInputStream fis = new FileInputStream("./mySave.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            thisGame = (Game) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

        }
        return thisGame;
    }
}
