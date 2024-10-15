package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Updated JDBC URL with SSL parameters
    private static final String URL = "jdbc:mysql://localhost:3306/ticketing_application";
    private static final String USER = "root";
    private static final String PASSWORD = "Pass@147";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Successfully connected to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to create the database connection.");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

}
