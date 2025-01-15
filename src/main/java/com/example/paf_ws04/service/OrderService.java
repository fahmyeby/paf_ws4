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
    private OrderRepo orderRepo;

    // Create a new order
    public Integer createOrder(Order order) {
        Integer orderId = orderRepo.insertOrder(order);
        if (orderId != null) {
            boolean detailsInserted = orderRepo.insertOrderDetails(orderId, order.getOrderDetails());
            if (!detailsInserted) {
                throw new RuntimeException("Failed to insert order details");
            }
        } else {
            throw new RuntimeException("Failed to insert order");
        }
        return orderId;
    }

    // Retrieve all orders
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepo.getAllOrders();
        for (Order order : orders) {
            List<OrderDetail> details = orderRepo.getOrderDetailsByOrderId(order.getOrderId());
            order.setOrderDetails(details);
        }
        return orders;
    }

    // Retrieve a single order by ID
    public Order getOrderById(Integer orderId) {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " not found");
        }
        List<OrderDetail> details = orderRepo.getOrderDetailsByOrderId(orderId);
        order.setOrderDetails(details);
        return order;
    }
}

