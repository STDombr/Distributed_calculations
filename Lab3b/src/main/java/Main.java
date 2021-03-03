import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore chair = new Semaphore(1);
        Semaphore clientQueue = new Semaphore(10);

        new Thread(new BarberShop(chair, clientQueue)).start();
    }
}
