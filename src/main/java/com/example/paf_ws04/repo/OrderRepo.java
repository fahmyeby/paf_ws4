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

    public static final String createOrder = "INSERT INTO orders (customer_name, ship_address, notes, tax, order_date) VALUES (?, ?, ?, ?, ?)";
    public static final String createOrderDetails = "INSERT INTO order_details (order_id, product, unit_price, discount, quantity) VALUES (?, ?, ?, ?, ?)";
    public static final String getOrders = "SELECT * FROM orders";
    public static final String getOrderById = "SELECT * FROM orders WHERE order_id = ?";
    public static final String getOrderDetailsById = "SELECT * FROM order_details WHERE order_id = ?";

    // insert order and return generated id
    public Integer insertOrder(Order order) {
        KeyHolder kh = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(createOrder, new String[] { "order_id" });
                ps.setString(1, order.getCustomerName());
                ps.setString(2, order.getShipAddress());
                ps.setString(3, order.getNotes());
                ps.setFloat(4, order.getTax());
                ps.setDate(5, Date.valueOf(order.getOrderDate()));
                return ps;
            }
        };
        template.update(psc, kh);
        return kh.getKey() != null ? kh.getKey().intValue() : null;
    }

    // insert order details
    public Boolean insertOrderDetails(Integer orderId, List<OrderDetail> details) {
        for (OrderDetail detail : details) {
            int rowsInserted = template.update(createOrderDetails,
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

    // get all orders
    public List<Order> getAllOrders() {
        return template.query(getOrders, BeanPropertyRowMapper.newInstance(Order.class));
    }

    // get order by id
    public Order getOrderById(Integer orderId) {
        return template.queryForObject(getOrderById, BeanPropertyRowMapper.newInstance(Order.class), orderId);
    }

    // get order details by id
    public List<OrderDetail> getOrderDetailsById(Integer orderId){
        return template.query(getOrderDetailsById, BeanPropertyRowMapper.newInstance(OrderDetail.class), orderId);
    }
}
