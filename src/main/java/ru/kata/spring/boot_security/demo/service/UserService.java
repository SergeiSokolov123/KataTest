package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getAllUsers();

    Users getById(int id);

    void addUser(Users users);

    void update(Users users);

    void delete(int id);

    UserDetails loadUserByUsername(String username);
}