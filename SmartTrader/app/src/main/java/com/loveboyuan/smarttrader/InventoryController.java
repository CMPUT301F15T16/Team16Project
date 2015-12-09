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
 *
 * InventoryController maintains a unique copy of Inventory for the
 * logged in user and saves it to the elastic search server upon
 * request.
 */
public class InventoryController {
    private static Inventory inventory = null;
    private static Gson gson = new Gson();
    private static final String TAG = "InventoryController";

    /**
     * Create a new embedded Inventory upon first invocation;
     * returns the embedded Inventory if it already exists.
     * @return embedded Inventory model
     */
    static public Inventory getInventoryModel() {
        if (inventory == null) {
            inventory = new Inventory();
            return inventory;

        } else {
            return inventory;

        }

    }

    /**
     * Push the embedded local Inventory to the elastic search server.
     * @param inventory Inventory to be pushed
     */
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


    /**
     * Convert the photo taken by the user to string by
     * the base64 mechanism.
     * @param bitmap Bitmap to be converted
     * @return converted string format
     */
    public static String convertBitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    /**
     * Convert the base64 string representation to its original
     * bitmap to be displayed properly.
     * @param image base64 string representation to be converted
     * @return converted original bitmap
     */
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
