package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.Roles.Role;
import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.MainClasses.repos.ClientRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final ClientRepo repos;

    public UserService(ClientRepo repos) {
        this.repos = repos;
    }


    public boolean checkIfAlreadyExists(String nickname) {
        return repos.existsByUsername(nickname);
    }


    public boolean haveEnoughMoney(Long clientId, double payload) {
        return repos.getClientById(clientId).getCash() >= payload;
    }


    public void changeClientCash(Long clientId, double payload) {
        User user = repos.getClientById(clientId);
        user.setCash(user.getCash() - payload);
        repos.save(user);
    }

    public void changeClientPassword(Long clientId, String password) {
        User user = repos.getClientById(clientId);
        user.setPassword(password);
        repos.save(user);
    }

    public void changeClientUsername(Long clientId, String username){
        User user = repos.getClientById(clientId);
        user.setUsername(username);
        repos.save(user);
    }

    public User getClientById(Long clientId) {
        return repos.getClientById(clientId);
    }


    public void changeVip(Long id, boolean isVip) {
        User client = repos.getClientById(id);
        client.setVip(isVip);
        repos.save(client);
    }

    public void makeAnAdmin(Long id) {
        User user = repos.getClientById(id);
        user.getRoles().clear();
        user.getRoles().add(Role.ADMIN);
        repos.save(user);

    }

    public void deleteFromAdmin(Long id) {
        User user = repos.getClientById(id);
        user.getRoles().clear();
        user.getRoles().add(Role.USER);
        repos.save(user);
    }

    public void saveUser(User user) {
        repos.save(user);
    }


    public List<User> clientReport() {
        return (List<User>) repos.findAll();
    }

    public void deleteUserById(Long id){
        User user = repos.getClientById(id);
        repos.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repos.getClientByUsername(username);
    }
}
