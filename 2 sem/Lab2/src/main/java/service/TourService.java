package service;

import DAO.FactoryDAO;
import DAO.country.CountryDAO;
import DAO.order.OrderDAO;
import DAO.tour.TourDAO;
import DAO.tour.TourTypeDAO;
import DAO.user.UserDAO;
import model.country.Country;
import model.order.Order;
import model.tour.Tour;
import model.tour.TourBuilder;
import model.tour.TourType;
import model.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TourService {
    private final TourDAO tourDAO;
    private final TourTypeDAO tourTypeDAO;
    private final CountryDAO countryDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public TourService() {
        this.tourDAO = FactoryDAO.createTourDao();
        this.tourTypeDAO = FactoryDAO.createTourTypeDao();
        this.countryDAO = FactoryDAO.createCountryDao();
        this.orderDAO = FactoryDAO.createOrderDao();
        this.userDAO = FactoryDAO.createUserDao();
    }

    public List<Tour> getTours() {
        List<TourType> tourTypes = tourTypeDAO.getAllTourTypes();
        List<Country> countries = countryDAO.getAllCountries();

        return tourDAO.getAllTours(tourTypes, countries);
    }

    public Tour getTourById(Integer id) {

        if (id > 0) {
            List<TourType> tourTypes = tourTypeDAO.getAllTourTypes();
            List<Country> countries = countryDAO.getAllCountries();


            Tour tour = tourDAO.getTourById(id, tourTypes, countries);
            if (tour.getId() == id) {
                List<Order> orders = orderDAO.getOrderByTourId(tour.getId());
                int count = 0;
                for (int i = 0; i < orders.size(); i++) {
                    try {
                        Date date = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH).parse(orders.get(i).getDate());

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -7);

                        if (!date.after(cal.getTime())) {
                            count++;
                        }
                    } catch (ParseException e) {
                    }
                }

                return tour;
            }
        }

        return null;
    }

    public boolean updateTour(Tour tour) {

        if (tour.getId() > 0) {
            tourDAO.updateTourById(tour.getId(), tour);

            return true;
        } else {
            return false;
        }
    }

    public boolean newOrder(Tour tour, User user, Integer count) {

        if (tour.getId() > 0) {

            if (user.getMoney() >= (count * tour.getPrice() * (100 - tour.getSale())) / 100) {
                orderDAO.insertOrder(tour.getId(), user.getId(), count);
                userDAO.addToMoney(user, (float) ((-1) * (count * tour.getPrice() * (100 - tour.getSale())) / 100));

                return true;
            }
        }

        return false;
    }

    public List<Order> getOrderByUserId(User user) {

        List<Order> orders = orderDAO.getOrderByUserId(user.getId());
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setName(tourDAO.getTourById(orders.get(i).getTourId(), null, null).getName());
        }

        return orders;
    }
}
