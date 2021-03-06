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

/**
 * Created by boyuangu on 2015-11-26.
 *
 * SearchInventoryManager implements the search functionality
 * within the Inventory by handling the communication between
 * the local app and the remote elastic search server.
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


    /**
     * Queries the elastic search database for the search result
     * returned by using the given search keyword.
     * @param searchString text search keyword
     * @return Inventory containing search results
     */
    public Inventory searchInventory(String searchString, String field) {
        Inventory result = new Inventory();




            HttpPost searchRequest = new HttpPost(prefix.concat("/_search"));


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
            Type searchResponseType = new TypeToken<SearchResponse<Inventory>>() {
            }.getType();

            SearchResponse<Inventory> esResponse;
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


            for (SearchHit<Inventory> hit : esResponse.getHits().getHits()) {
                if(isFriend(hit.getSource().getInventoryId())) {
                    // if the item is not private, it can be searched by other users
                    result.getInventory().addAll(hit.getSource().getInventory());
                }

            }




        return result;
    }


    /**
     * Queries only the Inventory of the current logged in user
     * by the given string text field.
     * @param searchString text search keyword
     * @return Inventory containing the results
     */
    public Inventory searchOwnInventory(String searchString, String field) {
        Inventory result =null;


        HttpPost searchRequest = new HttpPost(prefix.concat("/_search"));


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
        Type searchResponseType = new TypeToken<SearchResponse<Inventory>>() {
        }.getType();

        SearchResponse<Inventory> esResponse;
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



        for (SearchHit<Inventory> hit : esResponse.getHits().getHits()) {
            if(hit.getSource().getInventoryId() == usr.getMy_id()){

                result = hit.getSource();
                return result;
            }

        }



        return result;


    }

    /**
     * Determines whether the Inventory of a particular
     * user belongs to one of the friends.
     * @param inventoryId unique id of Inventory
     * @return whether the associated Inventory belongs to a friend
     */
    private boolean isFriend(int inventoryId) {

        for(User user: FriendListController.getFriendListModel().getFriendList()){
            if(user.getMy_id()== inventoryId){
                return true;
            }
        }

        return false;
    }
}
