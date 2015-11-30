package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        ListView fakeActionBar = (ListView)findViewById(R.id.tradeHistoryActionBar);
        final DrawerListAdapter adapter = new DrawerListAdapter(this,
                generateTradeHistoryActionBar());
        fakeActionBar.setAdapter(adapter);

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




    }



    private ArrayList<DrawerListEntry> generateTradeHistoryActionBar() {
        ArrayList<DrawerListEntry> drawerListEntries = new ArrayList<>();

        drawerListEntries.add(new DrawerListEntry("History"));

        return drawerListEntries;
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
