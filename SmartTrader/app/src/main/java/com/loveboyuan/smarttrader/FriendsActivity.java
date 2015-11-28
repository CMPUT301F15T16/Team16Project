package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class FriendsActivity extends AppCompatActivity {
    private FriendListManager friendListManager = new FriendListManager("");
    FriendList pulledFriendList = new FriendList();


    // SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        FriendListController.clear();

        // We want to pull from server what friends the user has and add it to friendsController

        // So search first
        String searchString = "*";
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(User user: pulledFriendList.getFriendList()){

            FriendListController.getFriendListModel().addFriend(user);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addFriend(View v){

        Intent intent = new Intent(this, FriendSearchActivity.class);
        startActivity(intent);


    }

    public void searchInventory(View v){

        Intent intent = new Intent(this, SearchInventoryActivity.class);
        startActivity(intent);
    }



    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }

        @Override
        public void run(){
            ArrayList<User>users = friendListManager.searchOwnFriends(search, null).getFriendList();
            for(User user: users ) {
                pulledFriendList.addFriend(user);
            }
        }

    }

}
