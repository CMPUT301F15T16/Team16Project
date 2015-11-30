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
    private PendingManager pendingManager = new PendingManager("");
    Pending pulledSent =null;
    Pending pulledReceived =null;
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    static User usr=LoginActivity.usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        ListView fakeActionBar = (ListView)findViewById(R.id.pendingActionBar);
        final DrawerListAdapter adapter = new DrawerListAdapter(this, generatePendingActionBar());
        fakeActionBar.setAdapter(adapter);

        PendingController.clear();

        String searchString = String.valueOf(usr.getMy_id());
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(pulledSent != null) {
            for (User user : pulledSent.getPendingSent()) {

                PendingController.getPendingModel().addPendingSent(user);
            }
        }else{
            // else we need to make sure we create a new pending sent list and push it to the server

            Pending pendingS = new Pending();
            pendingS.setPendingSentId(usr.getMy_id());
            Thread thread = new AddSThread(pendingS);
            thread.start();

        }

        if(pulledReceived != null) {
            for (User user : pulledReceived.getPendingSent()) {

                PendingController.getPendingModel().addPendingReceived(user);
            }
        }else{
            // else we need to make sure we create a new pending received list and push it to the server

            Pending pendingR = new Pending();
            pendingR.setPendingReceivedId(usr.getMy_id());
            Thread thread = new AddRThread(pendingR);
            thread.start();

        }

        ListView pendingSentView = (ListView) findViewById(R.id.pendingSentView);
        ListView pendingReceivedView = (ListView) findViewById(R.id.pendingReceivedView);

        Collection<User> usersSent = PendingController.getPendingModel().getPendingSent();
        Collection<User> usersReceived = PendingController.getPendingModel().getPendingReceived();

        final ArrayList<User> pendingSent = new ArrayList<User>(usersSent);
        final ArrayList<User> pendingReceived = new ArrayList<User>(usersReceived);

        final ArrayAdapter<User> pendingSentAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, pendingSent);
        final ArrayAdapter<User> pendingReceivedAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, pendingReceived);

        pendingSentView.setAdapter(pendingSentAdapter);
        pendingReceivedView.setAdapter(pendingReceivedAdapter);



        PendingController.getPendingModel().addMyObserver(new MyObserver() {
            @Override
            public void update() {
                pendingSent.clear();
                pendingReceived.clear();
                Collection<User> userSent = PendingController.getPendingModel().getPendingSent();
                Collection<User> userReceived = PendingController.getPendingModel().getPendingReceived();
                pendingSent.addAll(userSent);
                pendingReceived.addAll(userReceived);
                pendingSentAdapter.notifyDataSetChanged();
                pendingReceivedAdapter.notifyDataSetChanged();
            }
        });

        pendingSentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = pendingSent.get(position);
                Intent intent = new Intent(PendingActivity.this, ProfileActivity.class);
                intent.putExtra("USR",  user);
                intent.putExtra("ARD", "ard");
                startActivity(intent);
            }
        });

        pendingReceivedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = pendingReceived.get(position);
                Intent intent = new Intent(PendingActivity.this, ProfileActivity.class);
                intent.putExtra("USR",  user);
                intent.putExtra("ARD", "ard");
                startActivity(intent);
            }
        });
    }

    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }


        @Override
        public void run() {
            //  try {
            pulledSent = pendingManager.searchOwnPendingSent(search, null);
            pulledReceived = pendingManager.searchOwnPendingReceived(search, null);
            //     }catch (RuntimeException e){


            //  }
        }

    }


    class AddSThread extends Thread {
        private Pending pendingS;

        public AddSThread(Pending pending) {
            this.pendingS = pending;
        }

        @Override
        public void run() {

            PendingController.addPendingSent(pendingS);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }

    class AddRThread extends Thread {
        private Pending pendingR;

        public AddRThread(Pending pending) {
            this.pendingR = pending;
        }

        @Override
        public void run() {

            PendingController.addPendingReceived(pendingR);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }

    private ArrayList<DrawerListEntry> generatePendingActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("Pend"));

        return drawerListEntries;
    }

}
