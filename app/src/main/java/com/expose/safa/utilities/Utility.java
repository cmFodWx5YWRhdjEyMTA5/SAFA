package com.expose.safa.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by vineesh on 03/04/2017.
 */

public class Utility {





    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

        Log.d("toByteArray",stream.toByteArray().toString());
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }




}
