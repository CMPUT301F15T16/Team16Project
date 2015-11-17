package com.loveboyuan.smarttrader;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.Serializable;


public class ItemActivity extends AppCompatActivity {

    // Thread that close the activity after finishing add
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // We want to let the user choose the quantity of the item
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(1000);
        numberPicker.setMinValue(1);

        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

// In case of edit item in the inventory, the activity is started with message passed with. get intent!
        try {
            Item item = (Item) getIntent().getSerializableExtra("MyItem");

            String name = item.getName();
            if(!name.equals("")) {
                Button addButton = (Button) findViewById(R.id.addButton);

                Button updateButton = (Button) findViewById(R.id.updateButton);
                addButton.setVisibility(View.GONE);
                updateButton.setVisibility(View.VISIBLE);

                String quality = item.getQuality();
                String category = item.getCategory();
                String description = item.getDescription();
                int quantity = item.getQuantity();
                Boolean isPrivate = item.isPrivate();

                EditText nameView = (EditText) findViewById(R.id.itemNameText);
                RadioGroup qualityRadios = (RadioGroup) findViewById(R.id.qualityRadioGroup);
                RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
                EditText descriptionView = (EditText) findViewById(R.id.descriptionText);

                //set name to view
                nameView.setText(name);
                //set quantity to view
                numberPicker.setValue(quantity);

                //set description to view
                descriptionView.setText(description);

                if(quality.equals("Lightly Used")){
                    qualityRadios.check(R.id.mediumRadioButton);
                }else if(quality.equals("New")){
                    qualityRadios.check(R.id.newRadioButton);
                }else{
                    qualityRadios.check(R.id.oldRadioButton);
                }

                if(isPrivate == Boolean.TRUE) {
                    privacyRadios.check(R.id.privateRadioButton);
                }else{
                    privacyRadios.check(R.id.publicRadioButton);
                }

                int select = 0;
                switch (category) {
                    case "Computer Science": select = 0 ; break;
                    case "Mathematics": select = 1 ; break;
                    case "French": select = 2 ; break;
                    case "History": select = 3 ; break;
                    case "Economics": select = 4 ; break;
                    case "Philosophy": select = 5 ; break;
                    case "Anthropology": select = 6 ; break;
                    case "Legal Studies": select = 7 ; break;
                    case "Psychology": select = 8 ; break;
                    case "Nursing": select = 9 ; break;
                }
                spinner.setSelection(select);

            }
        } catch (RuntimeException exception){
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
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

    public void addItem(View view){
        try {
            String name, category, quality, description, photoPath;
            boolean isPrivate;
            int quantity;


            // name
            EditText nameView = (EditText) findViewById(R.id.itemNameText);
            name = nameView.getText().toString();
            // category
            Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
            category = spinner.getSelectedItem().toString();
            // quantity
            NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
            quantity = numberPicker.getValue();
            // quality
            RadioGroup qualityRadios = (RadioGroup) findViewById(R.id.qualityRadioGroup);
            RadioButton qualityRadio = (RadioButton) findViewById(qualityRadios.getCheckedRadioButtonId());
            quality = (String) qualityRadio.getText();
            // isPrivate
            RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
            RadioButton privacyRadio = (RadioButton) findViewById(privacyRadios.getCheckedRadioButtonId());
            String bool = (String) privacyRadio.getText();
            if (bool.equals("Public")) {
                isPrivate = Boolean.FALSE;
            } else {
                isPrivate = Boolean.TRUE;
            }
            // description
            EditText descriptionView = (EditText) findViewById(R.id.descriptionText);
            description = descriptionView.getText().toString();
            // photopath will be null for now
            photoPath = "null";


            Item item = new Item(name, category, quantity, quality, isPrivate, description, photoPath);

            // Execute the thread

            //InventoryController.addItem(item);
            InventoryController.getInventoryModel().addItem(item);
            Thread thread = new AddThread(item);
            thread.start();
        }catch (RuntimeException ignored){
        }

        this.finish();
    }

    class AddThread extends Thread {
        private Item item;

        public AddThread(Item item) {
            this.item = item;
        }

        @Override
        public void run() {

            InventoryController.addItem(item);


            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }



    public void goBackInventory(View v){
        this.finish();

    }


    public void updateItem(View v){
        Serializable item = getIntent().getSerializableExtra("MyItem");
        Item theItem = (Item) item;
        Inventory deleteList = new Inventory();
       // Toast.makeText(ItemActivity.this, theItem.getName(), Toast.LENGTH_SHORT).show();
        for (Item item1 :InventoryController.getInventoryModel().getInventory()){
            deleteList.addItem(item1);
        }
        for (Item item2: deleteList.getInventory()){
            InventoryController.removeItem(item2);

        }

        addItem(v);

    }


}
