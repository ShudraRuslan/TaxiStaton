package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import com.example.demo.Services.MainClasses.DriverInfo.Category;
import com.example.demo.Services.MainClasses.repos.CashierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Service
public class CashierService {

    private final CashierRepo repos;


    @Autowired
    public CashierService(CashierRepo repos) throws SQLException {
        this.repos = repos;

    }


    public void createCashier(Long orderId) {
        Cashier cashier = new Cashier(orderId);
        repos.save(cashier);
    }


    public void changeFuelLoses(Long orderId, double distance, double consumption) {
        Cashier cashier = repos.getByOrderId(orderId);
        double fuelPrice = 0.2;
        double fuelLoses = distance * consumption * fuelPrice;
        cashier.setFuelCash(fuelLoses);
        repos.save(cashier);
    }

    public double getCurrentBookingCash(double distance, int amountOfPassengers, boolean isVipClient) {
        if (isVipClient)

            return (15 * distance + 10 * amountOfPassengers) * 0.2;

        else
            return 15 * distance + 10 * amountOfPassengers;
    }

    private void changeBookingCash(Long orderId, double distance, int amountOfPassengers, boolean isVipClient) {

        Cashier cashier = repos.getByOrderId(orderId);
        double orderPrice = getCurrentBookingCash(distance, amountOfPassengers, isVipClient);
        cashier.setBookingCash(orderPrice);
        repos.save(cashier);
    }

    private void changeCarServiceCash(Long orderId) {

        Cashier cashier = repos.getByOrderId(orderId);
        double carServicePrice = 3000;
        cashier.setCarServiceCash(carServicePrice);
        repos.save(cashier);
    }

    private void changeDriverSalaryCash(Long orderId, double distance, int amountOfPassengers, Category category) {

        Cashier cashier = repos.getByOrderId(orderId);
        double orderSalary = (amountOfPassengers * 5 + distance) * category.ordinal();
        cashier.setDriverSalaryCash(orderSalary);
        repos.save(cashier);
    }

    private void countCashierBalance(Long orderId) {

        Cashier cashier = repos.getByOrderId(orderId);
        double balance = cashier.getBookingCash() - cashier.getCarServiceCash() -
                cashier.getDriverSalaryCash() - cashier.getFuelCash();

        cashier.setBalance(balance);
        repos.save(cashier);
    }

    public void updateCashier(Long orderId, double distance, double consumption, int amountOfPassengers,
                              Category driverCategory, boolean needsService, boolean isVip) {

        changeFuelLoses(orderId, distance, consumption);
        changeDriverSalaryCash(orderId, distance, amountOfPassengers, driverCategory);
        changeBookingCash(orderId, distance, amountOfPassengers, isVip);
        if (needsService)

            changeCarServiceCash(orderId);

        countCashierBalance(orderId);
    }

    public double getDriverSalary(Long id) {
        return repos.gerFullDriverSalary(id);
    }

    public Cashier cashierReport(Long orderId) {

        return repos.getByOrderId(orderId);
    }

    public Map<String, Double> fullCashierReport() {
        return repos.getFullReport();
    }

    public Map<String, Double> dailyCashierReport() {
        return repos.getDailyReport();
    }

    private void deleteOperation(List<Cashier> list) {
        if (list.size() == 0) return;
        int iterator = 0;
        int iterationSize = list.size();

        for (iterator = 0; iterator < iterationSize; iterator++) {

            repos.delete(list.get(iterator));
        }
    }

    public void cleanCashierFromCancelledOrders() {
        try {
            List<Long> idList = repos.getIdsForCashOfCancelledOrders();
            for (Long aLong : idList) {
                Cashier cashier = repos.getById(aLong);
                repos.delete(cashier);
            }
        } catch (Exception ignored) {
        }
    }

    public void cleanCashier() {
        List<Cashier> cashiers = (List<Cashier>) repos.findAll();
        deleteOperation(cashiers);
    }

    public Long getCashierIdFromOrder(Long orderId) {
        Long id;
        try {
            id = repos.getCashierIdFromOrder(orderId);
            Cashier result = repos.getById(id);
            if (result != null)
                return id;
            else return 0L;
        } catch (Exception e) {
            return 0L;
        }

    }


}






