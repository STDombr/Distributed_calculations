public class Pot {
    private int maxSize;
    private int honeyValue;

    public Pot(int maxSize) {
        this.maxSize = maxSize;
        this.honeyValue = 0;
    }

    synchronized void fill(int id) {
        if (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        honeyValue++;
        System.out.println("Bee " + id + " fill honey. Pot size is " + honeyValue);
    }

    synchronized void eat() {
        honeyValue = 0;
        System.out.println("Pot is empty.");
        notifyAll();
    }

    synchronized boolean isFull() {
        return maxSize == honeyValue;
    }
}
