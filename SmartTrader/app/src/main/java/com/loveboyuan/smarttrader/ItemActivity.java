package com.loveboyuan.smarttrader;


import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "Locationlatitude";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String photo = null;
    private Bitmap bitmap;
    private int MEDIA_TYPE_IMAGE = 1;
    private ImageView imageView;
    private View.OnClickListener update;
    private View.OnClickListener add;
    private EditText lat;
    private EditText longit;


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
        update = new View.OnClickListener(){
            public void onClick(View v){

                updateItem(v);
            }
        };
        add = new View.OnClickListener(){
            public void onClick(View v){
                addItem(v);
            }
        };


        UserLocation.startTracking(this);
        // We want to let the user choose the quantity of the item
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo == null) {
                    Toast.makeText(ItemActivity.this,"Please long click to edit item image",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ItemActivity.this,ImageViewActivity.class);
                    intent.putExtra("Item Photo",photo);
                    startActivity(intent);
                }
            }
        });

        // In case of edit item in the inventory, the activity is started with message passed with. get intent!
        try {
            Item item = (Item)getIntent().getExtras().getSerializable("MyItem");

            Location location = (Location) getIntent().getExtras().get("Location");

            item.setLocation(location);

            String name = item.getName();
            if(!name.equals("")) {
                Button updateButton = (Button) findViewById(R.id.addButton);
                EditText lat = (EditText) findViewById(R.id.latitude);
                EditText longit = (EditText) findViewById(R.id.longitude);
                updateButton.setText("Save");
                updateButton.setOnClickListener(update);
                lat.setText(String.valueOf(item.getLocation().getLatitude()));
                longit.setText(String.valueOf(item.getLocation().getLongitude()));

                String quality = item.getQuality();
                String category = item.getCategory();

                String description = item.getDescription();
                int quantity = item.getQuantity();
                Boolean isPrivate = item.isPrivate();
                photo = item.getPhoto();
                if (!(photo == null)) {
                    bitmap = InventoryController.StringToBitMap(photo);
                    imageView.setImageBitmap(bitmap);
                }

                EditText nameView = (EditText) findViewById(R.id.itemNameText);
                RadioGroup qualityRadios = (RadioGroup) findViewById(R.id.qualityRadioGroup);
                RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
                EditText descriptionView = (EditText) findViewById(R.id.descriptionText);

                //set name to view
                nameView.setText(name);
                //set quantity to view

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
            //Toast.makeText(ItemActivity.this, "not working "+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            String s = getIntent().getStringExtra("OTH");
            if(s.equals("others")){
                EditText nameView = (EditText) findViewById(R.id.itemNameText);
                RadioGroup qualityRadios = (RadioGroup) findViewById(R.id.qualityRadioGroup);
                RadioGroup privacyRadios = (RadioGroup) findViewById(R.id.privacyRadioGroup);
                EditText descriptionView = (EditText) findViewById(R.id.descriptionText);

                nameView.setFocusable(false);
                qualityRadios.setEnabled(false);
                privacyRadios.setEnabled(false);
                descriptionView.setFocusable(false);
                Button updateButton = (Button) findViewById(R.id.updateButton);
                updateButton.setVisibility(View.GONE);
                spinner.setEnabled(false);
                Button tradeButton = (Button) findViewById(R.id.tradeButton);
                tradeButton.setVisibility(View.VISIBLE);
                Button cancelButton = (Button) findViewById(R.id.cancelButton);
                cancelButton.setVisibility(View.GONE);

            }



        }catch (RuntimeException e){

        }

    }
    //Taken from android developers website
    //http://developer.android.com/guide/topics/media/camera.html#manifest
    public void setPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //Taken from android developers website
    //http://developer.android.com/guide/topics/media/camera.html#intent-receive
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(bitmap);
                photo = InventoryController.convertBitMapToString(bitmap);

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
            String photoPath;
            boolean isPrivate;
            Integer quantity;


            // name
            EditText nameView = (EditText) findViewById(R.id.itemNameText);
            name = nameView.getText().toString();
            // category
            Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
            category = spinner.getSelectedItem().toString();
            // quantity
            EditText quantityfield = (EditText) findViewById(R.id.quantityEdit);
            quantity = Integer.valueOf(quantityfield.getText().toString());
            if (quantity == null) quantity = 1;
            System.out.println("Quantity " + quantity);
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
            photoPath = photo;

            Item item = new Item(name, category, quantity, quality, isPrivate, description, photo);

            UserLocation.setItemLocation(item);
          //  Log.e(TAG, (String.valueOf(item.getLocation().getLatitude())));


            InventoryController.getInventoryModel().addItem(item);

            // Execute the thread to add this remotely
           // Thread thread1 = new RemoveThread(InventoryController.getInventoryModel());
           // thread1.start();
            Thread thread = new AddThread(InventoryController.getInventoryModel());
            thread.start();
        }catch (RuntimeException ignored){
        }

        this.finish();
    }


    class AddThread extends Thread {
        private Inventory inventory;

        public AddThread(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            InventoryController.addInventory(inventory);


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
        Location loc = theItem.getLocation();
        loc.setLatitude(Double.valueOf(lat.getText().toString()));
        loc.setLongitude(Double.valueOf(longit.getText().toString()));
        theItem.setLocation(loc);
        Inventory deleteList = new Inventory();
       // Toast.makeText(ItemActivity.this, theItem.getName(), Toast.LENGTH_SHORT).show();
        for (Item item1 :InventoryController.getInventoryModel().getInventory()){
            if(item1.getName().equalsIgnoreCase(theItem.getName())) {
                deleteList.addItem(item1);
            }
        }
        for (Item item2: deleteList.getInventory()){

            InventoryController.getInventoryModel().removeItem(item2);

        }

        addItem(v);

    }


    public void startTradeActivity(View v){
        Intent intent = new Intent(this, TradeActivity.class);
        Item item = (Item) getIntent().getSerializableExtra("MyItem");

        intent.putExtra("ownerItem",item );
        startActivity(intent);

    }


}
