package com.auction;

import com.auction.dao.BidDao;
import com.auction.dao.TransactionDao;
import com.auction.dao.UserDao;

import com.auction.util.LogArchiver;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) {
        // Schedule weekly log archival
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(LogArchiver::archiveLogs, 0, 7, TimeUnit.DAYS);


        User user = new User(""); //Sample values will be given at last
        new UserDao().RegisterUser(user);
        Bid bid = new Bid(""); //Sample values will be given at last
        new BidDao().placeBid(bid);
        Transaction transaction = new Transaction(""); //Sample values will be given at last
        new TransactionDao().placeTransaction(transaction);
    }
}
