package com.loveboyuan.smarttrader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class FriendSearchActivity extends AppCompatActivity {
    final FriendList friendList = new FriendList();
    final Collection<User> users = friendList.getFriendList();
    private FriendListManager friendListManager = new FriendListManager("");

    private ArrayAdapter<User> searchFriendAdapter;

    final ArrayList<User> list = new ArrayList<User>(users);
    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        ListView fakeActionBar = (ListView)findViewById(R.id.friendSearchActionBar);
        final DrawerListAdapter adapter = new DrawerListAdapter(this,
                generateFriendSearchActionBar());
        fakeActionBar.setAdapter(adapter);

        ListView friendSearchListView = (ListView) findViewById(R.id.friendSearchListView);

        searchFriendAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, list);
        // inventoryListView set inventoryAdapter as its adaptor
        friendSearchListView.setAdapter(searchFriendAdapter);




        friendSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = list.get(position);

                Intent intent = new Intent(FriendSearchActivity.this, ProfileActivity.class);
                intent.putExtra("USR",  user);
                startActivity(intent);
            }


        });

    }


    public void search(View view) {
        String searchString = null;

        list.clear();

        // TODO: Extract search query from text view
        EditText editText = (EditText) findViewById(R.id.friendSearchText);
        searchString = editText.getText().toString();

        // TODO: Run the search thread

        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();


    }

    private ArrayList<DrawerListEntry> generateFriendSearchActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("Search"));

        return drawerListEntries;
    }

    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }

        @Override
        public void run(){
            list.clear();
            list.addAll(friendListManager.searchFriends(search, null).getFriendList());
            notifyUpdated();

        }

    }

    public void notifyUpdated() {
        // Thread to update adapter after an operation
        Runnable doUpdateGUIList = new Runnable() {
            public void run() {
                searchFriendAdapter.notifyDataSetChanged();
            }
        };

        runOnUiThread(doUpdateGUIList);
    }



}
