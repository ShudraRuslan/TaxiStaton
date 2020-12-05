package com.example.demo.Services.MainClasses.OrderInfo;

import com.example.demo.Services.MainClasses.Roles.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;


@Entity
@EnableAutoConfiguration
public final class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private int amountOfPassengers;
    private double distance;
    private Long driverId;
    private Long carId;
    private OrderStatus status;
    private Long clientId;
    private Date date;


    public Orders() {
    }

    public Orders(int amountOfPassengers, double distance, User client) {
        this.amountOfPassengers = amountOfPassengers;
        this.distance = distance;
        this.clientId = client.getId();
        this.status = OrderStatus.isPreparing;
        long mills = System.currentTimeMillis();
        date = new Date(mills);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmountOfPassengers(int amountOfPassengers) {
        this.amountOfPassengers = amountOfPassengers;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getAmountOfPassengers() {
        return amountOfPassengers;
    }

    public double getDistance() {
        return distance;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long client) {
        this.clientId = client;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", amountOfPassengers=" + amountOfPassengers +
                ", distance=" + distance +
                ", username=" + clientId +
                ", driverId=" + driverId +
                ", carId=" + carId +
                ", status=" + status +
                '}';
    }
}


