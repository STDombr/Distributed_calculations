import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    final static int FIRST_MONKS_COUNT = 40;
    final static int SECOND_MONKS_COUNT = 40;
    final static int NUM_OF_THREADS = 2;

    public static void main(String[] args) {
        Monastery firstMonastery = new Monastery("HuanIn");
        Monastery secondMonastery = new Monastery("HuanYan");
        List<Monk> list = new LinkedList<>();
        list.addAll(Utils.generateMonks(firstMonastery, FIRST_MONKS_COUNT));
        list.addAll(Utils.generateMonks(secondMonastery, SECOND_MONKS_COUNT));

        ForkJoinPool pool = new ForkJoinPool(NUM_OF_THREADS);
        Monk winner = pool.invoke(new Battle(list, 0, FIRST_MONKS_COUNT + SECOND_MONKS_COUNT - 1));
        System.out.println("Winner: " + winner + " from " + winner.monastery.getName() + " monastery.");
    }
}
