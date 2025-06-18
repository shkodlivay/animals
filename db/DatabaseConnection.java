package db;

import properties.FilePropertiesReader;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

// Connection to DB and sent requests
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = getConnection();
            if (connection != null)
                System.out.println("Соединение с БД установлено");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Public method to get the singleton instance
    public static DatabaseConnection getInstance() throws SQLException, IOException {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Public method to get the connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Map<String, String> settings = new FilePropertiesReader().getSettings();
                connection = DriverManager.getConnection(settings.get("url"), settings.get("user"), settings.get("password"));
            }
        } catch (SQLException e) {
            System.err.println("Соединение с БД не установлено");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    // Method to execute a query (SELECT)
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);

        // Set parameters if any
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        return preparedStatement.executeQuery();
    }

    // Method to execute an update (INSERT, UPDATE, DELETE)
    public int executeUpdate(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        return preparedStatement.executeUpdate();
    }
}
