package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {

        @GetMapping("/")
        public String mainPage() {
            return "redirect:/login";
        }

        @GetMapping("/admin/")
        public String adminPage() {
            return "users";
        }

        @GetMapping("/user/")
        public String userPage() {
            return "user";
        }

    }

