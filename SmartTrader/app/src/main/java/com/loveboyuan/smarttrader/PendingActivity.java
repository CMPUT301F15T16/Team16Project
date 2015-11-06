package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by bstang on 11/5/2015.
 *
 * This is the view for the Pending model. It contains a list view of pending friend requests,
 * you can click on a pending request to view the profile od the user(needs to be implemented,
 * goes to our profile at the moment.)
 * There is also a button to accept all friends. Buttons for accepting/removing single requests
 * needs to be added in.
 */

public class PendingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        ListView pendingSentView = (ListView) findViewById(R.id.pendingSentView);
        ListView pendingReceivedView = (ListView) findViewById(R.id.pendingReceivedView);

        Collection<User> usersSent = PendingController.getPendingModel().getPendingSent();
        Collection<User> usersReceived = PendingController.getPendingModel().getPendingReceived();

        final ArrayList<User> pendingSent = new ArrayList<User>(usersSent);
        final ArrayList<User> pendingReceived = new ArrayList<User>(usersReceived);

        final ArrayAdapter<User> pendingSentAdapter = new ArrayAdapter<User>(this, android.R.layout.activity_list_item, pendingSent);
        final ArrayAdapter<User> pendingReceivedAdapter = new ArrayAdapter<User>(this, android.R.layout.activity_list_item, pendingReceived);

        pendingSentView.setAdapter(pendingSentAdapter);
        pendingReceivedView.setAdapter(pendingReceivedAdapter);


        Button acceptAll = (Button)findViewById(R.id.acceptAllFriendsButton);
        acceptAll.setOnClickListener(acceptAllListener);

        Button cancelAll = (Button)findViewById(R.id.cancelAllRequestsButton);
        cancelAll.setOnClickListener(cancelAllListener);


        PendingController.getPendingModel().addMyObserver(new MyObserver() {
            @Override
            public void update() {
                pendingSent.clear();
                pendingReceived.clear();
                Collection<User> userSent = PendingController.getPendingModel().getPendingSent();
                Collection<User> userReceived = PendingController.getPendingModel().getPendingReceived();
                userSent.addAll(userSent);
                userReceived.addAll(userReceived);
                pendingSentAdapter.notifyDataSetChanged();
                pendingReceivedAdapter.notifyDataSetChanged();
            }
        });

        pendingSentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PendingActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        pendingReceivedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PendingActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }


    private OnClickListener acceptAllListener = new OnClickListener() {
        public void onClick(View v) {
            ArrayList<User> pending = PendingController.getPendingModel().getPendingReceived();
            PendingController.getPendingModel().acceptAllFriends(pending);
        }
    };

    private OnClickListener cancelAllListener = new OnClickListener() {
        public void onClick(View v) {
            ArrayList<User> pending = PendingController.getPendingModel().getPendingSent();
            PendingController.getPendingModel().cancelAllRequests(pending);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending, menu);
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
            Intent intent = new Intent(this, UserSettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
