package com.loveboyuan.smarttrader;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class TradeHistory implements Search, Serializable {

    // A TradeHistory has an array list of trades
    private ArrayList<Trade> trades = new ArrayList<Trade>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();



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



    public void addMyObserver(MyObserver observer){
        observers.add(observer);

    }


    public void notifyAllObservers(){
        for(MyObserver observer: observers){
            observer.update();
        }

    }
}
