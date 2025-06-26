//FAMIYA


package com.auction.model;



public class Matcher {
    public static void match(Object obj) {
        switch (obj) {
            case RecordClass.Users(int user_id, String name, String email) ->
                    System.out.println(" USER → ID: " + user_id + " | Name: " + name + " | Email: " + email);

            case RecordClass.AuctionItem(int item_id, String title, String category, double start_price) ->
                    System.out.println(" AUCTION ITEM → ID: " + item_id + " | Title: " + title + " | Category: " + category + " | Start Price: ₹" + start_price);

            case RecordClass.Bid(int bid_id, int user_id, int item_id, double amount, var bid_time) ->
                    System.out.println(" BID → Bid ID: " + bid_id + " | User ID: " + user_id + " | Item ID: " + item_id + " | Amount: ₹" + amount + " | Time: " + bid_time);

            case RecordClass.PlacedTransaction trans ->
                    System.out.println(" TRANSACTION → Transaction ID: " + trans.transaction_id() + " | Item ID: " + trans.item_id() + " | Buyer ID: " + trans.buyer_id() + " | Amount: ₹" + trans.amount() + " | Time: " + trans.transaction_time());

            default -> System.out.println(" Unknown object type.");
        }
    }
}
