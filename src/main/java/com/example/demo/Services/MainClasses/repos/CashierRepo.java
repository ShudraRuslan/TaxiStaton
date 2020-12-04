package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import org.springframework.data.repository.CrudRepository;

public interface CashierRepo extends CrudRepository<Cashier, String> {
    Cashier getByNameOfTheStation(String name);
}
