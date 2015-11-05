package com.loveboyuan.smarttrader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class InventoryActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // getting the list view in the ui
        ListView inventoryListView = (ListView) findViewById(R.id.inventoryListView);
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
                String name = item.getName();
                String quality = item.getQuality();
                String category = item.getCategory();
                String description = item.getDescription();
                int quantity = item.getQuantity();
               // String quan = String.valueOf(quantity);


               // Toast.makeText(InventoryActivity.this, quan, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);
                intent.putExtra("MyItem", item);
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
                        InventoryController.removeItem(item);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
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

    // user wants to add an Item to his/her inventory
    public void addItem(View view){
        Intent intent = new Intent(InventoryActivity.this, ItemActivity.class);

        startActivity(intent);

    }


}
