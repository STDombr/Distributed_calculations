package model.order;

public class OrderBuilder {
    private int tourId;
    private int userId;
    private int count;
    private String date;

    public OrderBuilder setTourId(int tourId) {
        this.tourId = tourId;
        return this;
    }

    public OrderBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    public OrderBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public Order build() {
        return new Order(tourId, userId, count, date);
    }
}
