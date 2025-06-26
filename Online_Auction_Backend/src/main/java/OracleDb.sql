--bibhab
CREATE TABLE User (
    user_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE AuctionItem (
    item_id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    start_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Bid (
    bid_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    bid_time TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id)
);

CREATE TABLE Transaction (
    transaction_id INT PRIMARY KEY,
    item_id INT NOT NULL,
    buyer_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_time TIMESTAMP NOT NULL,
    FOREIGN KEY (item_id) REFERENCES AuctionItem(item_id),
    FOREIGN KEY (buyer_id) REFERENCES User(user_id)
);