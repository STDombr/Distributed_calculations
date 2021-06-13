package model.country;

public class CountryBuilder {
    private int id;
    private String name;

    public CountryBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CountryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Country build() {
        return new Country(id, name);
    }
}
