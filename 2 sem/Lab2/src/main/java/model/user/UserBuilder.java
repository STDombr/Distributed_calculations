package model.user;

public class UserBuilder {
    private int id;
    private String nickname;
    private String password;
    private boolean admin;
    private float money;
    private int transactions;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }

    public UserBuilder setMoney(float money) {
        this.money = money;
        return this;
    }

    public UserBuilder setTransactions(int transactions) {
        this.transactions = transactions;
        return this;
    }

    public User build() {
        return new User(id, nickname, password, admin, money, transactions);
    }
}
