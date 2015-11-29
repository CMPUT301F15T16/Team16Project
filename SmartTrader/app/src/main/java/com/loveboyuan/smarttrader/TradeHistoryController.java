package com.loveboyuan.smarttrader;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
        String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/TradeHistory";


        String resourseURL = prefix.concat(String.valueOf(trade.getBorrower()).concat("/"));
        String resourseURL2 = prefix.concat(String.valueOf(trade.getOwner()).concat("/"));
        try {

            HttpPost addRequest = new HttpPost(resourseURL);


            StringEntity stringEntity = new StringEntity(gson.toJson(trade));
            Log.e("gsonitem", gson.toJson(trade).toString());

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            HttpPost addRequest = new HttpPost(resourseURL2);


            StringEntity stringEntity = new StringEntity(gson.toJson(trade));
            Log.e("gsonitem", gson.toJson(trade).toString());

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            // Log.e(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public static void removeTradeFromServer(Trade trade) {
    }



    public static void clear() {
        tradeHistory = null;

    }

}
