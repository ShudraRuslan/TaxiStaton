package com.example.demo.Api;

import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.ServicesRealization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AuthorizationController {
    private final UserService clientService;


    @Autowired
    AuthorizationController(UserService clientService) {
        this.clientService = clientService;

    }

    @GetMapping("/")
    public String mainPanel() {
        return "mainPage";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            model.put("message", "Please fill in all fields!");
            return "registrationPage";
        }
        boolean result = clientService.createUser(user);
        if (result) {
            return "redirect:/login";
        } else {
            model.put("message", "User with this username already exists!");
            return "registrationPage";
        }
    }


}
