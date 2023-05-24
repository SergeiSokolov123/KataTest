package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new-user";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit-user";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.getById(id));
        return "delete-user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/admin/";
    }
}
