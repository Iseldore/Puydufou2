package com.exia.puydufou.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class Storage {
    Context context;
    public final static String APP_PATH_SD_CARD = "images/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";

    public Storage(Context context){
        this.context = context.getApplicationContext();
    }

    public String saveImageToInternalStorage(Bitmap bitmapImage, String filename){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, filename);

        FileOutputStream fos = null;
        if(!mypath.exists()) {
            System.out.println("File not exists : "+filename);
            try {
//                System.err.println("set : "+mypath.toString());
                fos = new FileOutputStream(mypath);

                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return directory.getAbsolutePath();
        }
        else
            return null;
    }

    public Bitmap getThumbnail(String filename) {
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

            File f=new File(directory, filename);
//            System.err.println("get : "+f.toString());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }

    public Bitmap getBitmap(String url){
       /* Bitmap bitmap = null;
        try {
            bitmap = new BackgroundTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;*/
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*private class BackgroundTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {

        }

        // Download Music File from Internet
        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(url[0]).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap file_url) {

        }
    }*/

}
