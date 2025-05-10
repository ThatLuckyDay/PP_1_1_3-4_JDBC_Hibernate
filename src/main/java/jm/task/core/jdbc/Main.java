package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        logger.log(Level.INFO, "Create table [ users ]");
        userService.createUsersTable();

        logger.log(Level.INFO, "Insert users.");
        for (int i = 0; i < 4; i++) {
            userService.saveUser("name" + i, "lastname" + i, (byte) (i * 10));
            logger.log(Level.FINE, "Insert user {1}.", i);
        }

        logger.log(Level.INFO, "Get all users.");
        userService.getAllUsers();

        logger.log(Level.INFO, "Clean all users.");
        userService.cleanUsersTable();

        logger.log(Level.INFO, "Delete table [ users ].");
        userService.dropUsersTable();
    }
}
