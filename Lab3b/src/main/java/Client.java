import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private Semaphore chair;
    private Semaphore clientQueue;

    private Barber barber;

    private int id;

    public Client(Barber barber, Semaphore chair, Semaphore clientQueue, int id) {
        this.chair = chair;
        this.clientQueue = clientQueue;
        this.barber = barber;
        this.id = id;
    }

    @Override
    public void run() {
        if (clientQueue.tryAcquire()) {
            System.out.println("Client " + id + " is waiting....");
            while (!chair.tryAcquire()) {
                barber.wakeUp();
            }
            System.out.println("Client " + id + " set in barber chair");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientQueue.release();
        } else {
            System.out.println("No free chair");
        }
    }
}
