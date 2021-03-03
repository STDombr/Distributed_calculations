import java.util.concurrent.Semaphore;

public class Barber implements Runnable {
    private boolean isSleeping;
    private Semaphore chair;

    public Barber(Semaphore chair) {
        this.chair = chair;
        isSleeping = false;
    }

    public void wakeUp() {
        if (isSleeping) {
            System.out.println("Barber has woken up");
            isSleeping = false;
            chair.release();
        }
    }


    @Override
    public void run() {
        while (true) {
            if (isSleeping) {
                System.out.println("The barber is sleeping...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (chair.tryAcquire()) {
                    isSleeping = true;
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The barber has cut the hair");
                    chair.release();
                }
            }
        }
    }
}
