package com.example.sharingapp;

import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Command to delete a bid
 */
public class DeleteBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;
    private Context context;

    public DeleteBidCommand(BidList bid_list, Bid bid, Context context) {
        this.bid_list = bid_list;
        this.bid = bid;
        this.context = context;
    }

    // Delete bid remotely
    public void execute(){
        ElasticSearchManager.RemoveBidTask removeBidTask = new ElasticSearchManager.RemoveBidTask();
        removeBidTask.execute(bid);

        try {
            if(removeBidTask.get()) {
                bid_list.removeBid(bid);
                super.setIsExecuted(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
    }

    // // Delete bid locally
    // public void execute(){
    //     bid_list.removeBid(bid);
    //     super.setIsExecuted(bid_list.saveBids(context));
    // }
}