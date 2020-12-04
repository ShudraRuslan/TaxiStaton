package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.ServicesRealization.OrderFulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/orders")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminOrderController {

    private final OrderFulfillmentService service;

    @Autowired
    public AdminOrderController(OrderFulfillmentService service) {
        this.service = service;
    }

    @GetMapping
    public String orderReport(Map<String, Object> model) {
        model.put("orders", service.orderReport());
        return "AdminOrderPage";
    }

    @PostMapping
    public String orderFilter(Map<String, Object> model,
                              @RequestParam String select) {
        if (select.equals("s1"))
            model.put("orders", service.orderReport());
        else if (select.equals("s2"))
            model.put("orders", service.getOrdersByStatus(OrderStatus.isCompleted));
        else model.put("orders", service.getOrdersByStatus(OrderStatus.isCancelled));
        return "AdminOrderPage";

    }

    @GetMapping("/deleteCancelled")
    public String deleteCancelledOrders(){
        service.deleteOrdersByStatus(OrderStatus.isCancelled);
        return "redirect:/admin/orders";

    }

    @GetMapping("/deleteAll")
    public String deleteAllOrders(){
        service.deleteOrdersByStatus(OrderStatus.isCancelled);
        service.deleteOrdersByStatus(OrderStatus.isCompleted);
        return "redirect:/admin/orders";

    }
}
