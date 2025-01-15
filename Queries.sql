-- queries

-- Table: orders
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATE NOT NULL,
    customer_name VARCHAR(128) NOT NULL,
    ship_address VARCHAR(128),
    notes TEXT,
    tax DECIMAL(5,2) DEFAULT 0.05
);

-- Table: order_details
CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product VARCHAR(64) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 1.00,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

describe orders;

insert into orders (
	customer_name, ship_address,
	notes, tax, order_date 
) values (
	'Alan Wong', '500 Address Ave',
	'Test 2', 6.00, '2025-02-01'
)

insert into order_details (
	order_id, product, unit_price, discount, quantity
) values (
	1, 'Laptop', 3589.99, 5.00, 1
)
select * from orders

select * from order_details

describe order_details 
