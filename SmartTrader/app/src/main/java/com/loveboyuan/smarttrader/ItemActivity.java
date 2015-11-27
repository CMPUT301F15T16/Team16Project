package com.loveboyuan.smarttrader;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "Locationlatitude";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private int MEDIA_TYPE_IMAGE = 1;
    private ImageView imageView;


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


      //  UserLocation.startTracking(this);
        // We want to let the user choose the quantity of the item
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(1000);
        numberPicker.setMinValue(1);
        imageView = (ImageView) findViewById(R.id.itemIV);

        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPhoto();
                return true;
            }
        });

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
    //Taken from android developers website
    //http://developer.android.com/guide/topics/media/camera.html#manifest
    public void setPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = ItemController.getOutputMediaFileUri(MEDIA_TYPE_IMAGE,this.getBaseContext());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Taken from android developers website
    //http://developer.android.com/guide/topics/media/camera.html#intent-receive
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
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
            String name, category, quality, description;
            Uri photoPath;
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
            photoPath = null;

            Item item = new Item(name, category, quantity, quality, isPrivate, description, photoPath);
         //   UserLocation.setItemLocation(item);
          //  Log.e(TAG, (String.valueOf(item.getLocation().getLatitude())));


            InventoryController.getInventoryModel().addItem(item);

            // Execute the thread to add this remotely
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
            if(item1.getName().equalsIgnoreCase(theItem.getName())) {
                deleteList.addItem(item1);
            }
        }
        for (Item item2: deleteList.getInventory()){

            InventoryController.getInventoryModel().removeItem(item2);
            // Execute the thread to add this remotely
            Thread thread = new RemoveThread(item2);
            thread.start();

        }

        addItem(v);

    }

    class RemoveThread extends Thread {
        private Item item;

        public RemoveThread(Item item) {
            this.item = item;
        }

        @Override
        public void run() {

            InventoryController.removeItem(item);


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishAdd);
        }
    }

}
