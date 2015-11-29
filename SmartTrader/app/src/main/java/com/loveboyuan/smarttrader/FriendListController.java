package com.loveboyuan.smarttrader;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by jiahui on 11/4/15.
 */
public class FriendListController {
    private static FriendList friendList = null;
    private static final String TAG = "FriendListController";


    static public FriendList getFriendListModel() {
        if (null == friendList)
            friendList = new FriendList();
        return friendList;
    }

    static public void addFriend(User user) {
        getFriendListModel().addFriend(user);
    }

    static public void removeFriend(User user) {
        HttpClient httpClient = new DefaultHttpClient();
        User usr=LoginActivity.usr;

        try {
            String removeString = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/Friends"
                    .concat(String.valueOf(usr.getMy_id())).concat("/")
                    .concat(user.getEsId());
            HttpDelete deleteRequest = new HttpDelete(removeString);
            Log.i(TAG,removeString );

            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void clear() {
        friendList = null;
    }
}
