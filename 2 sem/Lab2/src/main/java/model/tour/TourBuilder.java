package model.tour;

public class TourBuilder {
    private int id;
    private String tourType;
    private String name;
    private float price;
    private String country;
    private int days;
    private String info;
    private int sale;

    public TourBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TourBuilder setTourType(String tourType) {
        this.tourType = tourType;
        return this;
    }

    public TourBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TourBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public TourBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public TourBuilder setDays(int days) {
        this.days = days;
        return this;
    }

    public TourBuilder setInfo(String info) {
        this.info = info;
        return this;
    }

    public TourBuilder setSale(int sale) {
        this.sale = sale;
        return this;
    }

    public Tour build() {
        return new Tour(id, tourType, name, price, country, days, info, sale);
    }
}
