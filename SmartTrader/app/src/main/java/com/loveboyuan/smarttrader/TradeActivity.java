package com.loveboyuan.smarttrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        Item item = (Item) getIntent().getSerializableExtra("ownerItem");



    }


    // The user will be able to initialize or edit the trade details here!
    public void editTrade(View view){



    }
    // views including a lot of details
    // This activity is opened with information passing in!



    public void offerItem(View view){
        Intent intent = new Intent(this, SelectInventoryItemActivity.class);
        startActivity(intent);

    }
}
