package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String username = "root";
    private static final String password = "root";


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try  {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            System.out.println("Sucessfully connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return connection;
    }
}
