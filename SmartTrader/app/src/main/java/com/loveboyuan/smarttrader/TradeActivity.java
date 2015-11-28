package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeActivity extends AppCompatActivity {
    static User usr=LoginActivity.usr;
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        Item item = (Item) getIntent().getSerializableExtra("ownerItem");

        TextView ownerItemTextView = (TextView) findViewById(R.id.ownerItemTextView);
        ownerItemTextView.setText(item.getName());

        ListView listView = (ListView) findViewById(R.id.borrowerItemsListView);

        try{
            Inventory selected = (Inventory) getIntent().getSerializableExtra("PASSITEMS");
            ArrayList<Item> items = selected.getInventory();
            final ArrayList<Item> list = new ArrayList<Item>(items);
            final ArrayAdapter<Item> inventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(inventoryAdapter);
        }catch (RuntimeException e){

        }



        Button tradeProposeButton = (Button) findViewById(R.id.tradeProposeButton);
        tradeProposeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here I should create the trade based on what i have in the views
                // I should push this trade to the server for both parties
                try {
                    Inventory selected = (Inventory) getIntent().getSerializableExtra("PASSITEMS");

                    Item item = (Item) getIntent().getSerializableExtra("ownerItem");
                    Trade trade = new Trade(item.getOwnerID(), item, usr.getMy_id(), selected.getInventory());

                    TradeHistoryController.getTradeHistory().addTrade(trade);

                    // Execute the thread to add this remotely
                    Thread thread = new AddThread(trade);
                    thread.start();

                }catch (RuntimeException e){

                }
            }
        });

    }


    class AddThread extends Thread {
        private Trade trade;

        public AddThread(Trade trade) {
            this.trade = trade;
        }

        @Override
        public void run() {

            TradeHistoryController.addTradeToServer(trade);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }


    // The user will be able to initialize or edit the trade details here!
    public void editTrade(View view){



    }


    public void offerItem(View view){
        Item item = (Item) getIntent().getSerializableExtra("ownerItem");

        Intent intent = new Intent(this, SelectInventoryItemActivity.class);
        intent.putExtra("NeedBack",item );
        startActivity(intent);

    }
}
