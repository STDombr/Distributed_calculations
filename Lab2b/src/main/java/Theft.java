public class Theft {
    final static int STOLEN_WEAPONS_MAX_SIZE = 4;
    private Storage mainStorage;
    private Storage stolenWeapons;
    private Truck truck;
    private final Object monitor;
    private int weaponsCost;

    public Theft(int weaponCount) {
        mainStorage = Utils.generateStorage(weaponCount);
        stolenWeapons = new Storage();
        truck = new Truck();
        monitor = new Object();
        weaponsCost = 0;
    }

    public void theftProcess() throws InterruptedException {
        Thread threadIvanov = new Thread(() -> {
            while (!mainStorage.isEmpty()) {
                Weapon w = mainStorage.getWeapon();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (monitor) {
                    while (stolenWeapons.getSize() == STOLEN_WEAPONS_MAX_SIZE) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    stolenWeapons.addWeapon(w);
                    monitor.notifyAll();
                }
                System.out.println("Ivanov took from the storage " + w.getName());
            }
        });
        threadIvanov.start();


        Thread threadPetrov = new Thread(() -> {
            while ((!mainStorage.isEmpty()) || (!stolenWeapons.isEmpty())) {
                Weapon w;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (monitor) {
                    while (stolenWeapons.isEmpty()) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    w = stolenWeapons.getWeapon();
                    truck.addWeapon(w);
                    System.out.println("Petrov put in the track " + w.getName());
                    monitor.notifyAll();
                }
            }
        });
        threadPetrov.start();


        Thread threadNecheporchuk = new Thread(() -> {
            while ((!mainStorage.isEmpty()) || (!stolenWeapons.isEmpty()) || (!truck.isEmpty())) {
                Weapon w;
                synchronized (monitor) {
                    while (truck.isEmpty()) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                w = truck.getWeapon();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                weaponsCost += w.getPrice();
                System.out.println("Necheporchuk calculate the cost of " + w.getName() + " " + w.getPrice());
            }

        });
        threadNecheporchuk.start();

        threadIvanov.join();
        threadPetrov.join();
        threadNecheporchuk.join();

        System.out.println("All weapon cost is " + weaponsCost);
    }
}
