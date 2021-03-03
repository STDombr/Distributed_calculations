import java.util.concurrent.Semaphore;

public class BarberShop implements Runnable {
    private Semaphore chair;
    private Semaphore clientQueue;

    public BarberShop(Semaphore chair, Semaphore clientQueue) {
        this.chair = chair;
        //this.chair.tryAcquire();
        this.clientQueue = clientQueue;
    }

    @Override
    public void run() {
        Barber barber = new Barber(chair);
        new Thread(barber).start();
        int i = 0;

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(new Client(barber, chair, clientQueue, i)).start();
            i++;
        }
    }
}
