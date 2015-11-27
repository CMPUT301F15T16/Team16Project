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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;

public class SearchInventoryActivity extends AppCompatActivity {

    final Inventory friendsInventory= new Inventory();
    final Collection<Item> inventory = friendsInventory.getInventory();
    private SearchInventoryManager searchInventoryManager = new SearchInventoryManager("");

    private ArrayAdapter<Item> searchInventoryAdapter;

    final ArrayList<Item> list = new ArrayList<Item>(inventory);
    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_inventory);

        Spinner spinner = (Spinner) findViewById(R.id.searchCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);




        ListView inventorySearchListView = (ListView) findViewById(R.id.inventorySearchListView);

        searchInventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
        // inventoryListView set inventoryAdapter as its adaptor
        inventorySearchListView.setAdapter(searchInventoryAdapter);




        inventorySearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item = list.get(position);

                Intent intent = new Intent(SearchInventoryActivity.this, ItemActivity.class);
                intent.putExtra("MyItem", item);
                intent.putExtra("OTH", "others");
                startActivity(intent);
            }


        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_inventory, menu);
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

    public void searchFriendsInventory(View v){
        String searchString = null;
        Spinner spinner = (Spinner) findViewById(R.id.searchCategorySpinner);

        String category = spinner.getSelectedItem().toString();
        searchString = category;
        list.clear();


        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();


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
            list.addAll(searchInventoryManager.searchInventory(search, null).getInventory());
            notifyUpdated();

        }

    }

    public void notifyUpdated() {
        // Thread to update adapter after an operation
        Runnable doUpdateGUIList = new Runnable() {
            public void run() {
                searchInventoryAdapter.notifyDataSetChanged();
            }
        };

        runOnUiThread(doUpdateGUIList);
    }

}
