package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.DriverInfo.Driver;
import com.example.demo.Services.MainClasses.DriverInfo.DriverStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepo extends CrudRepository<Driver, Long> {

    List<Driver> getAllDriversByStatus(DriverStatus status);

    Driver getByDriverId(Long id);


}
