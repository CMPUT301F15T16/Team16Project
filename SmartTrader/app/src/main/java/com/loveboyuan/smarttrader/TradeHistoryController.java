package com.loveboyuan.smarttrader;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by boyuangu on 2015-10-24.
 */

// We need a TradeHistory controller to interact with user behaviours by access TradeHistory model

public class TradeHistoryController {
    private static Gson gson = new Gson();
    private static TradeHistory tradeHistory = null;

    static public TradeHistory getTradeHistory() {
        if (tradeHistory == null) {
            tradeHistory = new TradeHistory();
            return tradeHistory;

        } else {
            return tradeHistory;

        }

    }

    public static void addTradeToServer(Trade trade) {
        HttpClient httpClient = new DefaultHttpClient();
        String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/TradeHistory/";


        try {

            HttpPost addRequest = new HttpPost(prefix + trade.getTradeID());


            StringEntity stringEntity = new StringEntity(gson.toJson(trade));

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public static void removeTradeFromServer(Trade trade) {
        HttpClient httpClient = new DefaultHttpClient();
        String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/TradeHistory/";


        try {

            HttpDelete deleteRequest = new HttpDelete(prefix + trade.getTradeID());


           // StringEntity stringEntity = new StringEntity(gson.toJson(trade));

           // deleteRequest.setEntity(stringEntity);
            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }




    public static void clear() {
        tradeHistory = null;

    }

}
