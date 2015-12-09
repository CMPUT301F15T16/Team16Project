package com.loveboyuan.smarttrader;

import java.io.Serializable;

/**
 * Created by boyuangu on 2015-10-16.
 *
 * User model class has all the personal information about an user.
 * Please note that even though there is a Profile option in our
 * app that functionality is actually carried over by this class
 * since Profile is merged into this class for simplicity.
 */
public class User implements Serializable {

    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/User";

    // the model needs to set address of the server
    private static final String RESOURCE_URL = prefix.concat("/");

    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/";
    private static final String SEARCH_URL = prefix.concat("/_search");
    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/_search";

    private static int id;

    private int my_id;
    // A User has his/her personal profile
    private String name;
    private String email;
    private String phoneNumber;
    private String cityName;



    public String esId;


    public User(int id) {
        User.id = id;
        my_id = id;
    }
    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }


    public int getMy_id() {
        return my_id;
    }
    public static int getId() {
        return id;
    }


    @Override
    public String toString(){
        return getName();
    }


    public static String getResourceUrl() {
        return RESOURCE_URL;
    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
