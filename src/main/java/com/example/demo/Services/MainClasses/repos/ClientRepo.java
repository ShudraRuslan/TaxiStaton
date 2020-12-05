package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.Roles.User;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends CrudRepository<User, Long> {

    boolean existsByUsername(String nickname);

    User getClientById(Long id);

    User getClientByUsername(String nickname);
}
