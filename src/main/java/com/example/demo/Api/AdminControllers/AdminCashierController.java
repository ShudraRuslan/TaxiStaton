package com.example.demo.Api.AdminControllers;

import com.example.demo.Services.ServicesRealization.CashierService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/cashier")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCashierController {
    private final CashierService service;

    AdminCashierController(CashierService service){
        this.service=service;
    }

    @GetMapping
    public String cashierController(Map<String,Object> model){
        model.put("cashier",service.cashierReport());
        return "adminCashierPage";
    }

    @PostMapping
    public String changeCashier(Map<String,Object> model,
                                @RequestParam(required = false,defaultValue = "0") double bookingCash,
                                @RequestParam(required = false,defaultValue = "0")  double driverSalaryCash,
                                @RequestParam(required = false,defaultValue = "0")  double carServiceCash,
                                @RequestParam(required = false,defaultValue = "0")  double fuelCash){
        boolean result = service.changeCashier(bookingCash, driverSalaryCash, carServiceCash, fuelCash);
        if(result){
            model.put("response","Completed!");
        }
        else {
            model.put("response","Something bad happened!Try again!");
        }
        model.put("cashier",service.cashierReport());
        return "adminCashierPage";

    }

}
