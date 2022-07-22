package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    public static final SessionFactory factory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        String s = "CREATE TABLE IF NOT EXISTS users (id mediumint NOT NULL auto_increment, name varchar(50) NOT NULL, lastName varchar(60) NOT NULL, age tinyint, primary key(id))";
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.getTransaction();
            session.beginTransaction();
            session.createSQLQuery(s).executeUpdate();
            tx.commit();
            System.out.println("Table was created");
        }
        catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String s = "DROP TABLE if exists users";
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.getTransaction();
            session.beginTransaction();
            session.createSQLQuery(s).executeUpdate();
            System.out.println("Table was dropped");
            tx.commit();
        }
        catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.getTransaction();
            session.beginTransaction();
            session.save(user);
            System.out.println("User " + name + " was created");
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.getTransaction();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User " + user.getName() + " was deleted");
            tx.commit();
        }
        catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.beginTransaction();
            session.beginTransaction();
            users = session.createQuery("FROM User")
                    .getResultList();
            System.out.println(users);
            tx.commit();
        } catch (HibernateException e) {
            assert tx != null;
            tx.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String s = "Truncate Table Users";
        Transaction tx = null;
        try (Session session = factory.getCurrentSession()) {
            tx = session.getTransaction();
            session.beginTransaction();
            session.createSQLQuery(s).executeUpdate();
            System.out.println("Table was cleared");
            tx.commit();
        }
        catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}
