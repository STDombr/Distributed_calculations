package DAO.user;

import DAO.Converter;
import model.user.User;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserDAO {
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertUser(User user) {
        try {
            if (findUserByNickname(user.getNickname()).isPresent()) {
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("users.create"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isAdmin());
            preparedStatement.setFloat(4, user.getMoney());
            preparedStatement.setInt(5, user.getTransactions());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
        }
        return true;
    }

    public Optional<User> findUserByNickname(String nickname) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("users.find_by_nickname"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, nickname);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return Optional.of(Converter.getUserFromResultSet(result));
            }
        } catch (SQLException throwables) {
        }
        return Optional.empty();
    }

    public Optional<Float> getMoney(String nickname) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("users.get_money"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, nickname);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return Optional.of(Converter.getMoneyFromResultSet(result));
            }
        } catch (SQLException throwables) {
        }
        return Optional.empty();
    }

    public boolean addToMoney(User user, Float popUpMoney) {
        float money = getMoney(user.getNickname()).orElseThrow(() -> new RuntimeException("Cant find user money!"));

        money += popUpMoney;

        if (money >= 0) {

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("users.update_money"), Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setFloat(1, money);
                preparedStatement.setString(2, user.getNickname());
                preparedStatement.executeUpdate();

                user.setMoney(money);
                return true;
            } catch (SQLException throwables) {
            }
        }
        return false;
    }
}
