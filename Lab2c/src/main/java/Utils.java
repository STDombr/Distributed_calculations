import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Utils {
    final static int MAX_ENERGY = 500;
    final static int MIN_ENERGY = 50;

    public static Monk getWinner(Monk firstMonk, Monk secondMonk) {
        return firstMonk.getEnergy() > secondMonk.getEnergy() ? firstMonk : secondMonk;
    }

    public static List<Monk> generateMonks(Monastery monastery, int count) {
        Random random = new Random();
        List<Monk> list = new LinkedList<>();

        for (int i = 0; i < count; i++) {
            list.add(new Monk(monastery.getName() + "Monk" + i,
                    random.nextInt(MAX_ENERGY - MIN_ENERGY) + MIN_ENERGY,
                    monastery));
        }

        return list;
    }
}
