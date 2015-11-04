package com.loveboyuan.smarttrader;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


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
