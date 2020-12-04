package com.example.demo.Api;

import com.example.demo.Services.MainClasses.Roles.Role;
import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.ServicesRealization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
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

        if (clientService.checkIfAlreadyExists(user.getUsername())) {
            model.put("message", "User with this nickname already exists!");
            return "registrationPage";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        clientService.saveUser(user);
        return "redirect:/login";
    }


}
