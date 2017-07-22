package com.expose.safa.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.expose.safa.modelclasses.Model;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by vineesh on 18/04/2017.
 */





/**
 * this class for stoing the image to the
 * phone memmory to work the application in Offline
 *
 * storing path===/storage/emulated/0/Android/data/com.expose.safa
 *
 * why this Path ??
 * while storing images in this path it wont see the gallery
 * and also when the app is Uninstall the folder also delete
 *
 * corect path for the Image == ==/storage/emulated/0/Android/data/com.expose.safa/26Toilet Stand.jpg

 * **/
public class ImageStorage {

    public static String actualPath;

    public static String saveToSdCard(Bitmap bitmap, String filename) {

        String stored = null;

        File sdcard = Environment.getExternalStorageDirectory() ;
        actualPath = sdcard.toString();

        File folder = new File(sdcard, "/Android/data/com.expose.safa");
        Log.d("saveToSdCard",sdcard.toString());
        //the dot makes this directory hidden to the user
        folder.mkdir();
        File file = new File(folder.getAbsoluteFile(), filename) ;



        if (file.exists())
            return stored ;

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stored;
    }



// /storage/emulated/0/Android/data/com.expose.safa/products/

    public static File getImage(String imagename) {

        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir + "/Android/data/com.expose.safa/"+imagename);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaImage;
    }



    public static boolean checkifImageExists(String imagename)
    {
        Bitmap b = null ;
        File file = getImage("/"+imagename);
        String path = file.getAbsolutePath();

      /*  Model m=new Model();
        m.setImagePath(path);*/




        if (path != null)
            b = BitmapFactory.decodeFile(path);

        if(b == null ||  b.equals(""))
        {
            return false ;
        }
        return true ;
    }




}
