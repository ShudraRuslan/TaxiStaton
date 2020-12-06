package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Orders, Long> {
    Orders getOrderByOrderId(Long orderId);

    List<Orders> getAllOrdersByStatus(OrderStatus status);

    List<Orders> getAllOrdersByDriverId(Long driverID);

    List<Orders> getAllOrdersByCarId(Long carID);

    @Query("SELECT COUNT(o.orderId ) FROM Orders o WHERE o.status=2")
    int numberOfCancelledOrders();

    @Query("SELECT COUNT(o.orderId ) FROM Orders o WHERE o.status=1")
    int numberOfCompletedOrders();


}
