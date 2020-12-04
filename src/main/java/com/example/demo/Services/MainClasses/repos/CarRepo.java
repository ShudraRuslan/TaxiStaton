package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.CarInfo.Car;
import com.example.demo.Services.MainClasses.CarInfo.CarStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepo extends CrudRepository<Car, Long> {
    List<Car> getCarByStatus(CarStatus status);

    Car getCarByCarId(Long id);
}
