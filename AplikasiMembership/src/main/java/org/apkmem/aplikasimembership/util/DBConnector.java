package org.apkmem.aplikasimembership.util;

import org.apkmem.aplikasimembership.data.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @editor David.Seay-71220909
 */
public class DBConnector {
    private final String DB_URL = "jdbc:sqlite:memberDB.sqlite";
    private Connection connection;

    private static volatile DBConnector instance = null;

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            synchronized (DBConnector.class) {
                if (instance == null) {
                    instance = new DBConnector();
                    instance.getConnection();
                    instance.createTable();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection error
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection closure error
            }
        }
    }


    public void createTable() {
        // Create database tables if they don't exist
        String dbUSER = "CREATE TABLE IF NOT EXISTS users ("
                + "id TEXT PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "no_telp DATE NOT NULL,"
                + "password TEXT NOT NULL"
                + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dbUSER);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle table creation error
        }
    }

    public List<Users> getAllDataUsers() {
        String query = "SELECT * FROM users";
        List<Users> usersList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String no_telp = resultSet.getString("no_telp");
                String pass = resultSet.getString("password");
                Users user = new Users(id,username, email, no_telp, pass);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
        return usersList;
    }

    public boolean deleteNilaiUser(Users users) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, users.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addNilaiUsers(Users users) {
        String query = "INSERT INTO users (id,username, email, no_telp, pasword) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, users.getId());
            preparedStatement.setString(2, users.getUsername());
            preparedStatement.setString(3, users.getEmail());
            preparedStatement.setString(4, users.getTelephone());
            preparedStatement.setString(5, users.getPassword());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
        return false;
    }

    public boolean updateNilaiUsers(Users usersOld, Users usersNew) {
        String query = "UPDATE users SET username=?, email=?, no_telp=?, password=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, usersNew.getUsername());
            preparedStatement.setString(2, usersNew.getEmail());
            preparedStatement.setString(3, usersNew.getTelephone());
            preparedStatement.setString(4, usersNew.getPassword());
            preparedStatement.setInt(5, usersOld.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
        return false;
    }

}
