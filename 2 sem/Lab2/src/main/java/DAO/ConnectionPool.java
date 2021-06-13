package DAO;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance = null;

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/travel_agency");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
        }
        return connection;
    }
}
