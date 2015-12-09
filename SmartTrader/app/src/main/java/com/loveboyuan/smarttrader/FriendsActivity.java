package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class FriendsActivity extends AppCompatActivity {
    private FriendListManager friendListManager = new FriendListManager("");
    FriendList pulledFriendList =null;
    Inventory pulledInventory = null;
    private SearchInventoryManager searchInventoryManager = new SearchInventoryManager("");

    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    static User usr=LoginActivity.usr;


    // SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ListView fakeActionBar = (ListView)findViewById(R.id.friendsActionBar);
        final DrawerListAdapter adapter = new DrawerListAdapter(this, generateFriendsActionBar());
        fakeActionBar.setAdapter(adapter);

        FriendListController.clear();




        String searchString = String.valueOf(usr.getMy_id());
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        SearchInventoryThread searchInventoryThread = new SearchInventoryThread(searchString);
        searchInventoryThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(pulledFriendList != null) {
            for (User user : pulledFriendList.getFriendList()) {

                FriendListController.getFriendListModel().addFriend(user);
            }
        }else{
            // else we need to make sure we create a new friendlist and push it to the server

            FriendList friendList = new FriendList();
            friendList.setFriendListId(usr.getMy_id());
            Thread thread = new AddThread(friendList);
            thread.start();

        }

        if(pulledInventory != null) {
            for (Item item : pulledInventory.getInventory()) {

                InventoryController.getInventoryModel().addItem(item);
            }
        }


        ListView friendListView = (ListView)findViewById(R.id.friendListView);
        final Collection<User> users = FriendListController.getFriendListModel().getFriendList();
        final ArrayList<User> friendList = new ArrayList<User>(users);
        final ArrayAdapter<User> friendListAdapter =
                new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, friendList);
        friendListView.setAdapter(friendListAdapter);

        FriendListController.getFriendListModel().addMyObserver(new MyObserver() {
            @Override
            public void update() {
                friendList.clear();
                Collection<User> user = FriendListController.getFriendListModel().getFriendList();
                friendList.addAll(user);
                friendListAdapter.notifyDataSetChanged();
            }
        });



        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = friendList.get(position);
                Intent intent = new Intent(FriendsActivity.this, ProfileActivity.class);
                intent.putExtra("USR",  user);
                intent.putExtra("ARD", "ard");
                startActivity(intent);

            }
        });

    }




    public void addFriend(View v){

        Intent intent = new Intent(this, FriendSearchActivity.class);
        startActivity(intent);


    }

    public void searchInventory(View v){

        Intent intent = new Intent(this, SearchInventoryActivity.class);
        startActivity(intent);
    }

    private ArrayList<DrawerListEntry> generateFriendsActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("Friends"));

        return drawerListEntries;
    }

    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }


        @Override
        public void run() {
                pulledFriendList = friendListManager.searchOwnFriends(search, null);

        }

    }


    class AddThread extends Thread {
        private FriendList friendList;

        public AddThread(FriendList friendList) {
            this.friendList = friendList;
        }

        @Override
        public void run() {

            FriendListController.addFriendList(friendList);


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }



    class SearchInventoryThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchInventoryThread(String search){
            this.search = search;

        }


        @Override
        public void run() {
            pulledInventory = searchInventoryManager.searchOwnInventory(search, null);

        }

    }

}
