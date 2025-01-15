package com.example.paf_ws04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paf_ws04.model.Order;
import com.example.paf_ws04.model.exception.InvalidOrderException;
import com.example.paf_ws04.model.exception.OrderAlreadyExistsException;
import com.example.paf_ws04.model.exception.OrderDetailsNotFoundException;
import com.example.paf_ws04.model.exception.OrderNotFoundException;
import com.example.paf_ws04.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            if (order.getCustomerName() == null || order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) {
                throw new InvalidOrderException("Invalid order input. Customer name and order details are required.");
            }

            Integer orderId = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully with ID: " + orderId);
        } catch (OrderAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (InvalidOrderException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + ex.getMessage());
        }
    }

    // Retrieve all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                throw new OrderDetailsNotFoundException("No orders found.");
            }
            return ResponseEntity.ok(orders);
        } catch (OrderDetailsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Retrieve a single order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) {
                throw new OrderDetailsNotFoundException("Order details not found for order ID: " + orderId);
            }
            return ResponseEntity.ok(order);
        } catch (OrderDetailsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving order: " + ex.getMessage());
        }
    }
}

