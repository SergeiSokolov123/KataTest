package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Users;
import web.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserService usersService;

    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        model.addAttribute("users", usersService.getById(id));
        return "users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") Users user) {
        return "new-user";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") Users user) {
        usersService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable int id) {
        model.addAttribute("user", usersService.getById(id));
        return "edit-user";
    }

    @PutMapping("/update/{id}")
    public String update(@ModelAttribute("user") Users user) {
        usersService.update(user);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(Model model, @PathVariable int id) {
        model.addAttribute("user", usersService.getById(id));
        return "delete-user";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        usersService.delete(id);
        return "redirect:/";
    }
}
