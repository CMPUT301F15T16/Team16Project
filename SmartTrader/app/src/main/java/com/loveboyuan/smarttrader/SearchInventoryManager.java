package com.loveboyuan.smarttrader;

import android.util.Log;

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
import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-11-26.
 */
public class SearchInventoryManager {
    static User usr=LoginActivity.usr;


    private static final String TAG = "InventorySearch";
    private static final String TAG2 = "MyAddress";
    private Gson gson;
    private Inventory inventory = new Inventory();
    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/Inventory";



    public SearchInventoryManager(String search) {
        gson = new Gson();
    }


    public Inventory searchInventory(String searchString, String field) {
        Inventory result = new Inventory();

        ArrayList<User> friends = FriendListController.getFriendListModel().getFriendList();

        for(User user : friends) {


            HttpPost searchRequest = new HttpPost(prefix.concat(String.valueOf(user.getMy_id())).concat("/_search"));
            Log.i(TAG2, "Json command: " + prefix.concat(String.valueOf(user.getMy_id())).concat("/_search"));


            String[] fields = null;
            if (field != null) {
                throw new UnsupportedOperationException("Not implemented!");
            }

            SimpleSearchCommand command = new SimpleSearchCommand(searchString);

            String query = gson.toJson(command);
            Log.i(TAG, "Json command: " + query);

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
            Type searchResponseType = new TypeToken<SearchResponse<Item>>() {
            }.getType();

            SearchResponse<Item> esResponse;
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


            for (SearchHit<Item> hit : esResponse.getHits().getHits()) {
                if(!hit.getSource().isPrivate()) {
                    // if the item is not private, it can be searched by other users
                    result.addItem(hit.getSource());
                }

            }


        }

        return result;
    }

    public Inventory searchOwnInventory(String searchString, String field) {
        Inventory result = new Inventory();


        HttpPost searchRequest = new HttpPost(prefix.concat(String.valueOf(usr.getMy_id())).concat("/_search"));


        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

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
        Type searchResponseType = new TypeToken<SearchResponse<Item>>() {
        }.getType();

        SearchResponse<Item> esResponse;
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


        for (SearchHit<Item> hit : esResponse.getHits().getHits()) {
            result.addItem(hit.getSource());

        }




        return result;


    }
}
