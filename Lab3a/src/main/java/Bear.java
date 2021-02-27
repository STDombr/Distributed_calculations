public class Bear implements Runnable {
    private Pot pot;
    private volatile boolean isAwake;

    public Bear(Pot pot) {
        this.pot = pot;
        this.isAwake = false;
    }

    synchronized void wakeUp() {
        isAwake = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (!isAwake) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Bear ate all honey!");
            pot.eat();

            isAwake = false;
        }
    }
}
