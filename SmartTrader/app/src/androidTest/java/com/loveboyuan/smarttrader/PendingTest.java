package com.loveboyuan.smarttrader;

import junit.framework.TestCase;
import java.util.Collections;

/**
 * Created by bstang on 11/5/2015.
 *
 * This contains the test cases for the Pending class. It tests the methods used within the class.
 */
public class PendingTest extends TestCase {

    public void testAcceptFriend() throws Exception {
        User pending_friend = new User(0);
        FriendList friendList = new FriendList();
        FriendListController.getFriendListModel().addFriend(pending_friend);

        assertTrue("PendingTest: accepting friend failed", FriendListController.getFriendListModel().getFriendList().contains(pending_friend));
    }

    public void testAcceptAllFriends() throws Exception {
        User pending_friend = new User(0);
        FriendList friendList = new FriendList();
        //FriendListController.getFriendListModel().addAllFriends(pending_friend);

        assertTrue("PendingTest: accepting friend failed", FriendListController.getFriendListModel().getFriendList().contains(pending_friend));
    }

    public void testAddPending() throws Exception {
        User pending_friend = new User(0);
        Pending pending = new Pending();
        pending.addPending(pending_friend);

        //ASK ABOUT THIS
        assertTrue("PendingTest: add pending friend failed", pending.getPendingSent().contains(pending_friend));

        pending.addPending(pending_friend);
        int checkSame = Collections.frequency(pending.getPendingSent(), pending_friend);
        assertTrue("PendingTest: add same pending failed", checkSame == 1);
    }

    public void testRemoveRequest() throws Exception {
        Pending pending = new Pending();
        User pending_friend = new User(0);
        pending.addPending(pending_friend);
        pending.removeRequest(pending_friend);
        assertFalse("PendingTest: remove request failed", pending.getPendingReceived().contains(pending_friend));
    }


    public void testGetPendingSent() throws Exception {
        Pending pending = new Pending();
        User pending_friend = new User(0);

        assertTrue("PendingTest: pending is not empty",
                pending.getPendingSent().size() == 0);

        pending.addPending(pending_friend);

        assertTrue("PendingTest: pending is empty after adding pending friend",
                pending.getPendingSent().size() != 0);
    }

    public void testGetPendingReceived() throws Exception {
        Pending pending = new Pending();
        User pending_friend = new User(0);

        assertTrue("PendingTest: pending is not empty",
                pending.getPendingReceived().size() == 0);

        pending.addPending(pending_friend);

        assertTrue("PendingTest: pending is empty after adding pending friend",
                pending.getPendingReceived().size() != 0);
    }

}

