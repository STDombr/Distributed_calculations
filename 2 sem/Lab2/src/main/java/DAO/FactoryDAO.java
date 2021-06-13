package DAO;

import DAO.country.CountryDAO;
import DAO.order.OrderDAO;
import DAO.tour.TourDAO;
import DAO.tour.TourTypeDAO;
import DAO.user.UserDAO;

public class FactoryDAO {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static UserDAO userDAO = null;
    private static TourDAO tourDAO = null;
    private static TourTypeDAO tourTypeDAO = null;
    private static CountryDAO countryDAO = null;
    private static OrderDAO orderDAO = null;

    public static UserDAO createUserDao() {
        if (userDAO == null) {
            userDAO = new UserDAO(connectionPool.getConnection());
        }
        return userDAO;
    }

    public static TourDAO createTourDao() {
        if (tourDAO == null) {
            tourDAO = new TourDAO(connectionPool.getConnection());
        }
        return tourDAO;
    }

    public static TourTypeDAO createTourTypeDao() {
        if (tourTypeDAO == null) {
            tourTypeDAO = new TourTypeDAO(connectionPool.getConnection());
        }
        return tourTypeDAO;
    }

    public static CountryDAO createCountryDao() {
        if (countryDAO == null) {
            countryDAO = new CountryDAO(connectionPool.getConnection());
        }
        return countryDAO;
    }

    public static OrderDAO createOrderDao() {
        if (orderDAO == null) {
            orderDAO = new OrderDAO((connectionPool.getConnection()));
        }
        return orderDAO;
    }
}
