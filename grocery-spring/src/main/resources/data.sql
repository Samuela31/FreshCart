INSERT INTO customers (name, email, phone, password) VALUES
('Aria', 'aria@gmail.com', '9876543210', '$2a$10$slsBrN9Q1UPTmLPTqna99eHRFfhjM78WIDwgn.BSSUJDTFCGjl6dq'),
('Sam', 'sam@gmail.com', '9123456780', '$2a$10$G4AlaS/KiuCcKPmW1Ef96eUN7FDPCWpdLMmhCiPVS6OynP3FtlSUi');


INSERT INTO products (product_name, description, price, quantity, category, image_url) VALUES
('Fresh Milk', '1 litre of full cream milk', 60.00, 100, 'Dairy Items', 'https://images.unsplash.com/photo-1563636619-e9143da7973b?'),
('Brown Bread', 'Whole wheat bread loaf', 40.00, 5, 'Dairy Items', 'https://images.unsplash.com/photo-1611871679274-5242ed4c3cd0?'),
('Brown Eggs', 'Pack of 6 farm-fresh eggs', 75.00, 80, 'Dairy Items', 'https://images.unsplash.com/photo-1652847504592-d0c4cfe0a6c7?'),
('Basmati Rice', '1 kg premium basmati rice', 120.00, 40, 'Provisions', 'https://images.unsplash.com/photo-1643622357625-c013987d90e7?'),
('Imported Apples', '1 kg of fresh red apples', 150.00, 60, 'Fruits', 'https://images.unsplash.com/photo-1666164037865-1174c019e207?'),
('Coriander Leaves', 'A fresh bunch of 100gms', 15.50, 0, 'Vegetables', 'https://images.unsplash.com/photo-1535189487909-a262ad10c165?');

INSERT INTO products (product_name, description, price, quantity, category, image_url) VALUES
('Orange Juice', '500 ml freshly squeezed orange juice', 80.00, 70, 'Beverages', 'https://images.unsplash.com/photo-1618046364546-81e9d03d39a6?'),
('Cheddar Cheese', '200g block of aged cheddar cheese', 120.00, 30, 'Dairy Items', 'https://images.unsplash.com/photo-1683314573422-649a3c6ad784?'),
('Bananas', '1 dozen ripe bananas', 60.00, 90, 'Fruits', 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?'),
('Tomatoes', '1 kg of fresh red tomatoes', 45.00, 50, 'Vegetables', 'https://images.unsplash.com/photo-1444731961956-751ed90465a5?'),
('Olive Oil', '500 ml extra virgin olive oil', 250.00, 25, 'Provisions', 'https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?');

INSERT INTO payments (cardholder_name, amount, payment_status) VALUES 
('Alice Hart', 100.01, 'SUCCESS'),
('Mary S', 130.21, 'SUCCESS'),
('Jass Hart', 100.01, 'SUCCESS'),
('Mary Sue', 130.21, 'SUCCESS'),
('Sora .A', 10.00, 'SUCCESS');

INSERT INTO orders (customer_id, payment_id, delivery_address, total_amount, order_status) VALUES
(1, 1, '123 Park Street, Delhi', 180.00, 'Processing'),
(2, 2, '45 MG Road, Mumbai', 200.00, 'Pending'),
(1, 3, '78 Lake View, Chennai', 360.00, 'Delivered'),
(2, 4, '12 Hilltop Colony, Pune', 120.00, 'Processing'),
(1, 5, '9A Green Lane, Bangalore', 300.00, 'Pending');


-- Only JPA Entity gonna calculate subtotal automatically, not your direct insert query in H2
INSERT INTO order_items (order_id, product_id, quantity, product_price, subtotal) VALUES
(1, 1, 2, 60.00, 120.00),
(2, 2, 1, 40.00, 40.00),
(3, 3, 2, 75.00, 150.00),
(5, 4, 3, 120.00, 360.00),
(4, 5, 2, 150.00, 300.00);


--Spring security
INSERT INTO role_details (role_name) VALUES ('ADMIN');
INSERT INTO role_details (role_name) VALUES ('CUSTOMER');


--USE BCRYPT GENERATOR TO STORE HASHED PASSWORDS
--WHEN GIVING CREDENTIALS IN SWAGGER USE ACTUAL PASSWORD NOT ENCRYPTED PASSWORDS
--PASSWORD: password@123
INSERT INTO user_details (email, password)
VALUES ('aria@gmail.com', '$2a$10$slsBrN9Q1UPTmLPTqna99eHRFfhjM78WIDwgn.BSSUJDTFCGjl6dq');

--PASSWORD: password@user
INSERT INTO user_details (email, password)
VALUES ('sam@gmail.com', '$2a$10$G4AlaS/KiuCcKPmW1Ef96eUN7FDPCWpdLMmhCiPVS6OynP3FtlSUi');

--ADMIN ROLE
--change ID according to what is generated when inserting previous rows
INSERT INTO user_info_roles_details (id, role_id) VALUES (1,1);
INSERT INTO user_info_roles_details (id, role_id) VALUES (2,2);





-- 1 active promotion
INSERT INTO category_promotions (category, start_date, end_date, discount_percentage, is_active)
VALUES ('Fruits', '2025-10-25 00:00:00', '2025-11-05 23:59:59', 10.0, TRUE);

-- 3 stopped/inactive promotions
INSERT INTO category_promotions (category, start_date, end_date, discount_percentage, is_active)
VALUES ('Vegetables', '2025-09-01 00:00:00', '2025-09-10 23:59:59', 15.0, FALSE);

INSERT INTO category_promotions (category, start_date, end_date, discount_percentage, is_active)
VALUES ('Dairy', '2025-08-15 00:00:00', '2025-08-25 23:59:59', 5.0, FALSE);

INSERT INTO category_promotions (category, start_date, end_date, discount_percentage, is_active)
VALUES ('Snacks', '2025-07-01 00:00:00', '2025-07-10 23:59:59', 20.0, FALSE);




