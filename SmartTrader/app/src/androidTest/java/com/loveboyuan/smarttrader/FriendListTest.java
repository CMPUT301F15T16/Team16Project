package com.loveboyuan.smarttrader;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * Created by jiahui on 11/5/15.
 */
// covers use cases 6 and 15
public class FriendListTest extends TestCase {
    // covers use case 6
    public void testAddFriend() throws Exception {
        User friend = new User(0);
        FriendList friendList = new FriendList();
        friendList.addFriend(friend);

        assertTrue("FriendListTest: add new friend failed",
                friendList.getFriendList().contains(friend));

        friendList.addFriend(friend);
        int numFriendsWithSameId = Collections.frequency(friendList.getFriendList(), friend);
        assertTrue("FriendListTest: add redundant friend failed", numFriendsWithSameId == 1);
    }

    // covers use case 6
    public void testRemoveFriend() throws Exception {
        FriendList friendList = new FriendList();
        User friend = new User(0);
        friendList.addFriend(friend);
        friendList.removeFriend(friend);
        assertFalse("FriendListTest: remove friend failed",
                friendList.getFriendList().contains(friend));
    }

    public void testGetFriendList() throws Exception {
        FriendList friendList = new FriendList();
        User friend = new User(0);

        assertTrue("FriendListTest: size of friendlist is not 0 after initialization",
                friendList.getFriendList().size() == 0);

        friendList.addFriend(friend);

        assertTrue("FriendListTest: size of friendlist is 0 after adding friend",
                friendList.getFriendList().size() != 0);
    }

}