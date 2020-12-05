package com.example.demo.Api.AdminControllers;


import com.example.demo.Services.MainClasses.DriverInfo.Driver;
import com.example.demo.Services.MainClasses.DriverInfo.DriverStatus;
import com.example.demo.Services.ServicesRealization.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/drivers")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDriverController {

    private final PersonnelService service;

    @Autowired
    public AdminDriverController(PersonnelService service) {
        this.service = service;
    }

    @GetMapping
    public String driverList(Map<String, Object> model) {
        List<Driver> drivers = service.driversReport();
        model.put("drivers", drivers);
        return "adminDriverListPage";
    }

    @GetMapping("{driver}")
    public String editDriver(@PathVariable Driver driver, Map<String, Object> model) {
        return fillingInTheModel(driver, model);
    }


    @GetMapping("{driver}/deleteDriver")
    public String deleteDriver(@PathVariable Driver driver) {
        service.deleteDriverById(driver.getDriverId());

        return "redirect:/admin/drivers";

    }


    @PostMapping
    public String addDriver(Map<String, Object> model,
                            @RequestParam(defaultValue = "") String name,
                            @RequestParam(defaultValue = "-1") double mileage) {
        boolean result = service.addNewDriverToPersonnel(name,  mileage);
        if (result)
            model.put("response", "Completed!");
        else model.put("response", "Something bad happened! Try again!");
        List<Driver> list = service.driversReport();
        model.put("drivers", list);
        return "adminDriverListPage";
    }

    @PostMapping("{driver}")
    public String changeDriverStatus(@PathVariable Driver driver, @RequestParam String select,
                                     Map<String, Object> model) {
        if (select.equals("s1"))
            service.changeDriverStatus(driver.getDriverId(), DriverStatus.free);
        else service.changeDriverStatus(driver.getDriverId(), DriverStatus.atWork);
        return fillingInTheModel(driver, model);
    }

    @PostMapping("{driver}/salary")
    public String changeDriverSalary(@PathVariable Driver driver, @RequestParam double salary) {
        if (salary>=0)
        service.changeDriverSalary(driver.getDriverId(),salary);

        return "redirect:/admin/drivers/{driver}";
    }


    private String fillingInTheModel(Driver driver, Map<String, Object> model) {
        model.put("id", driver.getDriverId());
        model.put("name", driver.getName());
        model.put("category", driver.getCategory());
        model.put("salary", driver.getSalary());
        model.put("status", driver.getStatus());
        model.put("mileage", driver.getMileage());
        model.put("todayOrders",service.getTodayCompletedOrders(driver.getDriverId()));
        model.put("totalOrders",service.getTotalCompletedOrders(driver.getDriverId()));
        return "adminDriverEditPage";
    }
}
