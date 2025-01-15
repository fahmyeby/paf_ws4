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
import com.example.paf_ws04.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
    @Autowired
    private OrderService service;

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = service.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") Integer orderId){
        try {
            Order order = service.getOrderById(orderId);
            return ResponseEntity.ok().body(order);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        try {
            Integer orderId = service.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order successfully created with ID: " + orderId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }   
}
