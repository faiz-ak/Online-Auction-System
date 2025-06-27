// FAMIYA
package com.auction.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Matcher {
    private static final Logger logger = LoggerFactory.getLogger(Matcher.class);

    public static void match(Object obj) {
        switch (obj) {
            case RecordClass.Users(int user_id, String name, String email) ->
                    logger.info(" USER → ID: {} | Name: {} | Email: {}", user_id, name, email);

            case RecordClass.AuctionItem(int item_id, String title, String category, double start_price) ->
                    logger.info(" AUCTION ITEM → ID: {} | Title: {} | Category: {} | Start Price: ₹{}", item_id, title, category, start_price);

            case RecordClass.Bid(int bid_id, int user_id, int item_id, double amount, var bid_time) ->
                    logger.info(" BID → Bid ID: {} | User ID: {} | Item ID: {} | Amount: ₹{} | Time: {}", bid_id, user_id, item_id, amount, bid_time);

            case RecordClass.PlacedTransaction trans ->
                    logger.info(" TRANSACTION → Transaction ID: {} | Item ID: {} | Buyer ID: {} | Amount: ₹{} | Time: {}",
                            trans.transaction_id(), trans.item_id(), trans.buyer_id(), trans.amount(), trans.transaction_time());

            default -> logger.warn(" Unknown object type.");
        }
    }
}
