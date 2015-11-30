package com.loveboyuan.smarttrader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class InventoryActivity extends AppCompatActivity{
    // Thread that close the activity after finishing add
    private static final String TAG = "InventorySearch";

    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };
    static User usr=LoginActivity.usr;
    private SearchInventoryManager searchInventoryManager = new SearchInventoryManager("");

    private Inventory pulledInventory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ListView fakeActionBar = (ListView)findViewById(R.id.inventoryActionBar);
        final DrawerListAdapter adapter = new DrawerListAdapter(this, generateInventoryActionBar());
        fakeActionBar.setAdapter(adapter);


        InventoryController.clear();

        ListView inventoryListView = (ListView) findViewById(R.id.inventoryListView);

        // We want to pull from server the user's inventory and concurrent it with controller

        // So search first
        String searchString = String.valueOf(usr.getMy_id());
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();



        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // If the returned inventory is not null:
        if(pulledInventory != null) {
            for (Item item : pulledInventory.getInventory()) {

                InventoryController.getInventoryModel().addItem(item);
            }
        }else{
            // else we need to make sure we create a new inventory and push it to the server

            Inventory inventory = new Inventory();
            inventory.setInventoryId(usr.getMy_id());
            Thread thread = new AddThread(inventory);
            thread.start();

        }




        // items contains all items in the inventory
        Collection<Item> items = InventoryController.getInventoryModel().getInventory();
        // list contains items
        final ArrayList<Item> list = new ArrayList<Item>(items);
        // inventoryAdapter is an array Adapter to fit data in the list view
        final ArrayAdapter<Item> inventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        // inventoryListView set inventoryAdapter as its adaptor
        inventoryListView.setAdapter(inventoryAdapter);


        // we want a observer for the inventory model here. notify data change, and update the view
        InventoryController.getInventoryModel().addMyObserver(new MyObserver() {
            @Override
            public void update() {
                list.clear();
                Collection<Item> item = InventoryController.getInventoryModel().getInventory();
                list.addAll(item);
                inventoryAdapter.notifyDataSetChanged();
            }
        });


        // show inventory item and let user modify item attributes
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = list.get(position);



                Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
                Location location = item.getLocation();
                item.setLocation(null);
                intent.putExtra("MyItem", item);
                intent.putExtra("Location", location);

                // This is to check if the user is browsing other people's inventory
                if(item.getOwnerID() != usr.getMy_id()) {
                    intent.putExtra("OTH", "others");
                }
                startActivity(intent);

            }


        });


        // remove inventory item
        inventoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(InventoryActivity.this);
                adb.setMessage("Delete " + list.get(position).toString() + "?");
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item = list.get(position);
                        InventoryController.getInventoryModel().removeItem(item);

                      //  Thread thread1 = new RemoveThread(InventoryController.getInventoryModel());
                       // thread1.start();
                        Thread thread = new AddThread(InventoryController.getInventoryModel());
                        thread.start();

                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();


                return false;
            }
        });
    }



    // user wants to add an Item to his/her inventory
    public void addItem(View view){
        Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);

        startActivity(intent);

    }

    private ArrayList<DrawerListEntry> generateInventoryActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("Stock"));

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
            pulledInventory = searchInventoryManager.searchOwnInventory(search, null);

        }

    }


    class AddThread extends Thread {
        private Inventory inventory;

        public AddThread(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            InventoryController.addInventory(inventory);


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
