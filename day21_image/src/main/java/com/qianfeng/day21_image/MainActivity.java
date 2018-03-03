package com.qianfeng.day21_image;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.qianfeng.day21_image.entity.Gift;
import com.qianfeng.day21_image.utils.ImageLoader;
import com.qianfeng.day21_image.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL_IMAGE = "http://photo.enterdesk.com/2010-10-24/enterdesk.com-3B11711A460036C51C19F87E7064FE9D.jpg";

    private List<Gift> mGifts = null;
    public static final String URL_TEXT = "http://www.1688wan.com/majax.action?method=getGiftList&pageno=1";

    private ListView mGiftListView;
    private GiftAdapter mGiftAdapter;
    private JSONUtils mJsonUtils;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initData();
    }

    private void initData() {
        mJsonUtils = new JSONUtils();
        mJsonUtils.readText(URL_TEXT, new JSONUtils.OnTextTaskListener() {
            @Override
            public void onTextComplete(String text) {
                List<Gift> data = mJsonUtils.getGiftList(text);
                mGifts.addAll(data);
                mGiftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextFailed() {

            }
        });
    }

    private void initViews() {
        mGiftListView = (ListView)findViewById(R.id.list_view);
        mGifts = new ArrayList<>();
        mGiftAdapter = new GiftAdapter(this,mGifts);
        mGiftListView.setAdapter(mGiftAdapter);

        mImageView = (ImageView) findViewById(R.id.image_view);
        //测试图片加载
        ImageLoader.loadImage(URL_IMAGE, 800, 800,
                new ImageLoader.OnImageLoadListener() {
            @Override
            public void onImageLoadComplete(Bitmap bitmap, String urlStr) {
                if(bitmap != null){
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });
    }
}
