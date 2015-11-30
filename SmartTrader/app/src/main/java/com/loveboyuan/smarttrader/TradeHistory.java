package com.loveboyuan.smarttrader;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class TradeHistory implements Search, Serializable {
    static User usr=LoginActivity.usr;

    // A TradeHistory has an array list of trades
    private ArrayList<Trade> trades = new ArrayList<Trade>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();


    private int tradeHistoryId;
    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/TradeHistory";



    // the model needs to set address of the server
    private static final String RESOURCE_URL = prefix.concat("/");
    private static final String SEARCH_URL = prefix.concat("/_search");


    public TradeHistory(){
        setTradeHistoryId(usr.getMy_id());

    }
    public ArrayList<Trade> getTrades() {
        return trades;
    }
    // TradeHistory can add trade (in "trades")
    public void addTrade(Trade trade) {
        trades.add(trade);
        this.notifyAllObservers();

    }

    // TradeHistory can delete trade
    public void removeTrade(Trade trade) {
        trades.remove(trade);
        this.notifyAllObservers();

    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }
    public static String getResourceUrl() {
        return RESOURCE_URL;
    }

    public void addMyObserver(MyObserver observer){
        observers.add(observer);

    }

    public int getTradeHistoryId() {
        return tradeHistoryId;
    }

    public void setTradeHistoryId(int tradeHistoryId) {
        this.tradeHistoryId = tradeHistoryId;
    }

    public void notifyAllObservers(){
        for(MyObserver observer: observers){
            observer.update();
        }

    }
}
