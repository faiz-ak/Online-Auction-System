package com.auction;

import com.auction.dao.AuctionItemDao;
import com.auction.dao.BidDao;
import com.auction.dao.UserDao;
import com.auction.model.RecordClass.AuctionItem;
import com.auction.model.RecordClass.Bid;
import com.auction.model.RecordClass.User;
import com.auction.model.RecordClass.Transaction;
import com.auction.util.LogArchiver;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Schedule weekly log archival
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(LogArchiver::archiveLogs, 0, 7, TimeUnit.DAYS);

        // Register a user
        User user = new User(1, "John Doe", "john@example.com");
        new UserDao().RegisterUser(user);

        // Create and insert an auction item using DAO
        AuctionItemDao itemDao = new AuctionItemDao();
        AuctionItem item = new AuctionItem(1, "Vintage Watch", "Luxury Watch", 100.0, LocalDateTime.now().plusDays(1), user.id());
        itemDao.insertAuctionItem(item);

        // Display all auction items
        System.out.println("\n🔍 Before Update:");
        itemDao.displayAllAuctionItems();

        // Update auction item
        AuctionItem updatedItem = new AuctionItem(1, "Vintage Gold Watch", "Antique Gold Watch", 150.0, LocalDateTime.now().plusDays(2), user.id());
        itemDao.updateAuctionItem(updatedItem);

        // Display again after update
        System.out.println("\n🔍 After Update:");
        itemDao.displayAllAuctionItems();

        // Place bids
        Bid bid1 = new Bid(1, user.user_id(), item.item_id(), 200.0, LocalDateTime.now());
        new BidDao().placeBid(bid1);

        Bid bid2 = new Bid(2, user.user_id(), item.item_id(), 250.0, LocalDateTime.now().plusSeconds(10));
        new BidDao().placeBid(bid2);

        Transaction transaction = new Transaction(1, item.item_id(), user.user_id(), 250.0, LocalDateTime.now().plusMinutes(1));
        new TransactionDao().placeTransaction(transaction);


    }
}
