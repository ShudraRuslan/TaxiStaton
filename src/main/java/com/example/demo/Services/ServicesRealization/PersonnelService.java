package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.DriverInfo.Category;
import com.example.demo.Services.MainClasses.DriverInfo.Driver;
import com.example.demo.Services.MainClasses.DriverInfo.DriverStatus;
import com.example.demo.Services.MainClasses.repos.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonnelService {

    private final DriverRepo repos;

    @Autowired
    public PersonnelService(DriverRepo repos) {
        this.repos = repos;
    }

    private void changeDriversMileage(Long id, double additionMileage) {
        Driver driver = repos.getByDriverId(id);
        double newMileage = driver.getMileage() + additionMileage;
        driver.setMileage(newMileage);
        repos.save(driver);
    }

    public void changeDriverStatus(Long driverId, DriverStatus stat) {

        Driver driver = repos.getByDriverId(driverId);
        driver.setStatus(stat);
        repos.save(driver);
    }

    private void changeDriversQualities(Long id) {
        Driver driver = repos.getByDriverId(id);

        if (driver.getMileage() < 1000) {

            driver.setCategory(Category.A);
            driver.setSalary((1000));
            repos.save(driver);

        } else if (driver.getMileage() < 2000) {

            driver.setCategory(Category.B);
            driver.setSalary(driver.getSalary() + 1000);
            repos.save(driver);

        } else if (driver.getMileage() < 3000) {

            driver.setCategory(Category.C);
            driver.setSalary(driver.getSalary() + 1000);
            repos.save(driver);

        } else if (driver.getMileage() < 4000) {

            driver.setCategory(Category.D);
            driver.setSalary(driver.getSalary() + 1000);
            repos.save(driver);

        }
    }

    private Category getDriverCategory(double mileage) {
        if (mileage < 1000) {

            return Category.A;

        } else if (mileage < 2000) {

            return Category.B;

        } else if (mileage < 3000) {

            return Category.C;

        } else
            return Category.D;

    }


    public void updateDriverInformation(Long driverId, double additionMileage) {

        changeDriversMileage(driverId, additionMileage);
        changeDriversQualities(driverId);


    }

    public boolean addNewDriverToPersonnel(String name, double salary, double mileage) {
        if (name.equals("") || salary <= 0 || mileage < 0)
            return false;
        try {
            Category category = getDriverCategory(mileage);
            Driver driver = new Driver(name, category, salary, mileage);
            repos.save(driver);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Long findAppropriateDriver() {
        List<Driver> personnel = repos.getAllDriversByStatus(DriverStatus.free);

        for (Driver driver : personnel) {

            System.out.println("Success! Driver was found! It is:");
            System.out.println(driver.getName());
            repos.save(driver);
            return driver.getDriverId();

        }
        return null;
    }

    public List<Driver> getDriversByStatus(DriverStatus status) {
        return repos.getAllDriversByStatus(status);
    }

    private boolean deleteOperation(List<Driver> list) {
        if (list.size() == 0) return false;
        int iterator = 0;
        int iterationSize = list.size();

        for (iterator = 0; iterator < iterationSize; iterator++) {

            repos.delete(list.get(iterator));
        }
        return true;
    }


    public boolean deleteAllDriverByStatus(DriverStatus status) {

        List<Driver> list = repos.getAllDriversByStatus(status);
        return deleteOperation(list);
    }

    public boolean deleteAllDrivers() {
        List<Driver> list = (List<Driver>) repos.findAll();
        return deleteOperation(list);
    }

    public void deleteDriverById(Long id) {

        List<Driver> list = new ArrayList<>();
        list.add(repos.getByDriverId(id));
        deleteOperation(list);

    }


    public List<Driver> driversReport() {
        return (List<Driver>) repos.findAll();
    }

    public Driver getDriverById(Long driverId) {
        return repos.getByDriverId(driverId);
    }

    public void changeDriverSalary(Long id, double salary) {
        Driver driver = repos.getByDriverId(id);
        driver.setSalary(salary);
        repos.save(driver);
    }
}

