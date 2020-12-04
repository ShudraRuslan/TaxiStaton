package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface CashierRepo extends CrudRepository<Cashier, Long> {
    Cashier getByOrderId(Long id);
    @Query("SELECT SUM (c.balance) as balance,SUM (c.bookingCash) as bookingCash,SUM (c.carServiceCash) as carServiceCash,SUM (c.driverSalaryCash) as driverSalaryCash,SUM (c.fuelCash) as fuelCash from  Cashier c")
    Map<String,Double> getFullReport();
}
