package com.loveboyuan.smarttrader;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;


public class ItemActivity extends AppCompatActivity {


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


            Button addButton =(Button) findViewById(R.id.addButton);
            Button updateButton = (Button) findViewById(R.id.updateButton);
            addButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);


            String name = item.getName();
            String quality = item.getQuality();
            String category = item.getCategory();
            String description = item.getDescription();
            int quantity = item.getQuantity();
            EditText nameView = (EditText) findViewById(R.id.itemNameText);
            RadioGroup qualityRadios = (RadioGroup) findViewById(R.id.qualityRadioGroup);
            RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
            EditText descriptionView = (EditText) findViewById(R.id.descriptionText);



            nameView.setText(name);

            numberPicker.setValue(quantity);

            qualityRadios.check(R.id.mediumRadioButton);

            privacyRadios.check(R.id.publicRadioButton);

            spinner.setSelection(3);

            descriptionView.setText(description);


            Toast.makeText(ItemActivity.this, name, Toast.LENGTH_SHORT).show();
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
        quality = (String)qualityRadio.getText();
        // isPrivate
        RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
        RadioButton privacyRadio = (RadioButton) findViewById(privacyRadios.getCheckedRadioButtonId());
        String bool = (String)privacyRadio.getText();
        if(bool.equals("Public")){
            isPrivate = Boolean.FALSE;
        }else{
            isPrivate = Boolean.TRUE;
        }
        // description
        EditText descriptionView = (EditText) findViewById(R.id.descriptionText);
        description = descriptionView.getText().toString();
        // photopath will be null for now
        photoPath = "null";



        Item item = new Item(name, category, quantity, quality, isPrivate, description, photoPath);

        InventoryController.addItem(item);



    }
}
