package service;

import DAO.FactoryDAO;
import DAO.user.UserDAO;
import model.user.User;


public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = FactoryDAO.createUserDao();
    }

    public User loginUser(String nickname, String password) {
        User user = userDAO.findUserByNickname(nickname).orElse(new User());

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public boolean registerUser(String nickname, String password) {
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);

        if (userDAO.insertUser(user)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean topUp(User user, Float money) {
        if (money == null) {
            return false;
        }

        if (userDAO.addToMoney(user, money)) {
            return true;
        } else {
            return false;
        }
    }
}
