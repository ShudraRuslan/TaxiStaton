package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.ServicesRealization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
@PreAuthorize("hasAuthority('SUPERUSER')")
public class AdminUserController {
    private final UserService service;

    @Autowired
    AdminUserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String allUsers(Map<String, Object> model) {
        List<User> users = service.clientReport();
        model.put("users", users);
        return "adminUserListPage";

    }

    @GetMapping("{user}")
    public String editUser(@PathVariable User user, Map<String, Object> model) {
        model.put("username", user.getUsername());
        model.put("id", user.getId());
        model.put("roles", user.getRoles());
        model.put("vip", user.isVip());
        return "adminUserEditPage";
    }

    @PostMapping("{user}/vip")
    public String changeClientStatus(@PathVariable User user, @RequestParam String select,
                                     Map<String, Object> model) {
        service.changeVip(user.getId(), select.equals("s1"));
        return "redirect:/admin/user/{user}";

    }

    @PostMapping("{user}/makeAdmin")
    public String setAdmin(@PathVariable User user, @RequestParam String select,
                           Map<String, Object> model) {
        if (select.equals("s1"))
            service.makeAnAdmin(user.getId());
        else service.deleteFromAdmin(user.getId());
        return "redirect:/admin/user/{user}";

    }

    @GetMapping("{user}/delete")
    public String deleteUser(@PathVariable User user) {
        service.deleteUserById(user.getId());

        return "redirect:/admin/user";

    }

}
