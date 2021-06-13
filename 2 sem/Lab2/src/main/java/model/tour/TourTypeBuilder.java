package model.tour;


public class TourTypeBuilder {
    private int id;
    private String name;

    public TourTypeBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TourTypeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TourType build() {
        return new TourType(id, name);
    }
}
