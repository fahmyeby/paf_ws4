package com.example.paf_ws04.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.paf_ws04.model.Order;
import com.example.paf_ws04.model.OrderDetail;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate template;

    private static final String CREATE_ORDER_SQL = "INSERT INTO orders (customer_name, ship_address, notes, tax, order_date) VALUES (?, ?, ?, ?, ?)";
    private static final String CREATE_ORDER_DETAILS_SQL = "INSERT INTO order_details (order_id, product, unit_price, discount, quantity) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_ORDERS_SQL = "SELECT * FROM orders";
    private static final String GET_ORDER_BY_ID_SQL = "SELECT * FROM orders WHERE order_id = ?";
    private static final String GET_ORDER_DETAILS_BY_ID_SQL = "SELECT * FROM order_details WHERE order_id = ?";

    // Insert order and return generated ID
    public Integer insertOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_ORDER_SQL, new String[] { "order_id" });
            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getShipAddress());
            ps.setString(3, order.getNotes());
            ps.setFloat(4, order.getTax());
            ps.setDate(5, Date.valueOf(order.getOrderDate()));
            return ps;
        };
        template.update(psc, keyHolder);
        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    // Insert order details
    public Boolean insertOrderDetails(Integer orderId, List<OrderDetail> details) {
        for (OrderDetail detail : details) {
            int rowsInserted = template.update(CREATE_ORDER_DETAILS_SQL,
                    orderId,
                    detail.getProduct(),
                    detail.getUnitPrice(),
                    detail.getDiscount(),
                    detail.getQuantity());
            if (rowsInserted <= 0) {
                return false;
            }
        }
        return true;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return template.query(GET_ALL_ORDERS_SQL, BeanPropertyRowMapper.newInstance(Order.class));
    }

    // Get order by ID
    public Order getOrderById(Integer orderId) {
        return template.queryForObject(GET_ORDER_BY_ID_SQL, BeanPropertyRowMapper.newInstance(Order.class), orderId);
    }

    // Get order details by order ID
    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        return template.query(GET_ORDER_DETAILS_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(OrderDetail.class),
                orderId);
    }
}
