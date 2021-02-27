import java.util.LinkedList;

public class Utils {
    public static LinkedList<Bee> createBees(int beesCount, Pot pot, Bear bear) {
        LinkedList<Bee> bees = new LinkedList<>();

        for (int i = 0; i < beesCount; i++) {
            bees.add(new Bee(pot, bear, i));
        }

        return bees;
    }
}
