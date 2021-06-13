package model.tour;

public class Tour {
    private int id;
    private String tourType;
    private String name;
    private float price;
    private String country;
    private int days;
    private String info;
    private int sale;

    public Tour() {
        this.id = -1;
        this.tourType = "";
        this.name = "";
        this.price = 0;
        this.country = "";
        this.days = 0;
        this.info = "";
        this.sale = 0;
    }

    public Tour(int id, String tourType, String name, float price, String country, int days, String info, int sale) {
        this.id = id;
        this.tourType = tourType;
        this.name = name;
        this.price = price;
        this.country = country;
        this.days = days;
        this.info = info;
        this.sale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", tourType=" + tourType + '\'' +
                ", name=" + name + '\'' +
                ", price=" + price +
                ", country=" + country + '\'' +
                ", days=" + days +
                ", info=" + info + '\'' +
                ", sale=" + sale + "%" +
                '}';
    }
}
