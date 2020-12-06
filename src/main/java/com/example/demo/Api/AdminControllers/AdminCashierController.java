package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import com.example.demo.Services.ServicesRealization.CashierService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/cashier")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCashierController {
    private final CashierService service;

    AdminCashierController(CashierService service) {
        this.service = service;
    }

    @GetMapping
    public String cashierReport(Map<String, Object> model) {

        fillingTheModel(model);
        return "adminCashierPage";
    }


    @GetMapping("/clean")
    public String cleanAllValues() {
        service.cleanCashier();
        return "redirect:/admin/cashier";

    }

    @PostMapping
    public String getInfoAboutOrder(@RequestParam(defaultValue = "0") Long orderId,
                                    Map<String, Object> model,
                                    @RequestParam String select) {
        if (orderId != 0) {

            try {

                Cashier result = service.cashierReport(orderId);
                model.put("balance", result.getBalance());
                model.put("bookingCash", result.getBookingCash());
                model.put("driverSalaryCash", result.getDriverSalaryCash());
                model.put("fuelCash", result.getFuelCash());
                model.put("carServiceCash", result.getCarServiceCash());
                model.put("orderId", orderId);

            } catch (Exception e) {

                model.put("response", "There are no order with this id!");
            }

            return "adminCashierPage";
        } else if (select.equals("s1")) {

            fillingTheModel(model);
        } else {

            try {

                Map<String, Double> result = service.dailyCashierReport();
                model.put("balance", result.get("balance"));
                model.put("bookingCash", result.get("bookingCash"));
                model.put("driverSalaryCash", result.get("driverSalaryCash"));
                model.put("fuelCash", result.get("fuelCash"));
                model.put("carServiceCash", result.get("carServiceCash"));

            } catch (Exception e) {

                model.put("response", "There are no data to get info from!");
            }

        }

        return "adminCashierPage";
    }

    @GetMapping("{cashier}")
    public String currentCashierInfo(@PathVariable Cashier cashier, Map<String, Object> model) {
        model.put("balance", cashier.getBalance());
        model.put("bookingCash", cashier.getBookingCash());
        model.put("driverSalaryCash", cashier.getDriverSalaryCash());
        model.put("fuelCash", cashier.getFuelCash());
        model.put("carServiceCash", cashier.getCarServiceCash());
        model.put("orderId", cashier.getOrderId());
        model.put("cashId", cashier.getId());
        return "adminCashierReportPage";
    }

    @GetMapping("/cleanCancelled")
    public String cleanCancelledOrders() {
        service.cleanCashierFromCancelledOrders();
        return "redirect:/admin/cashier";

    }

    private void fillingTheModel(Map<String, Object> model) {
        try {
            Map<String, Double> result = service.fullCashierReport();
            model.put("balance", result.get("balance"));
            model.put("bookingCash", result.get("bookingCash"));
            model.put("driverSalaryCash", result.get("driverSalaryCash"));
            model.put("fuelCash", result.get("fuelCash"));
            model.put("carServiceCash", result.get("carServiceCash"));
        } catch (Exception e) {
            model.put("response", "There are no data to get info from!");
        }
    }


}
