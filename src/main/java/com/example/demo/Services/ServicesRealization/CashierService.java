package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
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
        double additionFuelLoses = distance * consumption;
        double newFuelLoses = cashier.getFuelCash() + additionFuelLoses;
        cashier.setFuelCash(newFuelLoses);
        repos.save(cashier);
    }

    public double getCurrentBookingCash(double distance, int amountOfPassengers, boolean isVipClient) {
        if (isVipClient)

            return (15 * distance + 10 * amountOfPassengers) * 0.2;

        else
            return 15 * distance + 10 * amountOfPassengers;
    }

    public void changeBookingCash(Long orderId, double distance, int amountOfPassengers, boolean isVipClient) {

        Cashier cashier = repos.getByOrderId(orderId);
        double additionOrderPrice = getCurrentBookingCash(distance, amountOfPassengers, isVipClient);
        double newBookingCash = cashier.getBookingCash() + additionOrderPrice;
        cashier.setBookingCash(newBookingCash);
        repos.save(cashier);
    }

    public void changeCarServiceCash(Long orderId) {

        Cashier cashier = repos.getByOrderId(orderId);
        double newServiceCash = cashier.getCarServiceCash() + 4000;
        cashier.setCarServiceCash(newServiceCash);
        repos.save(cashier);
    }

    public void changeDriverSalaryCash(Long orderId, double salary) {

        Cashier cashier = repos.getByOrderId(orderId);
        double newDriverSalaryCash = cashier.getDriverSalaryCash() + salary;
        cashier.setDriverSalaryCash(newDriverSalaryCash);
        repos.save(cashier);
    }

    public void countCashierBalance(Long orderId) {

        Cashier cashier = repos.getByOrderId(orderId);
        double balance = cashier.getBookingCash() - cashier.getCarServiceCash() -
                cashier.getDriverSalaryCash() - cashier.getFuelCash();

        cashier.setBalance(balance);
        repos.save(cashier);
    }

    public void updateCashier(Long orderId, double distance, double consumption, int amountOfPassengers,
                              double salary, boolean needsService, boolean isVip) {

        changeFuelLoses(orderId, distance, consumption);
        changeDriverSalaryCash(orderId, salary);
        changeBookingCash(orderId, distance, amountOfPassengers, isVip);
        if (needsService)

            changeCarServiceCash(orderId);

        countCashierBalance(orderId);
    }

    public Cashier cashierReport(Long orderId) {

        return repos.getByOrderId(orderId);
    }

    public Map<String, Double> fullCashierReport() {
        return repos.getFullReport();
    }

    private void deleteOperation(List<Cashier> list) {
        if (list.size() == 0) return;
        int iterator = 0;
        int iterationSize = list.size();

        for (iterator = 0; iterator < iterationSize; iterator++) {

            repos.delete(list.get(iterator));
        }
    }

        public void cleanCashier(){
            List<Cashier> cashiers = (List<Cashier>) repos.findAll();
            deleteOperation(cashiers);
        }


    }






