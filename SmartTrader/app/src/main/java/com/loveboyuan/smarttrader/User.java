package com.loveboyuan.smarttrader;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class User {

    private final int id;
    // A User has his/her personal profile
    private Profile profile;
    // A User has his/her inventory
    private Inventory inventory = new Inventory();
    // A User has his/her trade history
    private TradeHistory tradeHistory = new TradeHistory();

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // A User can get his/her inventory
    public Inventory getInventory() {
        return inventory;
    }

    // A User can propose a trade as a borrower with another user(owner)
    public void proposeTrade(Trade trade) {
        // Borrower proposed the trade, owner should receives the trade info in his/her tradeHistory
        this.tradeHistory.addTrade(trade);
        User owner = trade.getOwner();
        owner.getTradeHistory().addTrade(trade);

    }

    // A borrower can delete his/her trade request
    public void deleteTrade(Trade trade) {
        this.tradeHistory.removeTrade(trade);
        User owner = trade.getOwner();
        owner.getTradeHistory().removeTrade(trade);

    }


    // A User can get his/her trade history
    public TradeHistory getTradeHistory() {
        return tradeHistory;
    }


}
