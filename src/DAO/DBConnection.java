package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeManagement"; // Replace with your DB name
    private static final String USER = "root"; // Default XAMPP username
    private static final String PASSWORD = "";

    // Static connection instance
    private static Connection connection;

    // Method to get the database connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");                // Establish connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver not found: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}