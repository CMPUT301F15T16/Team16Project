package com.loveboyuan.smarttrader;
import java.util.ArrayList;

/**
 * Created by jiahui on 10/18/15.
 */
public class FriendList {
    private ArrayList<User> friendList = new ArrayList<User>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

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
}
