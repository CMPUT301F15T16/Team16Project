package com.loveboyuan.smarttrader;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class User {


    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/User";


    // the model needs to set address of the server
    private static final String RESOURCE_URL = prefix.concat("/");


    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/";
    private static final String SEARCH_URL = prefix.concat("/_search");
    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/_search";

    private static int id;


    private int my_id;
    // A User has his/her personal profile
    private Profile profile;
    // A User has his/her inventory
    private Inventory inventory = new Inventory();
    // A User has his/her trade history
    private TradeHistory tradeHistory = new TradeHistory();

    public User(int id) {
        User.id = id;
        my_id = id;
    }


    public int getMy_id() {
        return my_id;
    }
    public static int getId() {
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


    @Override
    public String toString(){
        return String.valueOf(getMy_id());
    }


    public static String getResourceUrl() {
        return RESOURCE_URL;
    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }
}
