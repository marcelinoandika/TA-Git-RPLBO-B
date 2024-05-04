package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
    private static Connection conn;

    public static Connection getConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:memberDB.sqlite");
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, e);
        }

        return conn;
    }

}
