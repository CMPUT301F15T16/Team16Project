package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class SelectInventoryItemActivity extends AppCompatActivity {
    static User usr=LoginActivity.usr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_inventory_item);

        final ListView listView = (ListView)findViewById(R.id.selectListView);
        Collection<Item> items = InventoryController.getInventoryModel().getInventory();
        final ArrayList<Item> list = new ArrayList<Item>(items);
        final ArrayAdapter<Item> inventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(inventoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.isItemChecked(position)) {
                    listView.setItemChecked(position, true);
                } else {
                    listView.setItemChecked(position, false);

                }
            }


        });


        Button button = (Button) findViewById(R.id.gobackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectInventoryItemActivity.this, TradeActivity.class);
                Item item = (Item) getIntent().getSerializableExtra("NeedBack");

                intent.putExtra("ownerItem", item);


                Inventory passItems = new Inventory();
                SparseBooleanArray checked = listView.getCheckedItemPositions();

                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        passItems.getInventory().add(list.get(i));
                    }
                }

                intent.putExtra("PASSITEMS",passItems);

                startActivity(intent);


            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_inventory_item, menu);
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



}
