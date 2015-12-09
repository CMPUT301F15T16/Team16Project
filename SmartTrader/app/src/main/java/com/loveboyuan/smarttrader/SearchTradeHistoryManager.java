package com.loveboyuan.smarttrader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loveboyuan.smarttrader.elastic_search.SearchHit;
import com.loveboyuan.smarttrader.elastic_search.SearchResponse;
import com.loveboyuan.smarttrader.elastic_search.SimpleSearchCommand;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by boyuangu on 2015-11-29.
 *
 * SearchTradeHistoryManager is used to implement the communication
 * of search requests of TradeHistory between the local app and the
 * remote elastic search server.
 */
public class SearchTradeHistoryManager {

    static User usr=LoginActivity.usr;

    private Gson gson;
    private TradeHistory tradeHistory = new TradeHistory();
    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/TradeHistory";



    public SearchTradeHistoryManager(String search) {
        gson = new Gson();
    }


    /**
     * Main method used to queries the remote server about the given
     * search text string.
     * @param searchString search text keyword
     * @return TradeHistory containing results
     */
    public TradeHistory searchOwnTradeHistory(String searchString, String field) {
        TradeHistory result = new TradeHistory();


        HttpPost searchRequest = new HttpPost(prefix.concat("/_search"));


        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);


        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<Trade>>() {
        }.getType();

        SearchResponse<Trade> esResponse;
        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Extract the movies from the esResponse and put them in result


        for (SearchHit<Trade> hit : esResponse.getHits().getHits()) {
            if((hit.getSource().getOwner() == usr.getMy_id()) || (hit.getSource().getBorrower() == usr.getMy_id())) {
                result.addTrade(hit.getSource());
            }



        }




        return result;


    }

}
