package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    private Connection connection;

    private final String tableName = "users";

    public UserDaoJDBCImpl() {
        try {
            Util.createConnection();
            connection = Util.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS %s (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(30),
                    lastName VARCHAR(30),
                    age TINYINT
                );
                """.formatted(tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
            logger.log(Level.INFO, "Create table [ users ] done.\n");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS %s;".formatted(tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
            logger.log(Level.INFO, "Drop table [ users ] done.\n");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO  %s (name, lastName, age) VALUES (?,?,?);".formatted(tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            logger.log(Level.INFO, "Save user [ name={1} lastName={2} age={3} ] done.\n",
                    new Object[]{name, lastName, age});
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM %s WHERE id=?;".formatted(tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.log(Level.INFO, "Save user with [ id={1} ] done.\n", new Object[]{id});
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM %s;".formatted(tableName);
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(user);
            }
            logger.log(Level.INFO, "Get all users done.\n");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE %s;".formatted(tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
            logger.log(Level.INFO, "Clean users done.\n");
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
