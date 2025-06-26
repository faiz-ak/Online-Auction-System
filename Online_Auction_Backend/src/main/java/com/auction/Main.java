package com.auction;

import com.auction.dao.AuctionItemDao;
import com.auction.dao.BidDao;
import com.auction.dao.TransactionDao;
import com.auction.dao.UserDao;
import com.auction.model.RecordClass.AuctionItem;
import com.auction.model.RecordClass.Bid;
import com.auction.model.RecordClass.Users;
import com.auction.model.RecordClass.PlacedTransaction;
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
        Users user = new Users(1, "John Doe", "john@example.com");
        new UserDao().RegisterUser(user);

        // Create and insert an auction item using DAO
        AuctionItem item = new AuctionItem(1, "Vintage Watch", "Luxury Watch", 100.0);
        new AuctionItemDao().insertAuctionItem(item);

        // Display all auction items
        System.out.println("\nBefore Update:");
        new AuctionItemDao().displayAllAuctionItems();

        // Update auction item
        AuctionItem updatedItem = new AuctionItem(1,"Vintage Gold Watch", "Antique Gold Watch", 150.0);
        new AuctionItemDao().updateAuctionItem(updatedItem);

        // Display again after update
        System.out.println("\nAfter Update:");
        new AuctionItemDao().displayAllAuctionItems();

        // Place bids
        Bid bid1 = new Bid(1, user.user_id(), item.item_id(), 300.0, LocalDateTime.now());
        new BidDao().placeBid(bid1);
       // Display Bid
        System.out.println("\nBid Placed Details:");
        new BidDao().displayBid(bid1);
        PlacedTransaction transaction = new PlacedTransaction(1, item.item_id(), user.user_id(), 250.0, LocalDateTime.now().plusMinutes(1));
        new TransactionDao().placeTransaction(transaction);


    }
}
