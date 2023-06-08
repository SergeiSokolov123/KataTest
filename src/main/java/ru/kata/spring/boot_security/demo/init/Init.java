package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private UserServiceImpl userServiceImpl;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserServiceImp(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        Role role = new Role("ROLE_ADMIN");
        Role role1 = new Role("ROLE_USER");
        roleRepository.save(role);
        roleRepository.save(role1);

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(role);
        userRole.add(role1);
        User user = new User("admin", "admin",  "admin@test.ru", "123", adminRole);
       User user1 = new User("user","user","user@test.ru","1234",userRole);
        userServiceImpl.addUser(user);
    userServiceImpl.addUser(user1);
    }
}
