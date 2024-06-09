--@block
CREATE TABLE categories (
    category_id CHAR(4) PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);
--@block
CREATE TABLE suppliers(
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL
);
--@block
CREATE TABLE product_types (
    product_type_id CHAR(1) PRIMARY KEY,
    product_type_name VARCHAR(100) NOT NULL
);
--@block
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_code VARCHAR(50) NOT NULL,
    barcode VARCHAR(50) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    product_price DECIMAL(10, 2) NOT NULL,
    product_size VARCHAR(50) NOT NULL,
    category_id CHAR(4) NOT NULL,
    supplier_id INT NOT NULL,
    product_type_id CHAR(1) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (product_type_id) REFERENCES product_types(product_type_id)
);
--@block
-- correction in suppliers table
ALTER TABLE suppliers
MODIFY COLUMN supplier_id INT;
--@block
CREATE TABLE discounts (
    discounts_type_id INT PRIMARY KEY,
    discount_type VARCHAR(100) NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL
);
--@block
SHOW TABLES;