import java.util.LinkedList;

public class Main {
    final static int POT_SIZE = 20;
    final static int BEES_COUNT = 4;

    public static void main(String[] args) {
        Pot pot = new Pot(POT_SIZE);
        Bear bear = new Bear(pot);
        LinkedList<Bee> bees = Utils.createBees(BEES_COUNT, pot, bear);

        new Thread(bear).start();

        for (Bee bee : bees) {
            new Thread(bee).start();
        }
    }
}
