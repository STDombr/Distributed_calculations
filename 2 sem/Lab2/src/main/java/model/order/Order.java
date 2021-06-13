package model.order;

public class Order {
    private String name;
    private int tourId;
    private int userId;
    private int count;
    private String date;

    public Order(int tourId, int userId, int count, String date) {
        this.tourId = tourId;
        this.userId = userId;
        this.count = count;
        this.date = date;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "tourId=" + tourId +
                ", userId=" + userId +
                ", count=" + count +
                ", date=" + date + '\'' +
                '}';
    }
}
