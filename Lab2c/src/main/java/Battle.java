import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Battle extends RecursiveTask<Monk> {
    List<Monk> list;
    int left, right;

    public Battle(List<Monk> list, int left, int right) {
        this.list = list;
        this.left = left;
        this.right = right;
    }

    @Override
    protected Monk compute() {
        if (left > right) {
            return null;
        } else if (left - right == 0) {
            return list.get(left);
        } else {
            Battle leftBattle = new Battle(list, left, (left + right) / 2);
            Battle rightBattle = new Battle(list, (left + right) / 2 + 1, right);

            leftBattle.fork();
            rightBattle.fork();

            Monk leftWinner = leftBattle.join();
            Monk rightWinner = rightBattle.join();

            System.out.println("Battle between: " + leftWinner
                    + " and " + rightWinner);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return Utils.getWinner(leftWinner, rightWinner);
        }
    }
}
