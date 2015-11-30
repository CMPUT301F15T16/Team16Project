package com.loveboyuan.smarttrader;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


/**
 * Created by brenn_000 on 11/5/2015.
 *
 * This is the controller for the Pending class.
 */
public class PendingController {
    private static Pending pending = null;
    private static Gson gson = new Gson();
    static User usr=LoginActivity.usr;


    private static final String TAG = "PendingController";


    static public Pending getPendingModel() {
        if (null == pending)
            pending = new Pending();
        return pending;
    }


    public static void clear() {
        pending = null;
    }

    public static void addPending(Pending pending) {


        HttpClient httpClient = new DefaultHttpClient();

        try {

            HttpPost addRequest = new HttpPost(getPendingModel().getResourceUrl() + pending.getPendingId());



            StringEntity stringEntity = new StringEntity(gson.toJson(pending));



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
