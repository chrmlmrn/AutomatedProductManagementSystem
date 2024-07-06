--@block
CREATE TABLE user_level_of_access (
    user_role_id CHAR(1) PRIMARY KEY NOT NULL,
    user_role_name VARCHAR(50) NOT NULL
);
--@block
INSERT INTO user_level_of_access (user_role_id, user_role_name)
VALUES ('A', 'Admin'),
    ('C', 'Cashier');
--@block
CREATE TABLE user_account_status (
    user_account_status_id CHAR(3) PRIMARY KEY NOT NULL,
    account_status VARCHAR(50) NOT NULL
);
--@block
INSERT INTO user_account_status (user_account_status_id, account_status)
VALUES ('ACT', 'Active'),
    ('INA', 'Inactive');
--@block
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    unique_user_id VARCHAR(20) NOT NULL UNIQUE,
    user_role_id CHAR(1),
    user_first_name VARCHAR(50) NOT NULL,
    user_last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_account_status_id CHAR(3) DEFAULT 'ACT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_role_id) REFERENCES user_level_of_access(user_role_id),
    FOREIGN KEY (user_account_status_id) REFERENCES user_account_status(user_account_status_id)
);
--@block
CREATE TABLE user_logs (
    user_log_id INT AUTO_INCREMENT PRIMARY KEY,
    unique_user_id VARCHAR(20),
    user_action VARCHAR(100),
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unique_user_id) REFERENCES users(unique_user_id)
);
--@block
CREATE TABLE report_type (
    report_type_id INT AUTO_INCREMENT PRIMARY KEY,
    report_type VARCHAR(100) NOT NULL
);
--@block
INSERT INTO report_type(report_type)
VALUES ('Sales Report'),
    ('Inventory Report'),
    ('Return Report');
--@block
CREATE TABLE reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_type_id INT,
    report_date DATE,
    unique_user_id VARCHAR(20),
    file_name VARCHAR(255),
    file_data LONGBLOB,
    FOREIGN KEY (report_type_id) REFERENCES report_type(report_type_id),
    FOREIGN KEY (unique_user_id) REFERENCES users(unique_user_id)
);
--@block
CREATE TABLE security_question (
    security_question_id INT AUTO_INCREMENT PRIMARY KEY,
    security_question VARCHAR(255) UNIQUE
);
--@block
INSERT INTO security_question (security_question)
VALUES ('What is your mother''s maiden name?'),
    ('What is the name of your first pet?'),
    ('In what city were you born?'),
    ('What is your favorite movie?'),
    (
        'In what year did you graduate from high school?'
    ),
    ('What was the name of your first school?');
--@block
CREATE TABLE security_answer (
    security_answer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    security_question_id INT,
    security_answer_hash VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (security_question_id) REFERENCES security_question(security_question_id)
);
--@block
CREATE TABLE product_type (
    product_type_id CHAR(1) PRIMARY KEY,
    product_type_name VARCHAR(50) NOT NULL
);
--@block
INSERT INTO product_type (product_type_id, product_type_name)
VALUES ('F', 'Fast'),
    ('S', 'Slow');
--@block
CREATE TABLE supplier (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(255) NOT NULL
);
--@block
CREATE TABLE category (
    category_id CHAR(2) PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);
--@block
INSERT INTO category (category_id, category_name)
VALUES ('FR', 'Fruits'),
    ('VE', 'Vegetables'),
    ('DA', 'Dairy'),
    ('RP', 'Rice and Pasta'),
    ('CG', 'Canned Goods'),
    ('FF', 'Frozen Foods'),
    ('CO', 'Condiments'),
    ('SN', 'Snacks'),
    ('DR', 'Drinks'),
    ('HH', 'Household'),
    ('PI', 'Personal Items'),
    ('OT', 'Other');
--@block
CREATE TABLE product_status (
    product_status_id CHAR(3) PRIMARY KEY,
    product_status_name VARCHAR(50)
);
--@block
INSERT INTO product_status (product_status_id, product_status_name)
VALUES ('ACT', 'Active'),
    ('INA', 'Inactive'),
    ('DIS', 'Discontinued');
--@block
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_code VARCHAR(20) NOT NULL UNIQUE,
    barcode VARCHAR(50) NOT NULL,
    barcode_image BLOB,
    product_name VARCHAR(255) NOT NULL,
    product_price DECIMAL(10, 2) NOT NULL,
    product_size VARCHAR(50),
    category_id CHAR(2) NOT NULL,
    supplier_id INT NOT NULL,
    product_type_id CHAR(1) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_status_id CHAR(3) NOT NULL DEFAULT 'ACT',
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (product_type_id) REFERENCES product_type(product_type_id),
    FOREIGN KEY (product_status_id) REFERENCES product_status(product_status_id)
);
--@block
CREATE TABLE product_inventory_status (
    product_inventory_status_id CHAR(3) PRIMARY KEY,
    product_inventory_status_name VARCHAR(50)
);
--@block
INSERT INTO product_inventory_status (
        product_inventory_status_id,
        product_inventory_status_name
    )
VALUES ('OOS', 'Out of Stock'),
    ('INS', 'In Stock'),
    ('ROG', 'Reordering');
--@block
CREATE TABLE inventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    product_total_quantity INT NOT NULL,
    critical_stock_level INT NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    product_inventory_status_id CHAR(3) NOT NULL DEFAULT 'INS',
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (product_inventory_status_id) REFERENCES product_inventory_status(product_inventory_status_id)
);
--@block
CREATE TABLE product_expiration (
    product_expiration_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    product_expiration_date DATE,
    product_quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
--@block
CREATE TABLE return_reason (
    return_reason_id CHAR(3) PRIMARY KEY,
    return_reason_name VARCHAR(50) NOT NULL
);
--@block
INSERT INTO return_reason (return_reason_id, return_reason_name)
VALUES ('DEF', 'Defective Product'),
    ('WRO', 'Wrong Product'),
    ('EXP', 'Expired Product');
--@block
CREATE TABLE return_products (
    return_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    return_quantity INT NOT NULL,
    return_reason_id CHAR(3) NOT NULL,
    return_date DATE NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (return_reason_id) REFERENCES return_reason(return_reason_id)
);
--@block
CREATE TABLE discount (
    discount_type_id CHAR(3) PRIMARY KEY,
    discount_type VARCHAR(50) NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL
);
--@block
INSERT INTO discount (discount_type_id, discount_type, discount_value)
VALUES ('SCN', 'Senior Citizen', 5.00),
    ('PWD', 'Person with Disability', 5.00);
--@block
SELECT DATE_FORMAT(last_updated, '%Y-%m-%d') AS formatted_date
FROM inventory;
--@block
SELECT DATE_FORMAT(product_expiration_date, '%Y-%m-%d') AS formatted_date
FROM product_expiration;
--@block
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    receipt_number VARCHAR(50),
    reference_number VARCHAR(50),
    date DATE,
    time TIME,
    subtotal DOUBLE,
    discount DOUBLE,
    vat DOUBLE,
    total DOUBLE
);
--@block
CREATE TABLE sales_summary (
    sale_date DATE PRIMARY KEY,
    hours_open INT NOT NULL,
    hours_closed INT NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    total_discount DECIMAL(10, 2) NOT NULL,
    total_sales DECIMAL(10, 2) NOT NULL
);
--@block
CREATE TABLE sold_products (
    sold_product_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);