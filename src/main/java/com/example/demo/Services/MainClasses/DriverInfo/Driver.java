package com.example.demo.Services.MainClasses.DriverInfo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@EnableAutoConfiguration
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long driverId;
    private String name;
    private Category category;
    private double salary;
    private DriverStatus status;
    private double mileage;

    public Driver() {

    }

    public Driver(String name, Category category, double mileage) {
        this.name = name;
        this.category = category;
        this.mileage = mileage;
        this.salary = 0;
        this.status = DriverStatus.free;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "DriverID is " + driverId + " Driver name is " + name + " Driver status is " + status +
                " DriverMileage is " + mileage + " DriverSalary is " + salary + " DriverCategory is " + category;
    }
}
