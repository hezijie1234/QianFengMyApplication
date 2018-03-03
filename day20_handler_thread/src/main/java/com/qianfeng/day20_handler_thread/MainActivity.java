package com.qianfeng.day20_handler_thread;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final int MESSAGE_IMAGE = 1;
    public static final String IMAGE_URL = "http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg";
    private ImageView mImageView;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_iv);
        mDialog = new ProgressDialog(this);
    }

    /**
     * 处理消息
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_IMAGE:
                    //图片的显示
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mImageView.setImageBitmap(bitmap);
                    mDialog.dismiss();
                    break;
            }
        }
    };

    public void onClickLoadImage(View view) {
        mDialog.show();
        //启动线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载图片
                try {
                    //
                    URL url = new URL(IMAGE_URL);
                    HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        //将图片发送给Handler进行处理
                        Message message = mHandler.obtainMessage(MESSAGE_IMAGE, bitmap);
                        mHandler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
