package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String s = "CREATE TABLE IF NOT EXISTS users (id mediumint NOT NULL auto_increment, name varchar(50) NOT NULL, lastName varchar(60) NOT NULL, age tinyint, primary key(id))";
        try (Connection connection = Util.getConnection();) {
            try (Statement statement = connection.createStatement();) {
                connection.setAutoCommit(false);
                statement.executeUpdate(s);
                System.out.println("Table was created");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    public void dropUsersTable() {
        String s = "DROP TABLE if exists users";

        try (Connection connection = Util.getConnection();) {
            try (Statement statement = connection.createStatement();) {
                statement.executeUpdate(s);
                System.out.println("Table was deleted");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String s = "insert into users (name, lastName, age) values (?, ?, ?)";
        try (Connection connection = Util.getConnection();) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(s);) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                System.out.println("Пользователь: " + name + " добавлен в базу данных");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String s = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(s);) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Пользователь: " + id + " удален из БД");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name, lastName, age FROM users");
            list = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();) {
            try (Statement statement = connection.createStatement();) {
                statement.executeUpdate("Truncate Table Users");
                System.out.println("Table deleted");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}