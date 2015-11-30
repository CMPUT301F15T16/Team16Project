package com.loveboyuan.smarttrader;

import java.util.ArrayList;

/**
 * Created by bstang on 11/5/2015.
 *
 * This model is for the pending friend requests, both the ones you sent (needs to be implemented)
 * and the friend requests you have received. Here you can accept or reject a friend request. You can
 * also cancel a previously sent request.
 */

public class Pending {
    private ArrayList<User> pendingSent = new ArrayList<User>();
    private ArrayList<User> pendingReceived = new ArrayList<User>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public static String prefix = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/PendingSent";
    public static String prefix2 = "http://cmput301.softwareprocess.es:8080/cmput301f15t16/PendingReceived";

    static User usr=LoginActivity.usr;

    // the model needs to set address of the server
    private static final String RESOURCE_URL_S = prefix.concat("/");
    private static final String RESOURCE_URL_R = prefix2.concat("/");
    private static final String SEARCH_URL_S = prefix.concat("/_search");
    private static final String SEARCH_URL_R = prefix2.concat("/_search");


    private int pendingSentId;
    private int pendingReceivedId;
    public Pending() {
    }


    /**
     * This adds a user to the sent pending list. This is invoked when the
     * current User requests a friend.
     * @param user us the User that has been requested as a friend
     */
    public void addPendingSent(User user) {
        if (!this.pendingSent.contains(user)) {
            this.pendingSent.add(user);
        }
        this.notifyAllObservers();
    }

    /**
     * This adds a user to the sent pending list. This is invoked when the
     * current User requests a friend.
     * @param user us the User that has been requested as a friend
     */
    public void addPendingReceived(User user) {
        if (!this.pendingReceived.contains(user)) {
            this.pendingReceived.add(user);
        }
        this.notifyAllObservers();
    }


    /**
     * This removes a friend request from the sent requests
     * pending list.
     * @param user the User to be removed the the sent request
     *             pending list
     */
    public void cancelRequest(User user) {
        if (this.pendingSent.contains(user)) {
            this.pendingSent.remove(user);
        }
        this.notifyAllObservers();
    }

    /**
     * This removes a friend request from the received friend
     * requests pending list.
     * @param user the User to be removed the the received request
     *             pending list
     */
    public void removeRequest(User user) {
        if (this.pendingReceived.contains(user)) {
            this.pendingReceived.remove(user);
        }
        this.notifyAllObservers();
    }

    /**
     * this gets the sent pending list
     * @return the sent pending list
     */
    public ArrayList<User> getPendingSent() {
        return pendingSent;
    }

    /**
     * this gets the received pending list
     * @return the received pending list
     */
    public ArrayList<User> getPendingReceived() {
        return pendingReceived;
    }

    public void addMyObserver(MyObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (MyObserver observer : observers)
            observer.update();
    }

    public int getPendingSentId() {
        return pendingSentId;
    }
    public int getPendingReceivedId() {
        return pendingReceivedId;
    }

    public void setPendingSentId(int pendingSentId) {
        this.pendingSentId = pendingSentId;
    }

    public void setPendingReceivedId(int pendingReceivedId) {
        this.pendingReceivedId = pendingReceivedId;
    }

    public static String getSearchUrlS() {
        return SEARCH_URL_S;
    }
    public static String getSearchUrlR() {
        return SEARCH_URL_R;
    }

    public static String getResourceUrlS() {
        return RESOURCE_URL_S;
    }
    public static String getResourceUrlR() {
        return RESOURCE_URL_R;
    }

}
