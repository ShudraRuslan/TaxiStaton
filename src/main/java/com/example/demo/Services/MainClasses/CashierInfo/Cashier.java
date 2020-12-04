package com.example.demo.Services.MainClasses.CashierInfo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Cashier {

    @Id
    private String nameOfTheStation;
    private boolean isOpen;
    private double bookingCash;
    private double carServiceCash;
    private double driverSalaryCash;
    private double fuelCash;
    private double balance;


    public Cashier() {
        isOpen = true;
    }

    public Cashier(String name) {
        this.nameOfTheStation = name;
        isOpen = true;
    }


    public String getName() {
        return nameOfTheStation;
    }


    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        isOpen = true;
    }

    public double getBookingCash() {
        return bookingCash;
    }

    public double getCarServiceCash() {
        return carServiceCash;
    }

    public double getDriverSalaryCash() {
        return driverSalaryCash;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFuelCash() {
        return fuelCash;
    }

    public void setBookingCash(double cash) {
        bookingCash = cash;
    }

    public void setCarServiceCash(double cash) {
        carServiceCash = cash;
    }

    public void setDriverSalaryCash(double cash) {
        driverSalaryCash = cash;
    }

    public void setFuelCash(double cash) {
        fuelCash = cash;
    }

    @Override
    public String toString() {
        return "bookingCash = " + bookingCash + "\n" + "carServiceCash= "
                + carServiceCash + "\n" + "driverSalaryCash= " + driverSalaryCash + "\n" +
                "fuelCash= " + fuelCash + "\n" +
                "balance= " + balance;
    }
}

