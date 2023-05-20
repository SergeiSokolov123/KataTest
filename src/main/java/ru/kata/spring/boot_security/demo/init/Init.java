package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
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
        roleRepository.save(role);

        Set<Role> adminRole = new HashSet<>();
        adminRole.add(role);

        Users user = new Users("admin", 10, "admin", adminRole);
        userServiceImpl.addUser(user);
    }
}
