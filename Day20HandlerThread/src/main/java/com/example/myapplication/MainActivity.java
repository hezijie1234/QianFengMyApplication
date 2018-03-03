package com.example.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView mImage;
    private ProgressDialog mDialog;
    public static final String IMAGE_URL = "http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
        mDialog = new ProgressDialog(this);

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mImage.setImageBitmap((Bitmap) msg.obj);
                    mDialog.dismiss();
                    break;
            }
        }
    };
    public void onClickLoadImage(View view) {
        mDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(IMAGE_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream input = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(input);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    Message message = handler.obtainMessage(1, bitmap);
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
