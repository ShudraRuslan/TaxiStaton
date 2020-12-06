package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.MainClasses.CarInfo.Car;
import com.example.demo.Services.MainClasses.CarInfo.CarStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import com.example.demo.Services.ServicesRealization.CarService;
import com.example.demo.Services.ServicesRealization.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/cars")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCarController {
    private final CarService service;
    private final OrderFulfillmentService orderService;

    @Autowired
    public AdminCarController(CarService service, OrderFulfillmentService orderService) {
        this.service = service;
        this.orderService = orderService;
    }

    @GetMapping
    public String carList(Map<String, Object> model) {
        List<Car> cars = service.carsReport();
        model.put("cars", cars);
        return "adminCarListPage";
    }

    @GetMapping("{car}")
    public String carEdit(@PathVariable Car car, Map<String, Object> model) {
        return fillingInModel(car, model);
    }

    @GetMapping("{car}/deleteCar")
    public String deleteCar(@PathVariable Car car) {
        service.deleteCarById(car.getCarId());
        return "redirect:/admin/cars";
    }

    @PostMapping

    public String addCar(Map<String, Object> model,
                         @RequestParam(defaultValue = "") String name,
                         @RequestParam(defaultValue = "-1") double mileage,
                         @RequestParam(defaultValue = "0") double fuelConsumption,
                         @RequestParam(defaultValue = "0") double enginePower,
                         @RequestParam(defaultValue = "0") int capacity) {

        boolean result = service.addNewCarToCarPark(name, enginePower, capacity, fuelConsumption, mileage);
        if (result)
            model.put("response", "Completed!");
        else model.put("response", "Something bad happened! Try again!");
        List<Car> list = service.carsReport();
        model.put("cars", list);
        return "adminCarListPage";
    }

    @PostMapping("{car}")
    public String changeCarStatus(@PathVariable Car car, @RequestParam String select,
                                  Map<String, Object> model) {
        if (select.equals("s1"))
            service.changeCarStatus(car.getCarId(), CarStatus.isFree);
        else if (select.equals("s2"))
            service.changeCarStatus(car.getCarId(), CarStatus.isAtWork);
        else service.changeCarStatus(car.getCarId(), CarStatus.isAtService);
        return fillingInModel(car, model);
    }


    private String fillingInModel(Car car, Map<String, Object> model) {
        model.put("id", car.getCarId());
        model.put("name", car.getName());
        model.put("mileage", car.getMileage());
        model.put("fuelConsumption", car.getFuelConsumption());
        model.put("enginePower", car.getEnginePower());
        model.put("capacity", car.getCapacity());
        model.put("status", car.getStatus());
        List<Orders> orders = orderService.getOrdersWithCar(car.getCarId());
        if (orders.size() == 0)
            model.put("orders", "No orders with this car at the moment!");
        else
            model.put("orders", orders);
        return "adminCarEditPage";
    }

}
