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

import java.util.ArrayList;
import java.util.Collection;

public class TradeHistoryActivity extends AppCompatActivity {
    static User usr=LoginActivity.usr;
    private SearchTradeHistoryManager searchTradeHistoryManager = new SearchTradeHistoryManager("");
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };
    private TradeHistory pulledTradeHistory = new TradeHistory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history);

        TradeHistoryController.clear();

        ListView tradeHistoryListView = (ListView) findViewById(R.id.tradeHistoryListView);

        String searchString = "*";
        SearchThread searchThread = new SearchThread(searchString);
        searchThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Trade trade: pulledTradeHistory.getTrades()){

            TradeHistoryController.getTradeHistory().addTrade(trade);
        }






        // items contains all items in the inventory
        Collection<Trade> trades = TradeHistoryController.getTradeHistory().getTrades();
        // list contains items
        final ArrayList<Trade> list = new ArrayList<Trade>(trades);
        // inventoryAdapter is an array Adapter to fit data in the list view
        final ArrayAdapter<Trade> tradeHistoryAdapter = new ArrayAdapter<Trade>(this, android.R.layout.simple_list_item_1, list);
        // inventoryListView set inventoryAdapter as its adaptor
        tradeHistoryListView.setAdapter(tradeHistoryAdapter);


        // we want a observer for the inventory model here. notify data change, and update the view
        TradeHistoryController.getTradeHistory().addMyObserver(new MyObserver() {
            @Override
            public void update() {
                list.clear();
                Collection<Trade> trades1 = TradeHistoryController.getTradeHistory().getTrades();
                list.addAll(trades1);
                tradeHistoryAdapter.notifyDataSetChanged();
            }
        });


        // show inventory item and let user modify item attributes
        tradeHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Trade trade = list.get(position);



                Intent intent = new Intent(TradeHistoryActivity.this, TradeActivity.class);
                intent.putExtra("MyTrade", trade);
                if(trade.getOwner() != usr.getMy_id()) {
                    intent.putExtra("OTH", "others");
                }
                startActivity(intent);

            }


        });


        // remove inventory item
        tradeHistoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb = new AlertDialog.Builder(TradeHistoryActivity.this);
                adb.setMessage("Delete " + list.get(position).toString() + "?");
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Trade trade = list.get(position);
                        TradeHistoryController.getTradeHistory().removeTrade(trade);
                        // Execute the thread to add this remotely
                        Thread thread = new RemoveThread(trade);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_history, menu);
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


    class SearchThread extends Thread {
        // TODO: Implement search thread

        private String search;

        public SearchThread(String search){
            this.search = search;

        }

        @Override
        public void run(){
            ArrayList<Trade> trades = searchTradeHistoryManager.searchOwnTradeHistory(search, null).getTrades();
            for(Trade trade: trades) {
                pulledTradeHistory.addTrade(trade);
            }
        }

    }


}
