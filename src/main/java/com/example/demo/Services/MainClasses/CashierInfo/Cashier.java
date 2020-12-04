package com.example.demo.Services.MainClasses.CashierInfo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Cashier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private double bookingCash;
    private double carServiceCash;
    private double driverSalaryCash;
    private double fuelCash;
    private double balance;


    public Cashier(){
    }

    public Cashier(Long orderId){
        this.orderId=orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
        return "Cashier{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", bookingCash=" + bookingCash +
                ", carServiceCash=" + carServiceCash +
                ", driverSalaryCash=" + driverSalaryCash +
                ", fuelCash=" + fuelCash +
                ", balance=" + balance +
                '}';
    }
}

