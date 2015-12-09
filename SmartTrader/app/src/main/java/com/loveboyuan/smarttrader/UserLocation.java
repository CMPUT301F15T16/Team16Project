package com.loveboyuan.smarttrader;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by BSid on 15-11-19.
 *
 * UserLocation is used to track the location of the user.
 */
public class UserLocation {

    private static final long TRACK_TIME = 2000;
    private static Location currentlocation;
    private static Context context;
    private static boolean isRunning;
    private static LocationManager manager;
    private static LocationListener listener;



    public static void startTracking(Context con){
        context = con;
        isRunning = true;
        init();
        System.out.println("Begin tracking");
    }

    private static void stopTracking(){
        isRunning = false;
        try {
            manager.removeUpdates(listener);
            System.out.println("Finish Tracking");
        }catch (SecurityException e){
            System.out.println("Lack Permissions to use location");
        }
    }
/*
    public static void setItemLocation(Item item){

        if (isRunning) {
            System.out.println("should be printing");
            System.out.println("location: " + currentlocation.getLatitude());
            item.setLocation(currentlocation);
            System.out.println("itemlocation: " + item.getLocation().getLatitude());
            stopTracking();
        }
    }

*/
    public static Location getUserLocation(){

            long start = System.currentTimeMillis();
            while((System.currentTimeMillis()-start)<TRACK_TIME){
                //wait
            }
            stopTracking();

        return currentlocation;
    }
/*
    public static double itemDistanceFromUser(Item item) {

        return getUserLocation().distanceTo(item.getLocation());
    }
*/
    private static void init() {

        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        listener  = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
                System.out.println("Location: " + location);
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
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        }catch (SecurityException e){
            System.out.println("Lack Permissions to use location");
        }

    }


    private static void updateLocation(Location location){
        if (locationIsCurrent(location)){
            if (location.getAccuracy() < currentlocation.getAccuracy()){
                currentlocation = location;
            }
        } else {
            currentlocation = location;
        }

    }

    private static Boolean locationIsCurrent(Location location){

        if (currentlocation == null || (location.getTime()-currentlocation.getTime())>2000) {
            System.out.println("locating not Current(time)");
            return false;
        } else{
            System.out.println("location current(time) ") ;
            return true;
        }
    }

}
