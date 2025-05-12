package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final Logger logger = Logger.getLogger(Util.class.getName());

    private static Connection connection;

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3?" +
            "useUnicode=yes&" +
            "characterEncoding=UTF8&" +
            "useSSL=false&" +
            "serverTimezone=Asia/Omsk";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Util.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        logger.log(Level.INFO, "Database connection created!\n");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        logger.log(Level.INFO, "Database connection closed!\n");
    }

    private static SessionFactory buildSessionFactory() {
        return new Configuration()
                /* connection */
                .setProperty(Environment.DRIVER, DRIVER)
                .setProperty(Environment.URL, URL)
                .setProperty(Environment.USER, USER)
                .setProperty(Environment.PASS, PASSWORD)
                /* hibernate */
                .setProperty(Environment.DIALECT, HIBERNATE_DIALECT)
                .setProperty(Environment.SHOW_SQL, "false")
                .setProperty(Environment.FORMAT_SQL, "true")
                .setProperty(Environment.HBM2DDL_AUTO, "none")
                /* classes */
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
