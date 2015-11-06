package com.loveboyuan.smarttrader;
import java.util.ArrayList;


/**
 * Created by brenn_000 on 11/5/2015.
 *
 * This is the controller for the Pending class.
 */
public class PendingController {
    private static Pending pending = null;

    /**
     * Gets the Pending model.
     * @return this is the Pending model
     */
    static public Pending getPendingModel() {
        if (null == pending)
            pending = new Pending();
        return pending;
    }

    /**
     * Accesses addPending from the Pending model
     * @param user the User to be added to the sent pending list
     */
    static public void addPending(User user) {
        getPendingModel().addPending(user);
    }

    /**
     * Accesses removeRequest from the Pending model
     * @param user the User to be removed from the received pending list
     */
    static public void removeRequest(User user) {
        getPendingModel().removeRequest(user);
    }

    /**
     * Accesses cancelRequest from the Pending model
     * @param user the User to be removed from the sent pending list
     */
    static public void cancelRequest(User user) {
        getPendingModel().cancelRequest(user);
    }

    /**
     * Accesses acceptFriend from the Pending model
     * @param user the User to be accepted as a friend
     *             and removed from the received pending list
     */
    static public void acceptFriend(User user) {
        getPendingModel().acceptFriend(user);
    }

    /**
     * Accesses acceptAllFriend from the Pending model
     * @param list the array list of Users to be added to the
     *             current User friend list and to be removed from
     *             the sent pending list
     */
    static public void acceptAllFriends(ArrayList list) {
        getPendingModel().acceptAllFriends(list);
    }

    /**
     * Accesses cancelAllRequests from the Pending model
     * @param list the array list of Users to be removed
     *             form the sent friend requests
     */
    static public void cancelAllRequests(ArrayList list) {
        getPendingModel().cancelAllRequests(list);
    }

    /**
     * Accesses removeAllRequests from the Pending model
     * @param list the array list of Users to be removed
     *             form the received friend requests
     */
    static public void removeAllRequests(ArrayList list) {
        getPendingModel().removeAllRequests(list);
    }

    /**
     * Accesses getPendingSent from the Pending model
     */
    static public ArrayList<User> getPendingSent() {
        return getPendingModel().getPendingSent();
    }

    /**
     * Accesses getPendingReceived from the Pending model
     */
    static public ArrayList getPendingReceived() {
        return getPendingModel().getPendingReceived();
    }

}
