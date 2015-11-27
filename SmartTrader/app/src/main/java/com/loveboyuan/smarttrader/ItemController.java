package com.loveboyuan.smarttrader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by nabdulla on 23/11/2015.
 */
public class ItemController {
    private static Item item = null;

    //Taken from android developers website
    //http://developer.android.com/guide/topics/media/camera.html#saving-media
    public static Uri getOutputMediaFileUri(int type, Context context){
        return Uri.fromFile(getOutputMediaFile(type, context));
    }
    public static File getOutputMediaFile(int type, Context context){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(context.getFilesDir(), "Smart Trader");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Smart Trader", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        return mediaFile;
    }
}
