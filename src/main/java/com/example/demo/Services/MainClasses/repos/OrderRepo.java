package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Orders, Long> {
    Orders getOrderByOrderId(Long orderId);

    List<Orders> getAllOrdersByStatus(OrderStatus status);

    @Query("SELECT c.id FROM Cashier c WHERE c.orderId=?1")
    Long getCashierIdFromOrder(Long id);

    @Query("SELECT COUNT(o.orderId ) FROM Orders o WHERE o.status=2")
    int numberOfCancelledOrders();

    @Query("SELECT COUNT(o.orderId ) FROM Orders o WHERE o.status=1")
    int numberOfCompletedOrders();


}
