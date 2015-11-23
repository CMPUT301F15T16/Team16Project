package com.loveboyuan.smarttrader;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by BSid on 15-11-19.
 */
public class UserLocation implements Runnable{

    private Location currentlocation;
    private Context context;
    private boolean isRunning = false;
    private static final long TRACK_TIME = 2000;

    public void run() {

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
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

    public void startTracking(Context context){
        this.context = context;
        isRunning = true;
        run();
    }

    private void stopTracking(){
        //TODO
        isRunning = false;
    }

    public void setItemLocation(Item item){

        if (isRunning){
            //uncomment this when item has location
            //item.location = currentlocation;
            stopTracking();
        }

    }

    public Location getUserLocation(){

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

    public double itemDistanceFromUser(Item item){

        //return getUserLocation().distanceTo(userlocation);//TODO
        //parameters should be item.location once item has location
        return 0.0;
    }

    private void updateLocation(Location location){
        if (!locationIsCurrent()){
            if (location.getAccuracy() > currentlocation.getAccuracy()){
                currentlocation = location;
            }
        }

    }

    private Boolean locationIsCurrent(){

        if (currentlocation == null || (System.currentTimeMillis() - currentlocation.getTime())<2000) {
            return false;
        } else{
            return true;
        }
    }

}
