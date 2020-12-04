package com.example.demo.Services.ServicesRealization;

import com.example.demo.Services.MainClasses.CashierInfo.Cashier;
import com.example.demo.Services.MainClasses.repos.CashierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CashierService {

    private final CashierRepo repos;


    @Autowired
    public CashierService(CashierRepo repos) {
        this.repos = repos;
        createCashier();
    }

    public void createCashier() {
        if (repos.getByNameOfTheStation("FirstStation") == null) {
            Cashier cashier = new Cashier("FirstStation");
            repos.save(cashier);
        }
    }

    public void changeFuelLoses(double distance, double consumption) {
        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
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

    public void changeBookingCash(double distance, int amountOfPassengers, boolean isVipClient) {

        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
        double additionOrderPrice = getCurrentBookingCash(distance, amountOfPassengers, isVipClient);
        double newBookingCash = cashier.getBookingCash() + additionOrderPrice;
        cashier.setBookingCash(newBookingCash);
        repos.save(cashier);
    }

    public void changeCarServiceCash() {

        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
        double newServiceCash = cashier.getCarServiceCash() + 4000;
        cashier.setCarServiceCash(newServiceCash);
        repos.save(cashier);
    }

    public void changeDriverSalaryCash(double salary) {

        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
        double newDriverSalaryCash = cashier.getDriverSalaryCash() + salary;
        cashier.setDriverSalaryCash(newDriverSalaryCash);
        repos.save(cashier);
    }

    public void countCashierBalance() {

        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
        double additionBalance = cashier.getBookingCash() - cashier.getCarServiceCash() -
                cashier.getDriverSalaryCash() - cashier.getFuelCash();

        double balance = cashier.getBalance() + additionBalance;
        cashier.setBalance(balance);
        repos.save(cashier);
    }

    public void updateCashier(double distance, double consumption, int amountOfPassengers,
                              double salary, boolean needsService, boolean isVip) {
        Cashier cashier = repos.getByNameOfTheStation("FirstStation");

        changeFuelLoses(distance, consumption);
        changeDriverSalaryCash(salary);
        changeBookingCash(distance, amountOfPassengers, isVip);
        if (needsService)

            changeCarServiceCash();

        countCashierBalance();
    }

    public Cashier cashierReport() {

        return repos.getByNameOfTheStation("FirstStation");
    }

    public boolean changeCashier(double bookingCash,
                                 double driverSalaryCash,
                                 double carServiceCash,
                                 double fuelCash) {
        Cashier cashier = repos.getByNameOfTheStation("FirstStation");
        try {
            cashier.setBookingCash(cashier.getBookingCash() + bookingCash);
            cashier.setCarServiceCash(cashier.getCarServiceCash() + carServiceCash);
            cashier.setDriverSalaryCash(cashier.getDriverSalaryCash() + driverSalaryCash);
            cashier.setFuelCash(cashier.getFuelCash() + fuelCash);
            repos.save(cashier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
