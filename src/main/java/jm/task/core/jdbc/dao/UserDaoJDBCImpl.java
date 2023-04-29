package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(50) NOT NULL," +
                    "lastName VARCHAR(50) NOT NULL," +
                    "age TINYINT NOT NULL" +
                    ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.connection.createStatement()) {
            String query = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.connection.createStatement()) {
            String query = "INSERT INTO users (name, lastName, age) VALUES " +
                    "('" + name + "', '" + lastName + "', " + age + ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.connection.createStatement()) {
            String query = "DELETE FROM users WHERE id = " + id;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.connection.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.connection.createStatement()) {
            String query = "DELETE FROM users";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
