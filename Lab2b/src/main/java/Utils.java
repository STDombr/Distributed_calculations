import java.util.Random;

public class Utils {
    final static int WEAPON_MAX_PRICE = 1000;
    final static int WEAPON_MIN_PRICE = 500;

    public static Storage generateStorage(int weaponCount) {
        Storage storage = new Storage();
        Weapon weapon;
        Random random = new Random();

        for (int i = 0; i < weaponCount; i++) {
            weapon = new Weapon("Weapon" + i, random.nextInt(WEAPON_MAX_PRICE - WEAPON_MIN_PRICE) + WEAPON_MIN_PRICE);
            storage.addWeapon(weapon);
        }

        return storage;
    }
}
