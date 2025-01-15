package com.example.paf_ws04.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.paf_ws04.model.Order;
import com.example.paf_ws04.model.OrderDetail;
import com.example.paf_ws04.repo.OrderRepo;

@Service
public class OrderService {
    @Autowired
    private OrderRepo repo;

    public Integer createOrder(Order order){
        Integer orderId = repo.insertOrder(order);
        if (orderId != null){
            Boolean detailsInserted = repo.insertOrderDetails(orderId, order.getOrderDetails());
            if(!detailsInserted){
                throw new RuntimeException("Failed to insert order details");
            }
        } else {
            throw new RuntimeException("Failed to insert order");
        } return orderId;
    }

    public List<Order> getAllOrders(){
        return repo.getAllOrders();
    }

    public Order getOrderById(Integer orderId){
        Order order = repo.getOrderById(orderId);
        List<OrderDetail> details = repo.getOrderDetailsById(orderId);
        order.setOrderDetails(details);
        return order;
    }
}
