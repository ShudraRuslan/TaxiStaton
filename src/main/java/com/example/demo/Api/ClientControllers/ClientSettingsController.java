package com.example.demo.Api.ClientControllers;

import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.ServicesRealization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/client/settings")
public class ClientSettingsController {
    private final UserService service;

    @Autowired
    public ClientSettingsController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String getSettingPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("username", user.getUsername());
        model.put("cash", user.getCash());
        model.put("vip", user.isVip());
        model.put("id", user.getId());
        return "clientSettingsPage";
    }

    @PostMapping
    public String saveChanges(@AuthenticationPrincipal User user,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "") String password,
                              @RequestParam(defaultValue = "0") double additionalCash,
                              Map<String,Object> model) {
        if(!username.equals("")){
            service.changeClientUsername(user.getId(),username);
        }

        if(!password.equals("")){
            service.changeClientPassword(user.getId(),password);
        }
        if(additionalCash!=0){
            service.changeClientCash(user.getId(),-additionalCash);
        }

        User changedUser = service.getClientById(user.getId());
        model.put("username", changedUser.getUsername());
        model.put("cash", changedUser.getCash());
        model.put("vip", changedUser.isVip());
        model.put("id", changedUser.getId());
        return "clientSettingsPage";



    }
}
