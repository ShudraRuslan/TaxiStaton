package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface CashierRepo extends CrudRepository<Cashier, Long> {
    Cashier getByOrderId(Long id);

    Cashier getById(Long id);

    @Query("SELECT SUM (c.balance) as balance,SUM (c.bookingCash) as bookingCash,SUM (c.carServiceCash) as carServiceCash,SUM (c.driverSalaryCash) as driverSalaryCash,SUM (c.fuelCash) as fuelCash from  Cashier c")
    Map<String, Double> getFullReport();

    @Query("SELECT SUM (c.balance) as balance,SUM (c.bookingCash) as bookingCash,SUM (c.carServiceCash) as carServiceCash,SUM (c.driverSalaryCash) as driverSalaryCash,SUM (c.fuelCash) as fuelCash " +
            "from  Cashier c INNER JOIN Orders o ON c.orderId=o.orderId WHERE o.date = CURRENT_DATE")
    Map<String, Double> getDailyReport();

    @Query("SELECT SUM (c.driverSalaryCash) from Cashier  c INNER JOIN Orders o ON o.orderId=c.orderId WHERE o.driverId=?1")
    double gerFullDriverSalary(Long id);

    @Query("SELECT c.id from Cashier c INNER JOIN Orders o ON c.orderId=o.orderId WHERE o.status=2")
    List<Long> getIdsForCashOfCancelledOrders();


}
