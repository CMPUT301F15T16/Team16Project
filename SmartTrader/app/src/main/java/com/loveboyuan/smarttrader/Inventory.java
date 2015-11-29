package com.loveboyuan.smarttrader;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by boyuangu on 2015-10-16.
 */
public class Inventory implements Search, Serializable{

    static User usr=LoginActivity.usr;

    // Inventory has items It should be an arraylist of Items
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();



    private int inventoryId;
    public Inventory() {

         setInventoryId(usr.getMy_id());
    }



    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/Inventory";


    // the model needs to set address of the server
    private static final String RESOURCE_URL = prefix.concat("/");
            //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/";
    private static final String SEARCH_URL = prefix.concat(String.valueOf(usr.getMy_id()).concat("/_search"));
            //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/_search";


    public ArrayList<Item> getInventory() {
        return inventory;
    }
    // Inventory can add an Item (to the "items")
    public void addItem(Item item) {
        inventory.add(item);
        this.notifyAllObservers();
    }

    public void removeItem(Item item){
        inventory.remove(item);
        this.notifyAllObservers();

    }


    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    public static String getResourceUrl() {
        return RESOURCE_URL;
    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }



    public void addMyObserver(MyObserver observer){
        observers.add(observer);

    }


    public void notifyAllObservers(){
        for(MyObserver observer: observers){
            observer.update();
        }

    }
}
