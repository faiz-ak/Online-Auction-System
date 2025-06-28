--FOR ORACLE QUERY
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE auction_item_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bid_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE transaction_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE Users (
    user_id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) NOT NULL
);

CREATE TABLE AuctionItem (
    item_id NUMBER PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,

    category VARCHAR2(50) NOT NULL,
    start_price NUMBER(10, 2) NOT NULL
);

CREATE TABLE Bid (
    bid_id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL,
    item_id NUMBER NOT NULL,
    amount NUMBER(10, 2) NOT NULL,
    bid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bid_user FOREIGN KEY (user_id) REFERENCES Users(user_id),
    CONSTRAINT fk_bid_item FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id)
);

CREATE TABLE PlacedTransaction (
    transaction_id NUMBER PRIMARY KEY,
    item_id NUMBER NOT NULL,
    buyer_id NUMBER NOT NULL,
    amount NUMBER(10, 2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transaction_item FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id),
    CONSTRAINT fk_transaction_buyer FOREIGN KEY (buyer_id) REFERENCES Users(user_id)
);

-- ORACLE QUERY END

--FOR MYSQL QUERY
--CREATE TABLE Users(
--    user_id INT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(100) NOT NULL,
--    email VARCHAR(100) NOT NULL
--);
--
--CREATE TABLE AuctionItem(
--    item_id INT AUTO_INCREMENT PRIMARY KEY,
--    title VARCHAR(100) NOT NULL,
--    category VARCHAR(50) NOT NULL,
--    start_price DECIMAL(10, 2) NOT NULL
--);
--
--CREATE TABLE Bid(
--    bid_id INT AUTO_INCREMENT PRIMARY KEY,
--    user_id INT NOT NULL,
--    item_id INT NOT NULL,
--    amount DECIMAL(10, 2) NOT NULL,
--    bid_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    FOREIGN KEY (user_id) REFERENCES Users(user_id),
--    FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id)
--);
--
--CREATE TABLE PlacedTransaction(
--    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
--    item_id INT NOT NULL,
--    buyer_id INT NOT NULL,
--    amount DECIMAL(10, 2) NOT NULL,
--    transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id),
--    FOREIGN KEY (buyer_id) REFERENCES Users(user_id)
--);
-- MYSQL QUERY END