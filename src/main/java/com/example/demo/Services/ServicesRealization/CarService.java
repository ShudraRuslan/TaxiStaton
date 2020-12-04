package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.CarInfo.Car;
import com.example.demo.Services.MainClasses.CarInfo.CarStatus;
import com.example.demo.Services.MainClasses.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarService {

    private final CarRepo repos;

    @Autowired
    public CarService(CarRepo repos) {
        this.repos = repos;
    }

    public void changeCarMileage(Long id, double additionMileage) {
        Car car = repos.getCarByCarId(id);
        double newMileage = car.getMileage() + additionMileage;
        car.setMileage(newMileage);
        repos.save(car);
    }

    public void changeCarStatus(Long carId, CarStatus stat) {

        Car car = getCarById(carId);
        car.setStatus(stat);
        repos.save(car);
    }

    public boolean needsService(Long carId) {
        return getCarById(carId).getMileage() > 4000;
    }

    public void changeCarQualities(Long id) {

        Car car = repos.getCarByCarId(id);
        if (car.getMileage() >= 5000) {

            car.setStatus(CarStatus.isAtService);
            car.setMileage(0);
            repos.save(car);
        }

    }

    public boolean addNewCarToCarPark(String name, double enginePower,
                                      int capacity, double fuelConsumption, double mileage) {
        if (name.equals("") || enginePower <= 0 || capacity <= 0 || fuelConsumption <= 0 || mileage < 0)
            return false;
        try {
            Car car = new Car(name, enginePower, capacity, fuelConsumption, mileage);
            repos.save(car);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public void updateCarInformation(Long carId, double additionMileage) {

        changeCarMileage(carId, additionMileage);
        changeCarQualities(carId);

    }

    public Long findAppropriateCar(int amountOfPassengers, double distance) {

        List<Car> cars = repos.getCarByStatus(CarStatus.isFree);

        for (Car car : cars) {

            if (car.getCapacity() >= amountOfPassengers
                    && car.getEnginePower() >= distance) {

                System.out.println("Appropriate car is found successfully! It is:");
                System.out.println((car.getName()));
                repos.save(car);
                return car.getCarId();
            }
        }
        return null;
    }

    public List<Car> getCarsByStatus(CarStatus status) {
        return repos.getCarByStatus(status);
    }

    private boolean deleteOperation(List<Car> list) {
        if (list.size() == 0) return false;
        int iterator = 0;
        int iterationSize = list.size();

        for (iterator = 0; iterator < iterationSize; iterator++) {
            repos.delete(list.get(iterator));
        }
        return true;
    }


    public boolean deleteAllCarByStatus(CarStatus status) {
        List<Car> list = repos.getCarByStatus(status);
        return deleteOperation(list);
    }

    public boolean deleteAllCars() {
        List<Car> list = (List<Car>) repos.findAll();
        return deleteOperation(list);
    }

    public void deleteCarById(Long id) {

        List<Car> list = new ArrayList<>();
        list.add(repos.getCarByCarId(id));
        deleteOperation(list);

    }


    public List<Car> carsReport() {
        return (List<Car>) repos.findAll();
    }

    public Car getCarById(Long id) {
        return repos.getCarByCarId(id);
    }
}
