package org.wildstang.wildrank.androidv2.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class ScoutingCamera {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public static void launchCamera(Activity activity, int teamkey) {
        File storageDir = new File(Environment.getExternalStorageDirectory().getPath(), "/wildrank/");
        if (!storageDir.exists()) {
            storageDir.mkdirs(); // May not actually work, but shouldn't be needed
        }
        File imageFile = new File(storageDir.getPath() + File.separator + teamkey + ".jpg");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            //File save directory
            Uri photoURI = Uri.fromFile(imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
