public class Bee implements Runnable {
    private Pot pot;
    private Bear bear;
    private int id;

    public Bee(Pot pot, Bear bear, int id) {
        this.pot = pot;
        this.bear = bear;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pot.fill(id);

            if (pot.isFull()) {
                System.out.println("Pot is full!");
                bear.wakeUp();
            }
        }
    }
}
