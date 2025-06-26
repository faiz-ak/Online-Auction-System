package com.auction;
import com.auction.dao.AuctionItem;
import com.auction.dao.BidDao;
import com.auction.dao.UserDao;
import com.auction.model.RecordClass.Bid;
import com.auction.model.RecordClass.User;
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

        // Create an auction item
        AuctionItem item = new AuctionItem(1, "Vintage Watch", "A classic timepiece", 100.0, 1);
        item.createAuctionItem();

        // Place bids
        Bid bid1 = new Bid(1, 1, 1, 200.0, LocalDateTime.now());
        new BidDao().placeBid(bid1);
        Bid bid2 = new Bid(2, 1, 1, 250.0, LocalDateTime.now().plusSeconds(10));
        new BidDao().placeBid(bid2);

        // Display auction details before update
        System.out.println("\nBefore Update:");
        item.displayAuctionDetails();

        // Update the auction item
        item.updateAuctionItem("Vintage Gold Watch", "A classic gold timepiece", 150.0);

        // Display auction details after update
        System.out.println("\nAfter Update:");
        item.displayAuctionDetails();
    }
}
