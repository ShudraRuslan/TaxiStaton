package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.DriverInfo.Driver;
import com.example.demo.Services.MainClasses.DriverInfo.DriverStatus;
import org.hibernate.persister.entity.Loadable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepo extends CrudRepository<Driver, Long> {

    List<Driver> getAllDriversByStatus(DriverStatus status);

    Driver getByDriverId(Long id);

    @Query("SELECT COUNT(o.orderId) FROM Orders o INNER JOIN Driver d ON o.driverId=d.driverId WHERE d.driverId=?1")
    int getTotalCountOfOrders(Long id);

    @Query("SELECT COUNT(o.orderId) FROM Orders o INNER JOIN Driver d ON o.driverId=d.driverId WHERE d.driverId=?1 AND o.date=CURRENT_DATE ")
    int getTodayCountOfOrders(Long id);


}
