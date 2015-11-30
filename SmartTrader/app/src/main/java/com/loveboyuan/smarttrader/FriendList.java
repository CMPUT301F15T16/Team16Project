package com.loveboyuan.smarttrader;
import java.util.ArrayList;

/**
 * Created by jiahui on 10/18/15.
 */
public class FriendList {


    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/Friends";
    public static String prefix2 = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/User/_search";

    static User usr=LoginActivity.usr;

    // the model needs to set address of the server
    private static final String RESOURCE_URL = prefix.concat("/");


    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/";
    private static final String SEARCH_URL = prefix.concat("/_search");
    //"http://cmput301.softwareprocess.es:8080/cmput301f15t16/inventoryItem/_search";
    private ArrayList<User> friendList = new ArrayList<User>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();



    private int friendListId;
    public FriendList() {

    }



    public void addFriend(User user) {
        if (!this.friendList.contains(user)) {
            this.friendList.add(user);
        }
        this.notifyAllObservers();
    }

    public void removeFriend(User user) {
        if (this.friendList.contains(user)) {
            this.friendList.remove(user);
        }
        this.notifyAllObservers();
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public void addMyObserver(MyObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (MyObserver observer : observers)
            observer.update();
    }


    public int getFriendListId() {
        return friendListId;
    }

    public void setFriendListId(int friendListId) {
        this.friendListId = friendListId;
    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }

    public static String getResourceUrl() {
        return RESOURCE_URL;
    }
    public static String getSearchUrl2() {
        return prefix2;
    }


}
