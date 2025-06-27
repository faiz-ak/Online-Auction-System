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
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Schedule weekly log archival
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(LogArchiver::archiveLogs, 0, 7, TimeUnit.DAYS);

        Scanner sc = new Scanner(System.in);

        UserDao userDao = new UserDao();
        AuctionItemDao itemDao = new AuctionItemDao();
        BidDao bidDao = new BidDao();
        TransactionDao transactionDao = new TransactionDao();

        // Insert multiple Users
        System.out.print("How many users to add? ");
        int ucount = sc.nextInt();
        for (int i = 0; i < ucount; i++) {
            System.out.print("Enter user_id: ");
            int uid = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter name: ");
            String uname = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();

            Users user = new Users(uid, uname, email);
            userDao.RegisterUser(user);
        }

        // Insert multiple Auction Items
        System.out.print("\nHow many auction items to add? ");
        int acount = sc.nextInt();
        for (int i = 0; i < acount; i++) {
            System.out.print("Enter item_id: ");
            int itemId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter title: ");
            String title = sc.nextLine();
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            System.out.print("Enter start_price: ");
            double price = sc.nextDouble();

            AuctionItem item = new AuctionItem(itemId, title, category, price);
            itemDao.insertAuctionItem(item);
        }

        // Update one item (optional)
        System.out.println("\nUpdate an item? (1 for yes): ");
        int updateChoice = sc.nextInt();
        if (updateChoice == 1) {
            System.out.print("Enter item_id to update: ");
            int itemId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new title: ");
            String newTitle = sc.nextLine();
            System.out.print("Enter new category: ");
            String newCat = sc.nextLine();
            System.out.print("Enter new price: ");
            double newPrice = sc.nextDouble();

            AuctionItem updatedItem = new AuctionItem(itemId, newTitle, newCat, newPrice);
            itemDao.updateAuctionItem(updatedItem);
        }

        // Display all items
        System.out.println("\nAll Auction Items:");
        itemDao.displayAllAuctionItems();

        // Place multiple bids
        System.out.print("\nHow many bids to place? ");
        int bcount = sc.nextInt();
        for (int i = 0; i < bcount; i++) {
            System.out.print("Enter bid_id: ");
            int bidId = sc.nextInt();
            System.out.print("Enter user_id: ");
            int userId = sc.nextInt();
            System.out.print("Enter item_id: ");
            int itemId = sc.nextInt();
            System.out.print("Enter bid amount: ");
            double amount = sc.nextDouble();

            Bid bid = new Bid(bidId, userId, itemId, amount, LocalDateTime.now());
            bidDao.placeBid(bid);
        }

        // Finalize and insert winning transactions
        System.out.println("\nFinalizing winning transactions...");
        bidDao.finalizeWinningTransactions();

        System.out.println("\nAll operations completed.");
        sc.close();

    }
}
