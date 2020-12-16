package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import com.example.demo.Services.ServicesRealization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/orders")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminOrderController {

    private final OrderFulfillmentService service;
    private final UserService userService;
    private final CashierService cashService;
    private final PersonnelService persService;
    private final CarService carService;

    @Autowired
    public AdminOrderController(OrderFulfillmentService service, UserService userService, CashierService cashService,
                                CarService carService, PersonnelService persService) {
        this.service = service;
        this.userService = userService;
        this.cashService = cashService;
        this.persService = persService;
        this.carService = carService;
    }

    @GetMapping
    public String orderReport(Map<String, Object> model) {
        model.put("orders", service.orderReport());
        model.put("cancelled", service.getNumberOfOrdersByStatus(OrderStatus.isCancelled));
        model.put("completed", service.getNumberOfOrdersByStatus(OrderStatus.isCompleted));
        return "adminOrderListPage";
    }

    @PostMapping
    public String orderFilter(Map<String, Object> model,
                              @RequestParam String select) {
        if (select.equals("s1")) {
            model.put("orders", service.orderReport());
            model.put("cancelled", service.getNumberOfOrdersByStatus(OrderStatus.isCancelled));
            model.put("completed", service.getNumberOfOrdersByStatus(OrderStatus.isCompleted));
        } else if (select.equals("s2")) {
            model.put("orders", service.getOrdersByStatus(OrderStatus.isCompleted));
            model.put("completed", service.getNumberOfOrdersByStatus(OrderStatus.isCompleted));
        } else {
            model.put("orders", service.getOrdersByStatus(OrderStatus.isCancelled));
            model.put("cancelled", service.getNumberOfOrdersByStatus(OrderStatus.isCancelled));
        }

        return "adminOrderListPage";

    }

    @GetMapping("/deleteCancelled")
    public String deleteCancelledOrders() {
        service.deleteOrdersByStatus(OrderStatus.isCancelled);
        return "redirect:/admin/orders";

    }

    @GetMapping("/deleteAll")
    public String deleteAllOrders() {
        service.deleteOrdersByStatus(OrderStatus.isCancelled);
        service.deleteOrdersByStatus(OrderStatus.isCompleted);
        return "redirect:/admin/orders";

    }

    @GetMapping("{order}")
    public String currentOrderPage(@PathVariable Orders order,
                                   Map<String, Object> model) {

        if (persService.checkIfExists(order.getDriverId()))
            model.put("driverId", order.getDriverId());
        if (carService.checkIfExists(order.getCarId()))
            model.put("carId", order.getCarId());
        if (userService.checkIfExists(order.getClientId()))
            model.put("userId", order.getClientId());
        Long cashId = cashService.getCashierIdFromOrder(order.getOrderId());
        if (cashId != 0)
            model.put("cashId", cashId);
        if (model.isEmpty())
            model.put("response", "No info to show!");
        return "adminCurrentOrderPage";

    }
}
