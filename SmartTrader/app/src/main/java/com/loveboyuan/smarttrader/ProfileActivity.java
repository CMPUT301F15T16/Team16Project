package com.loveboyuan.smarttrader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ProfileActivity extends AppCompatActivity {
    private static Gson gson = new Gson();

    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    static int usrID=LoginActivity.usrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView textView = (TextView)findViewById(R.id.profileIdView);
        textView.setText(String.valueOf(usrID));
        try {
            int userID = getIntent().getExtras().getInt("USR_ID");
            textView.setText(String.valueOf(userID));
            if(userID != usrID){
                //set save button invisble instead set addFriend button visible
                Button saveButton = (Button) findViewById(R.id.profileSaveButton);

                Button addButton = (Button) findViewById(R.id.addFriendButton);

                addButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);

            }

        }catch (RuntimeException e){

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

/*
        profileName.setText(name);
        profileCity.setText(city);
        profileCell.setText(cell);
        profileEmail.setText(email);
*/

        User user = new User(usrID);
        user.setName(name);
        user.setCityName(city);
        user.setPhoneNumber(cell);
        user.setEmail(email);

        // Execute the thread to add this remotely
        Thread thread = new AddThread(user);
        thread.start();



    }

    public void add(View v){



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

                HttpPost addRequest = new HttpPost(User.getResourceUrl() + user.getId());

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

}
