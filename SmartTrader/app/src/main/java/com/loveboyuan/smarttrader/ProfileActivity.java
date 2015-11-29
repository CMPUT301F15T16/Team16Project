package com.loveboyuan.smarttrader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static Gson gson = new Gson();
    private FriendListManager friendListManager = new FriendListManager("");
    FriendList pulledFriendList = new FriendList();


    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    static User usr=LoginActivity.usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView textView = (TextView)findViewById(R.id.profileIdView);
        textView.setText(String.valueOf(usr.getMy_id()));

        EditText profileName = (EditText) findViewById(R.id.profileNameEditText);
        EditText profileCity = (EditText) findViewById(R.id.profileCityEditText);
        EditText profileCell = (EditText) findViewById(R.id.profileCellEditText);
        EditText profileEmail = (EditText) findViewById(R.id.profileEmailEditText);

        profileName.setText(usr.getName());
        profileCity.setText(usr.getCityName());
        profileCell.setText(usr.getPhoneNumber());
        profileEmail.setText(usr.getEmail());

        try {
            User user = (User) getIntent().getSerializableExtra("USR");
            int userID = user.getMy_id();
            textView.setText(String.valueOf(userID));
            if(userID != usr.getMy_id()){
                //set save button invisble instead set addFriend button visible
                Button saveButton = (Button) findViewById(R.id.profileSaveButton);

                Button addButton = (Button) findViewById(R.id.addFriendButton);


                addButton.setVisibility(View.VISIBLE);

                saveButton.setVisibility(View.GONE);


                profileName.setText(user.getName());
                profileCity.setText(user.getCityName());
                profileCell.setText(user.getPhoneNumber());
                profileEmail.setText(user.getEmail());

                profileName.setFocusable(false);
                profileCity.setFocusable(false);
                profileCell.setFocusable(false);
                profileEmail.setFocusable(false);

            }


        }catch (RuntimeException e){

        }

        try{
            String ard = getIntent().getStringExtra("ARD");
            if(ard.equals("ard")){
                Button addButton = (Button) findViewById(R.id.addFriendButton);
                addButton.setVisibility(View.GONE);
                Button cancelButton = (Button)findViewById(R.id.profileCancelButton);
                cancelButton.setVisibility(View.GONE);
                Button deleteButton = (Button) findViewById(R.id.deleteFriendButton);
                deleteButton.setVisibility(View.VISIBLE);

            }
        }catch (RuntimeException e2){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    public void save(View v){
        EditText profileName = (EditText) findViewById(R.id.profileNameEditText);
        EditText profileCity = (EditText) findViewById(R.id.profileCityEditText);
        EditText profileCell = (EditText) findViewById(R.id.profileCellEditText);
        EditText profileEmail = (EditText) findViewById(R.id.profileEmailEditText);

        String name, city, cell, email;
        name = profileName.getText().toString();
        city = profileCity.getText().toString();
        cell = profileCell.getText().toString();
        email = profileEmail.getText().toString();


        usr.setName(name);
        usr.setEmail(email);
        usr.setPhoneNumber(cell);
        usr.setCityName(city);
        profileName.setText(name);
        profileCity.setText(city);
        profileCell.setText(cell);
        profileEmail.setText(email);


        User user = new User(usr.getMy_id());
        user.setName(name);
        user.setCityName(city);
        user.setPhoneNumber(cell);
        user.setEmail(email);

        // Execute the thread to add this remotely
        Thread thread = new AddThread(user);
        thread.start();



    }

    public void add(View v){
        User user = (User) getIntent().getSerializableExtra("USR");

        // Pending stuff:
        //PendingController.addPending(user);
        if(checkUserNotAdded(user)) {
            FriendListController.getFriendListModel().addFriend(user);

            Thread thread = new AddFThread(user);
            thread.start();
        }else{
            Toast.makeText(ProfileActivity.this, "Already your Friend!",Toast.LENGTH_SHORT).show();

        }

    }

    private boolean checkUserNotAdded(User user) {
        // So search first
        pulledFriendList.getFriendList().clear();

        String searchString = "*";
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(User user1: pulledFriendList.getFriendList()){
            if(user1.getMy_id()==user.getMy_id()){
                return false;
            }
        }

        return true;


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


    class AddFThread extends Thread {
        private User user;

        public AddFThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {

            HttpClient httpClient = new DefaultHttpClient();

            try {

                HttpPost addRequest = new HttpPost(("http://cmput301.softwareprocess.es:8080/cmput301f15t16/" +
                        "Friends").concat(String.valueOf(usr.getMy_id())).concat("/"));

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



    public void deleteFriend(View view){
        User user = (User) getIntent().getSerializableExtra("USR");
       // FriendListController.getFriendListModel().removeFriend(user);

        // I want to search my server friendlist
        pulledFriendList.getFriendList().clear();

        String searchString = "*";
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(final User user1: pulledFriendList.getFriendList()){
            if(user1.getMy_id()==user.getMy_id()){
                AlertDialog.Builder adb = new AlertDialog.Builder(ProfileActivity.this);
                adb.setPositiveButton("Delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   FriendListController.getFriendListModel().removeFriend(user1);
                        Thread thread = new RemoveThread(user1);
                        thread.start();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();


            }
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
            ArrayList<User> users = friendListManager.searchOwnFriends(search, null).getFriendList();
            for(User user: users ) {
                pulledFriendList.addFriend(user);
            }
        }

    }

    class RemoveThread extends Thread {
        private User user;

        public RemoveThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {

            FriendListController.removeFriend(user);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }
}
