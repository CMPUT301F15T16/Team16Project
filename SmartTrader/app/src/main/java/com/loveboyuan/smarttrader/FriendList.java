package com.loveboyuan.smarttrader;
import java.util.ArrayList;

/**
 * Created by jiahui on 10/18/15.
 */
public class FriendList {
    private ArrayList<Profile> friendList = new ArrayList<Profile>();
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public void addFriend(Profile profile) {
        if (false == this.friendList.contains(profile)) {
            this.friendList.add(profile);
        }
        this.notifyAllObservers();
    }

    public void removeFriend(Profile profile) {
        if (this.friendList.contains(profile)) {
            this.friendList.remove(profile);
        }
        this.notifyAllObservers();
    }

    public ArrayList<Profile> getFriendList() {
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
