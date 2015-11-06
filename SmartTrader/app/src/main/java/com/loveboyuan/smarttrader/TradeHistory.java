package com.loveboyuan.smarttrader;

import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class TradeHistory {

    // A TradeHistory has an array list of trades
    private ArrayList<Trade> trades = new ArrayList<Trade>();


    // TradeHistory can get any trade with index provided
    public Trade getTrade(Trade trade) {

        Trade returnTrade = null;
        for (Trade trade1 : trades) {
            if (trade1.equals(trade)) {
                returnTrade =  trade1;
            }

        }
        return returnTrade;
    }

    // TradeHistory can add trade (in "trades")
    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    // TradeHistory can delete trade
    public void removeTrade(Trade trade) {
        trades.remove(trade);
    }

    // TradeHistory can get size of the trades list
    public int size() {
        return trades.size();
    }

}
