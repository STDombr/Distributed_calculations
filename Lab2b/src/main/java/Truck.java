public class Truck {
    private Storage loadingStorage;

    public Truck() {
        loadingStorage = new Storage();
    }

    public void addWeapon(Weapon w) {
        loadingStorage.addWeapon(w);
    }

    public Weapon getWeapon() {
        if (loadingStorage.isEmpty()) {
            return null;
        }

        Weapon w = loadingStorage.getWeapon();

        return w;
    }

    public boolean isEmpty() {
        return loadingStorage.isEmpty();
    }
}
