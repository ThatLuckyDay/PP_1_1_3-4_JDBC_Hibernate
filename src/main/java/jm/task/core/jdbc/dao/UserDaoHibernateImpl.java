package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    private final String tableName = "User";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS %s (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(30),
                    lastName VARCHAR(30),
                    age TINYINT
                );
                """.formatted(tableName);

        executeQuery(query);
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS %s;".formatted(tableName);

        executeQuery(query);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(new User(name, lastName, age));

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM %s".formatted(tableName);

        try (Session session = sessionFactory.openSession()) {
            List<User> result = session.createQuery(hql, User.class)
                    .getResultList();
            System.out.println(result);
            return result;
        } catch (HibernateException e) {
            List<User> result = List.of();
            System.out.println(result);
            return result;
        }
    }

    @Override
    public void cleanUsersTable() {
        String hql = "DELETE FROM %s".formatted(tableName);

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery(hql).executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    private void executeQuery(String sqlQuery) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(sqlQuery)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
