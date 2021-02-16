import java.util.LinkedList;

public class Storage {

    private LinkedList<Weapon> list;

    public Storage() {
        this.list = new LinkedList<>();
    }

    public Storage(LinkedList<Weapon> list) {
        this.list = list;
    }

    public void addWeapon(Weapon w) {
        list.add(w);
    }

    public Weapon getWeapon() {
        if (!list.isEmpty()) {
            return list.remove();
        }

        return null;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int getSize() {
        return list.size();
    }
}
