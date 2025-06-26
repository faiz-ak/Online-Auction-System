package com.auction;

import com.auction.dao.BidDao;
import com.auction.dao.TransactionDao;
import com.auction.dao.UserDao;

public class Main {
    public static void main(String[] args) {
        User user = new User(""); //Sample values will be given at last
        new UserDao().RegisterUser(user);
        Bid bid = new Bid(""); //Sample values will be given at last
        new BidDao().placeBid(bid);
        Transaction transaction = new Transaction(""); //Sample values will be given at last
        new TransactionDao().placeTransaction(transaction);
    }
}
