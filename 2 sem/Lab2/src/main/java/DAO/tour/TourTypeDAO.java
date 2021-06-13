package DAO.tour;

import DAO.Converter;
import DAO.user.UserDAO;
import model.tour.TourType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class TourTypeDAO {
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public TourTypeDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TourType> getAllTourTypes() {
        List<TourType> list = new LinkedList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("tour_types.get_countries"), Statement.RETURN_GENERATED_KEYS);

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                TourType tourType = Converter.getTourTypeFromResultSet(result);
                list.add(tourType);
            }
        } catch (SQLException throwables) {
        }
        return list;
    }

}
