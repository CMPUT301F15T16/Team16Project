package com.loveboyuan.smarttrader;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by jiahui on 11/4/15.
 *
 * FriendListController is part of a singleton pattern applied to FriendList
 * and pulls data via elastic search to a local unique copy.
 */
public class FriendListController {
    private static FriendList friendList = null;
    private static Gson gson = new Gson();
    static User usr=LoginActivity.usr;


    private static final String TAG = "FriendListController";


    /**
     * Create a new FriendList model upon the first time
     * the method is invoked; return the embedded FriendList model
     * for the remaining calls.
     * @return the embedded unique copy of FriendList
     */
    static public FriendList getFriendListModel() {
        if (null == friendList)
            friendList = new FriendList();
        return friendList;
    }



    public static void clear() {
        friendList = null;
    }

    /**
     * Push the embedded local FriendList model up to the elastic search server.
     * @param friendList FriendList to be added
     */
    public static void addFriendList(FriendList friendList) {


        HttpClient httpClient = new DefaultHttpClient();

        try {

            friendList.setFriendListId(usr.getMy_id());
            HttpPost addRequest = new HttpPost(getFriendListModel().getResourceUrl() + friendList.getFriendListId());



            StringEntity stringEntity = new StringEntity(gson.toJson(friendList));



            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.e(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
