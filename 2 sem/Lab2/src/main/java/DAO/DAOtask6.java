package DAO;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DAOtask6 {
    private final String url = "jdbc:postgresql://localhost:5432/task6";
    private final String user = "postgres";
    private final String password = "0000";
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public DAOtask6() throws SQLException {
            this.connection = DriverManager.getConnection(url, user, password);
    }

    public String request1(Integer id) {
        String str = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request1"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                str = "Order[";
                str += String.valueOf(result.getInt("id")) + ", ";
                str += String.valueOf(result.getString("date")) + ", ";
                str += String.valueOf(result.getInt("count")) + ", ";
                str += String.valueOf(result.getInt("price")) + ", ";
                str += String.valueOf(result.getString("name")) + ", ";
                str += String.valueOf(result.getString("info")) + "]";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String request2(Double summ, Integer count) {
        String str = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request2"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, summ);
            preparedStatement.setInt(2, count);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                str += String.valueOf(result.getInt("id")) + ", ";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String request3(String name) {
        String str = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request3"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                str += String.valueOf(result.getInt("id")) + ", ";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String request4(String name) {
        String str = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request4"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            preparedStatement.setString(2, df.format(new Date()));

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                str += String.valueOf(result.getInt("id")) + ", ";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public void request5() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request5"), Statement.RETURN_GENERATED_KEYS);


            ResultSet result = preparedStatement.executeQuery();
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void request6(String name, Integer count) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("request6"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, count);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
