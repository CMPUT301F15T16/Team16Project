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

        //Need to check permissions before calling this function.
        //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listener);

    }

    public void startTracking(Context context){
        this.context = context;
    }

    public Location getUserLocation(){

        if (locationIsCurrent()){
            return currentlocation;
        } else {
            Location newlocation = null;
            currentlocation = newlocation;

        }
        return null;
    }

    public double distanceFromUser(Location userlocation, Item item){

        return userlocation.distanceTo(userlocation);//TODO

    }

    private void updateLocation(Location location){

    }

    private Boolean locationIsCurrent(){
        return false;
    }

}
