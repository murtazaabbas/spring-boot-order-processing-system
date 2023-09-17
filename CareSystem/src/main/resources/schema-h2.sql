CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    trace_id VARCHAR(255),
    installation_address VARCHAR(255),
    installation_date_time TIMESTAMP NOT NULL,
    approve INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS personal_information (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    national_id VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    package VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);