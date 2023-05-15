package web.dao;

import web.model.Users;

import java.util.List;

public interface UserDao {
    List<Users> getAllUsers();

    Users getById(int id);

    void addUser(Users users);

    void update(Users users);

    void delete(int id);
}
