package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private static Gson gson = new Gson();
    final ArrayList<User>searchId = new ArrayList<User>();
    private FriendListManager friendListManager = new FriendListManager("");
    private static final String TAG = "FriendSearch";



    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    public static User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ListView fakeActionBar = (ListView)findViewById(R.id.listView);
        final DrawerListAdapter adapter = new DrawerListAdapter(this, generateLoginActionBar());
        fakeActionBar.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    // The user press the login button
    public void login(View v) throws InterruptedException {


        TextView textView = (TextView)findViewById(R.id.usrID);

        int usrID = Integer.parseInt(textView.getText().toString());

        // I want to search server to see if its a new user or old user
        String searchString = String.valueOf(usrID);
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        Thread.sleep(500);


        if (searchId.size()!=0){
            usr = searchId.get(0);

        }else{
            usr = new User(usrID);

            // Execute the thread to add this remotely
            Thread thread = new AddThread(usr);
            thread.start();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    // used to fill in the fake LoginActionBar
    private ArrayList<DrawerListEntry> generateLoginActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("Login"));

        return drawerListEntries;
    }

    class AddThread extends Thread {
        private User user;

        public AddThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {

            HttpClient httpClient = new DefaultHttpClient();

            try {

                HttpPost addRequest = new HttpPost(User.getResourceUrl() + user.getMy_id());

                StringEntity stringEntity = new StringEntity(gson.toJson(user));

                addRequest.setEntity(stringEntity);
                addRequest.setHeader("Accept", "application/json");

                HttpResponse response = httpClient.execute(addRequest);
                String status = response.getStatusLine().toString();


            } catch (Exception e) {
                e.printStackTrace();
            }


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }


    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }

        @Override
        public void run(){
            searchId.clear();
            try {
                searchId.addAll(friendListManager.searchFriends(search, null).getFriendList());
            }catch (RuntimeException e){


            }
        }

    }



}
