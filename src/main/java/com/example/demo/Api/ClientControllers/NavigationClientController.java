package com.example.demo.Api.ClientControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class NavigationClientController {

    @GetMapping
    public String navigation() {
        return "clientNavigationPage";
    }
}
