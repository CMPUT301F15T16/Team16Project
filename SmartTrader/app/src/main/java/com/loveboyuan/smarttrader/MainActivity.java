package com.loveboyuan.smarttrader;

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

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first we want to get the string representation of the clicked text
                String activityName = mAdapter.getItem(position).toString();
                Toast.makeText(MainActivity.this, activityName, Toast.LENGTH_SHORT).show();
                // then get the actual class name with corresponding prefix(package name)
                // and postfix(Activity)
                activityName = "com.loveboyuan.smarttrader." + activityName + "Activity";
                Intent intent = null;
                // Note here we launch an activity based on the string representation of it
                try {
                    // Class.forName yields the Class object associated with the class or interface
                    // with the given string name
                    // from https://docs.oracle.com/javase/7/docs/api/java/lang/Class.html
                    intent = new Intent(MainActivity.this, Class.forName(activityName));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    // this exception could be made impossible to trigger
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, UserSettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {
        String[] osArray = { "Profile", "Pending" , "Inventory", "Friends", "TradeHistory" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }
}
