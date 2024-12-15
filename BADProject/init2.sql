-- -- Drop existing tables if they exist
-- DROP TABLE IF EXISTS transaction_detail;
-- DROP TABLE IF EXISTS transaction_header;
-- DROP TABLE IF EXISTS mscart;
-- DROP TABLE IF EXISTS msitem;
-- DROP TABLE IF EXISTS msuser;

-- Create tables
CREATE TABLE msuser (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    UserEmail VARCHAR(100) NOT NULL,
    UserPassword VARCHAR(100) NOT NULL,
    UserRole VARCHAR(20) NOT NULL,
    UserGender VARCHAR(10) NOT NULL,
    UserDOB DATE NOT NULL
);

CREATE TABLE msitem (
    ItemID INT PRIMARY KEY AUTO_INCREMENT,
    ItemName VARCHAR(70) NOT NULL,
    ItemPrice DECIMAL(10,2) NOT NULL,
    ItemDesc VARCHAR(255) NOT NULL,
    ItemCategory VARCHAR(50) NOT NULL,
    ItemStock INT NOT NULL
);

CREATE TABLE mscart (
    UserID INT,
    ItemID INT,
    Quantity INT NOT NULL,
    ItemName VARCHAR(70) NOT NULL,
    ItemPrice DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES msuser(UserID),
    FOREIGN KEY (ItemID) REFERENCES msitem(ItemID)
);

CREATE TABLE transaction_header (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    TransactionDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(20) DEFAULT 'In Queue',
    FOREIGN KEY (UserID) REFERENCES msuser(UserID)
);

CREATE TABLE transaction_detail (
    TransactionID INT,
    ItemID INT,
    Quantity INT NOT NULL,
    FOREIGN KEY (TransactionID) REFERENCES transaction_header(TransactionID),
    FOREIGN KEY (ItemID) REFERENCES msitem(ItemID)
);

-- Insert sample users
INSERT INTO msuser (UserEmail, UserPassword, UserRole, UserGender, UserDOB) VALUES
('admin@gomail.com', 'Admin123', 'Manager', 'Male', '1990-01-01'),
('john@gomail.com', 'John123', 'Shopper', 'Male', '1995-05-15'),
('sarah@gomail.com', 'Sarah123', 'Shopper', 'Female', '1992-08-22'),
('mike@gomail.com', 'Mike123', 'Shopper', 'Male', '1988-03-30'),
('lisa@gomail.com', 'Lisa123', 'Manager', 'Female', '1991-11-12');

-- Insert sample products
INSERT INTO msitem (ItemName, ItemPrice, ItemDesc, ItemCategory, ItemStock) VALUES
('Gaming Laptop XR5', 1299.99, 'High-performance gaming laptop with RGB keyboard', 'Electronics', 50),
('Wireless Earbuds Pro', 149.99, 'True wireless earbuds with noise cancellation', 'Electronics', 100),
('Smart Watch Elite', 299.99, 'Premium smartwatch with health tracking', 'Electronics', 75),
('Designer Backpack', 89.99, 'Stylish and durable everyday backpack', 'Fashion', 120),
('Running Shoes Air', 129.99, 'Professional running shoes with air cushion', 'Sports', 80),
('Coffee Maker Deluxe', 199.99, 'Programmable coffee maker with thermal carafe', 'Home', 60),
('Yoga Mat Premium', 49.99, 'Extra thick yoga mat with carrying strap', 'Sports', 150),
('Gaming Mouse RGB', 79.99, 'High-DPI gaming mouse with programmable buttons', 'Electronics', 100),
('Leather Wallet', 39.99, 'Genuine leather wallet with RFID protection', 'Fashion', 200),
('Fitness Tracker Band', 59.99, 'Water-resistant fitness tracker with heart rate monitor', 'Electronics', 90);

-- Insert sample cart items
INSERT INTO mscart (UserID, ItemID, Quantity, ItemName, ItemPrice) VALUES
(2, 1, 1, 'Gaming Laptop XR5', 1299.99),
(2, 3, 1, 'Smart Watch Elite', 299.99),
(3, 2, 2, 'Wireless Earbuds Pro', 149.99),
(4, 5, 1, 'Running Shoes Air', 129.99),
(4, 7, 1, 'Yoga Mat Premium', 49.99);

-- Insert sample transactions with items
INSERT INTO transaction_header (UserID, Status) VALUES
(2, 'In Queue'),
(3, 'Sent'),
(4, 'In Queue');

-- Insert transaction details
INSERT INTO transaction_detail (TransactionID, ItemID, Quantity) VALUES
(1, 1, 1), -- Gaming Laptop
(1, 3, 1), -- Smart Watch
(2, 2, 2), -- Wireless Earbuds
(3, 5, 1), -- Running Shoes
(3, 7, 1); -- Yoga Mat
