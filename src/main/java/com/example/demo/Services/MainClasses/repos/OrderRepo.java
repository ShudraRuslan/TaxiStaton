package com.example.demo.Services.MainClasses.repos;

import com.example.demo.Services.MainClasses.OrderInfo.OrderStatus;
import com.example.demo.Services.MainClasses.OrderInfo.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Orders, Long> {
    Orders getOrderByOrderId(Long orderId);

    List<Orders> getAllOrdersByClientId(Long clientId);

    List<Orders> getAllOrdersByCarId(Long carId);

    List<Orders> getAllOrdersByDriverId(Long driverId);

    List<Orders> getAllOrdersByStatus(OrderStatus status);


}
