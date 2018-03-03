package com.qianfeng.day10_listview_page.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xray on 16/12/1.
 */

public class ImageUtils {

    public interface OnImageTaskListener{

        void onImageComplete(Bitmap bitmap,String urlStr);
    }

    static class ImageInfo{
        Bitmap bitmap;
        String urlStr;

        public ImageInfo(Bitmap bitmap, String urlStr) {
            this.bitmap = bitmap;
            this.urlStr = urlStr;
        }
    }

    static class ImageTask extends AsyncTask<String,Void,ImageInfo>{

        private  OnImageTaskListener listener;

        public ImageTask(OnImageTaskListener listener){
            this.listener = listener;
        }

        @Override
        protected ImageInfo doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return new ImageInfo(bitmap,params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ImageInfo img) {
            if(listener != null){
                listener.onImageComplete(img.bitmap,img.urlStr);
            }
        }

    }

    public static void readImage(final String urlS, OnImageTaskListener listener){
        new ImageTask(listener).execute(urlS);
    }

}
