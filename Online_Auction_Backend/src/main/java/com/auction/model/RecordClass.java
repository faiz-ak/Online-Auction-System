//FAMIYA

package com.auction.model;
import java.time.LocalDateTime;



public class RecordClass {
 public record Users(int user_id,String name,String email){}
 public record AuctionItem(int item_id,String title,String category,double start_price){}
 public record Bid(int bid_id,int user_id,int item_id,double amount,LocalDateTime bid_time){}
public record  PlacedTransaction(int transaction_id,int item_id,int buyer_id,double amount,LocalDateTime transaction_time){}
}
