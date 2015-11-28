package com.loveboyuan.smarttrader;

/**
 * Created by jiahui on 11/4/15.
 */
public class FriendListController {
    private static FriendList friendList = null;

    static public FriendList getFriendListModel() {
        if (null == friendList)
            friendList = new FriendList();
        return friendList;
    }

    static public void addFriend(User user) {
        getFriendListModel().addFriend(user);
    }

    static public void removeFriend(User user) {
        getFriendListModel().removeFriend(user);
    }


    public static void clear() {
        friendList = null;
    }
}
