package com.loveboyuan.smarttrader;

import java.util.ArrayList;
import java.util.Collection;

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


    /**
     * Accepts the friend request and adds them to the current
     * User friend list. It also removes the pending friend request
     * from the received pending list.
     * @param user this is the User being accepted as a friend
     */
    public void acceptFriend(User user) {
        FriendListController.addFriend(user);
        removeRequest(user);
        this.notifyAllObservers();
    }

    /**
     * This accepts all friend requests in Pending and adds them all
     * to the current User friend list. It also removes all of the friend
     * requests from the received pending list.
     * @param list this is the array list of all friend requests
     */
    public void acceptAllFriends(ArrayList list) {
        //FriendListController.addAllFriends(list);
        removeAllRequests(list);
        this.notifyAllObservers();
    }

    /**
     * This adds a user to the sent pending list. This is invoked when the
     * current User requests a friend.
     * @param user us the User that has been requested as a friend
     */
    public void addPending(User user) {
        if (!this.pendingSent.contains(user)) {
            this.pendingSent.add(user);
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
     * This removes all pending sent friend requests
     * @param list the array list of sent friend
     *             requests to be removed from sent
     *             pending list
     */
    public void cancelAllRequests(ArrayList list) {
        this.pendingSent.removeAll(list);
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
     * This removes all pending received friend requests.
     * @param list the array list of all friends that have requested
     *             the current User.
     */
    public void removeAllRequests(ArrayList list) {
        this.pendingReceived.removeAll(list);
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
}
