package com.example.demo.Api.ClientControllers;

import com.example.demo.Services.MainClasses.CarInfo.CarStatus;
import com.example.demo.Services.MainClasses.DriverInfo.Category;
import com.example.demo.Services.MainClasses.DriverInfo.DriverStatus;
import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import com.example.demo.Services.MainClasses.Roles.User;
import com.example.demo.Services.ServicesRealization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/client/order")
public class ClientOrderController {
    private final OrderFulfillmentService orderService;
    private final CarService carService;
    private final PersonnelService persService;
    private final CashierService cashService;
    private final UserService clientService;

    @Autowired
    public ClientOrderController(PersonnelService persServ, CarService carServ,
                                 UserService clientServ, CashierService cashServ, OrderFulfillmentService orderService) {
        this.carService = carServ;
        this.persService = persServ;
        this.clientService = clientServ;
        this.cashService = cashServ;
        this.orderService = orderService;
    }


    @GetMapping
    public String getOrderResponse(@AuthenticationPrincipal User user, Map<String, Object> model) {

        model.put("username", user.getUsername());
        model.put("cash", user.getCash());
        model.put("vip", user.isVip());
        model.put("response", "Here will be the result of your order!");
        return "clientOrderPage";
    }

    @PostMapping
    public String createOrder(Map<String, Object> model,
                              @RequestParam(required = false, defaultValue = "0") int amountOfPassengers,
                              @RequestParam(required = false, defaultValue = "0") double distance,
                              @AuthenticationPrincipal User user) {

        if (amountOfPassengers <= 0) {
            model.put("response", "Amount of passengers must be greater then one!");
            model.put("username", user.getUsername());
            model.put("cash", user.getCash());
            model.put("vip", user.isVip());
            return "clientOrderPage";
        }

        if (distance <= 0) {
            model.put("response", "Distance must be greater then one!");
            model.put("username", user.getUsername());
            model.put("cash", user.getCash());
            model.put("vip", user.isVip());
            return "clientOrderPage";
        }

        Orders order = orderService.createOrder(amountOfPassengers, distance, user);
        boolean isVip = clientService.getClientById(order.getClientId()).isVip();
        Long carId = carService.findAppropriateCar(amountOfPassengers, distance);


        double payload = cashService.getCurrentBookingCash(distance, amountOfPassengers, isVip);


        if (carId != null)
            orderService.serCarId(order.getOrderId(), carId);
        else {
            orderService.setOrderStatus(order.getOrderId(), OrderStatus.isCancelled);
            orderService.serCarId(order.getOrderId(), 0L);
            orderService.serDriverId(order.getOrderId(), 0L);
            model.put("response", "Orders can not be completed now! There is no appropriate car on the station!");
            model.put("username", user.getUsername());
            model.put("cash", user.getCash());
            model.put("vip", user.isVip());
            return "clientOrderPage";
        }
        Long driverId = persService.findAppropriateDriver();

        if (driverId != null)
            orderService.serDriverId(order.getOrderId(), driverId);
        else {
            orderService.setOrderStatus(order.getOrderId(), OrderStatus.isCancelled);
            orderService.serCarId(order.getOrderId(), 0L);
            orderService.serDriverId(order.getOrderId(), 0L);
            model.put("response", "Orders can not be completed now!There are no free drivers on the station");
            model.put("username", user.getUsername());
            model.put("cash", user.getCash());
            model.put("vip", user.isVip());
            return "clientOrderPage";
        }

        checkClientsPayAbility(order, payload);
        if (orderService.getOrderStatus(order.getOrderId()) == OrderStatus.isCancelled) {
            model.put("response", "You don`t have enough money!");
        } else {
            updateInformation(order.getOrderId());
            clientService.changeClientCash(user.getId(), payload);
            model.put("response", "Your order was completed successfully" + "\n" + order);
        }
        model.put("payload", payload);
        model.put("username", user.getUsername());
        model.put("cash", user.getCash());
        model.put("vip", user.isVip());
        return "clientOrderPage";


    }

    private void checkClientsPayAbility(Orders order, double payload) {

        if (!clientService.haveEnoughMoney(order.getClientId(), payload)) {

            System.out.println("CLIENT DOES NOT HAVE ENOUGH MONEY!");
            orderService.setOrderStatus(order.getOrderId(), OrderStatus.isCancelled);

        }
    }

    private void updateInformation(Long id) {
        Orders order = orderService.getOrderById(id);
        cashService.createCashier(order.getOrderId());

        Long driverId = order.getDriverId();
        Category category = persService.getDriverById(driverId).getCategory();
        Long carId = order.getCarId();

        double distance = order.getDistance();
        int amountOfPassengers = order.getAmountOfPassengers();
        boolean isVip = clientService.getClientById(order.getClientId()).isVip();
        double carFuelConsumption = carService.getCarById(carId).getFuelConsumption();
        boolean carNeedsService = carService.needsService(carId);

        orderService.setOrderStatus(order.getOrderId(), OrderStatus.isCompleted);
        persService.changeDriverStatus(driverId, DriverStatus.atWork);
        carService.changeCarStatus(carId, CarStatus.isAtWork);
        persService.updateDriverInformation(driverId, distance);
        carService.updateCarInformation(carId, distance);
        cashService.updateCashier(order.getOrderId(), distance, carFuelConsumption,
                amountOfPassengers, category, carNeedsService, isVip);
        double additionalDriverSalary = cashService.getDriverSalary(driverId);
        persService.changeDriverSalary(driverId, additionalDriverSalary);

    }

}


