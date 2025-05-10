package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final Logger logger = Logger.getLogger(Util.class.getName());

    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3?" +
            "useUnicode=yes&" +
            "characterEncoding=UTF8&" +
            "useSSL=false&" +
            "serverTimezone=Asia/Omsk";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        logger.log(Level.INFO, "Database connection created!\n");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        logger.log(Level.INFO, "Database connection closed!\n");
    }
}
