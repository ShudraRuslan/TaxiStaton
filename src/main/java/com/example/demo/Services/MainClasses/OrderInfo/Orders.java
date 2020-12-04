package com.example.demo.Services.MainClasses.OrderInfo;

import com.example.demo.Services.MainClasses.Roles.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity
@EnableAutoConfiguration
public final class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private int amountOfPassengers;
    private double distance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    private Long driverId;
    private Long carId;
    private OrderStatus status;

    public Orders() {
    }

    public Orders(int amountOfPassengers, double distance, User client) {
        this.amountOfPassengers = amountOfPassengers;
        this.distance = distance;
        this.client=client;
        this.status = OrderStatus.isPreparing;

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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
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
                ", username=" + client.getUsername() +
                ", driverId=" + driverId +
                ", carId=" + carId +
                ", status=" + status +
                '}';
    }
}


