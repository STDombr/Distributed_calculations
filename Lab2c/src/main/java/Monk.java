public class Monk {
    private String name;
    private int energy;
    Monastery monastery;

    public Monk(String name, int energy, Monastery monastery) {
        this.name = name;
        this.energy = energy;
        this.monastery = monastery;
    }

    public Monastery getMonastery() {
        return monastery;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return name + ' ' + energy;
    }
}
