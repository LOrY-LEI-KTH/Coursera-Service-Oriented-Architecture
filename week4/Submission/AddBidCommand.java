package com.example.sharingapp;

import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Command to add a bid
 */
public class AddBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;
    private Context context;

    public AddBidCommand(BidList bid_list, Bid bid, Context context) {
        this.bid_list = bid_list;
        this.bid = bid;
        this.context = context;
    }

    // Save bid remotely
    public void execute(){
        ElasticSearchManager.AddBidTask addBidTask = new ElasticSearchManager.AddBidTask();
        addBidTask.execute(bid);

        try {
            if(addBidTask.get()) {
                bid_list.addBid(bid);
                super.setIsExecuted(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
    }


    // // Save bid locally
    // public void execute(){
    //     bid_list.addBid(bid);
    //     super.setIsExecuted(bid_list.saveBids(context));
    // }
}
