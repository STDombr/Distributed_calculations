package model.user;

public class User {
    private int id;
    private String nickname;
    private String password;
    private boolean admin;
    private float money;
    private int transactions;

    public User() {
        this.id = -1;
        this.nickname = "";
        this.password = "";
        this.money = 0;
        this.transactions = 0;
    }

    public User(int id, String nickname, String password, boolean admin, float money, int transactions) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.admin = admin;
        this.money = money;
        this.transactions = transactions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname=" + nickname + '\'' +
                '}';
    }
}
