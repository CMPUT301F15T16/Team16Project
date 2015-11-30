package com.loveboyuan.smarttrader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;


/**
 * Created by boyuangu on 2015-11-02.
 */
public class InventoryController {
    private static Inventory inventory = null;
    private static Gson gson = new Gson();
    private static final String TAG = "InventoryController";

    static public Inventory getInventoryModel() {
        if (inventory == null) {
            inventory = new Inventory();
            return inventory;

        } else {
            return inventory;

        }

    }

    public static void addInventory(Inventory inventory) {



        HttpClient httpClient = new DefaultHttpClient();

        try {

            HttpPost addRequest = new HttpPost(getInventoryModel().getResourceUrl() + inventory.getInventoryId());



            StringEntity stringEntity = new StringEntity(gson.toJson(inventory));

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.e(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //Taken from http://androidapplicationdeveloper.weebly.com/android-tutorial/how-to-convert-bitmap-to-string-and-string-to-bitmap
    public static String convertBitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    //Taken from http://androidapplicationdeveloper.weebly.com/android-tutorial/how-to-convert-bitmap-to-string-and-string-to-bitmap
    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static void clear() {
        inventory = null;

    }



}
