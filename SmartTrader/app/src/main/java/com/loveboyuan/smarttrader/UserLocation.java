package com.loveboyuan.smarttrader;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by BSid on 15-11-19.
 */
public class UserLocation {

    private static final long TRACK_TIME = 2000;
    private static Location currentlocation;
    private static Context context;
    private static boolean isRunning = false;
    private static LocationManager manager;
    private static LocationListener listener;


    public static void startTracking(Context con){
        context = con;
        isRunning = true;
        init();
    }

    private static void stopTracking(){
        isRunning = false;
        try {
            manager.removeUpdates(listener);
        }catch (SecurityException e){
            System.out.println("Lack Permissions to use location");
        }
    }

    public static void setItemLocation(Item item){

        if (isRunning) {
            item.setLocation(currentlocation);
            stopTracking();
        }
    }

    public static Location getUserLocation(){

        if (locationIsCurrent()){
            return currentlocation;
        } else {
            long start = System.currentTimeMillis();
            while((System.currentTimeMillis()-start)<TRACK_TIME){
                //wait
            }
            stopTracking();
        }
        return currentlocation;
    }

    public static double itemDistanceFromUser(Item item) {

        return getUserLocation().distanceTo(item.getLocation());
    }

    private static void init() {

        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        listener  = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }catch (SecurityException e){
            System.out.println("Lack Permissions to use location");
        }

    }

    private static void updateLocation(Location location){
        if (!locationIsCurrent()){
            if (location.getAccuracy() > currentlocation.getAccuracy()){
                currentlocation = location;
            }
        }

    }

    private static Boolean locationIsCurrent(){

        if (currentlocation == null || (System.currentTimeMillis() - currentlocation.getTime())<2000) {
            return false;
        } else{
            return true;
        }
    }

}
