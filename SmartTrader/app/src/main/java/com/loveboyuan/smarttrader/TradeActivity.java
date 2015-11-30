package com.loveboyuan.smarttrader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        TextView ownerItemTextView = (TextView) findViewById(R.id.ownerItemTextView);

        // Here is because origin Trade button on item being pressed
        try {
            Item item = (Item) getIntent().getSerializableExtra("ownerItem");
            ownerItemTextView.setText(item.getName());
        }catch (RuntimeException e){

        }

        ListView listView = (ListView) findViewById(R.id.borrowerItemsListView);


        // Here its getting back the borrower items!
        try{
            Inventory selected = (Inventory) getIntent().getSerializableExtra("PASSITEMS");
            ArrayList<Item> items = selected.getInventory();
            final ArrayList<Item> list = new ArrayList<Item>(items);
            final ArrayAdapter<Item> inventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(inventoryAdapter);
        }catch (RuntimeException e2){

        }

        // Here is Trade View, come from tradeHistory!
        try{
            Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");
            // if we get the trade, that means we can edit the trade. it has two sides which side we are in ?
            // borrower? or owner? How could we tell?
            Button proposeTradeButton = (Button) findViewById(R.id.tradeProposeButton);
            Button acceptTradeButton = (Button) findViewById(R.id.acceptTradeButton);
            Button declineTradeButton = (Button) findViewById(R.id.declineTradeButton);
            Button deleteTradeButton = (Button) findViewById(R.id.deleteTradeButton);

            EditText ownerCommentView = (EditText) findViewById(R.id.ownerCommentEditText);


            ownerItemTextView.setText(trade.getOItem().getName());

            final ArrayList<Item> list = (ArrayList<Item>) trade.getBItems();
            final ArrayAdapter<Item> inventoryAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(inventoryAdapter);

            if(trade.getOItem().getOwnerID() == usr.getMy_id()){
                // that means im the owner
                proposeTradeButton.setVisibility(View.GONE);
                acceptTradeButton.setVisibility(View.VISIBLE);
                declineTradeButton.setVisibility(View.VISIBLE);
               // ownerCommentView.setVisibility(View.VISIBLE);

            }else{
                // that means im the borrower
                proposeTradeButton.setVisibility(View.GONE);
                deleteTradeButton.setVisibility(View.VISIBLE);

            }


        }catch (RuntimeException e3){

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




    public void offerItem(View view){
        Item item = (Item) getIntent().getSerializableExtra("ownerItem");

        Intent intent = new Intent(this, SelectInventoryItemActivity.class);
        intent.putExtra("NeedBack",item );
        startActivity(intent);

    }


    public void accept(View view){
        // Change the trade status to in-progress, enable complete button Emails should be sent!
        Button completeTradeButton = (Button) findViewById(R.id.completeTradeButton);
        Button acceptTradeButton = (Button) findViewById(R.id.acceptTradeButton);
        Button declineTradeButton = (Button) findViewById(R.id.declineTradeButton);

        completeTradeButton.setVisibility(View.VISIBLE);
        acceptTradeButton.setVisibility(View.GONE);
        declineTradeButton.setVisibility(View.GONE);

        Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");
        trade.setTradeState("inProgress");
        trade.setTradeResult(true);

    }


    public void decline(View view){

        Button completeTradeButton = (Button) findViewById(R.id.completeTradeButton);
        Button acceptTradeButton = (Button) findViewById(R.id.acceptTradeButton);
        Button declineTradeButton = (Button) findViewById(R.id.declineTradeButton);

        completeTradeButton.setVisibility(View.VISIBLE);
        acceptTradeButton.setVisibility(View.GONE);
        declineTradeButton.setVisibility(View.GONE);

        final Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");
        trade.setTradeState("complete");
        trade.setTradeResult(Boolean.FALSE);


        // ask owner if wants to initialize a counter trade
        AlertDialog.Builder adb = new AlertDialog.Builder(TradeActivity.this);
        adb.setPositiveButton("Initialize Counter Trade", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //Create a new trade object and push it to the server
                // Happily, I dont need to go to other intent
                ArrayList<Item>items = new ArrayList<Item>();
                items.add(trade.getOItem());

                ArrayList<Item> itemm = (ArrayList < Item >) trade.getBItems();
                Item myItem = itemm.get(0);

                Trade counterTrade = new Trade(trade.getBorrower(), myItem, trade.getOwner(),items);

                TradeHistoryController.getTradeHistory().addTrade(counterTrade);

                // Execute the thread to add this remotely
                Thread thread = new AddThread(counterTrade);
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



    public void update(View view){
        Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");


    }


    public void delete(View view){
        // Delete post!
        final Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");

        RemoveThread thread = new RemoveThread(trade);
        thread.start();


    }



    public void complete(View view){
        Trade trade = (Trade) getIntent().getSerializableExtra("MyTrade");
        trade.setTradeState("complete");


    }



    class RemoveThread extends Thread {
        private Trade trade;

        public RemoveThread(Trade trade) {
            this.trade = trade;
        }

        @Override
        public void run() {
            TradeHistoryController.removeTradeFromServer(trade);

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
