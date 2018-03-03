package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.entities.StartSysLeftDetails;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by hezijie on 2017/1/10.
 */
public class StartSysActivity extends AppCompatActivity {

    private ImageView mGameImage;
    private TextView mGameName;
    private TextView mGameType;
    private TextView mGameSize;
    private LinearLayout mLinear;
    private TextView mGameDescrbles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_sys_details);
        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String gid = intent.getStringExtra("gid");
        Log.e("111", "initData: "+Constants.START_SYS_DETAILS+gid );
        final Gson gson = new Gson();
        LoaderJsonData load = new LoaderJsonData();
        load.getGift(Constants.START_SYS_DETAILS+gid, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                Log.e("111", "getJsonData: "+jsonData );
                StartSysLeftDetails startSysLeftDetails = gson.fromJson(jsonData, StartSysLeftDetails.class);
                StartSysLeftDetails.AppBean app = startSysLeftDetails.getApp();
                List<StartSysLeftDetails.ImgBean> img = startSysLeftDetails.getImg();
                LoadBtimap.getBitmap(Constants.BASE_URL + app.getLogo(), new LoadBtimap.LoadBitmapListener() {
                    @Override
                    public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                        if(null!=bitmap){
                            mGameImage.setImageBitmap(bitmap);
                        }
                    }
                });
                mGameName.setText(app.getName());
                mGameType.setText("类型:"+app.getTypename());
                mGameSize.setText("大小:未知");
                mGameDescrbles.setText("  "+app.getDescription());
                int width = getWindowManager().getDefaultDisplay().getWidth();
                //动态添加视图到scoreView中
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(width/2-10,800);
                ll.setMargins(2,0,2,0);
                for (int i = 0; i < img.size(); i++) {
                    final ImageView imageView = new ImageView(StartSysActivity.this);
                    LoadBtimap.getBitmap(Constants.BASE_URL + img.get(i).getAddress(), new LoadBtimap.LoadBitmapListener() {
                        @Override
                        public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                            if(null!=bitmap){
                                imageView.setImageBitmap(bitmap);
                                //必须要设置图片的样式
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        }
                    });
                    mLinear.addView(imageView,ll);
                }
            }
        });
    }

    private void initViews() {
        mGameImage = (ImageView) findViewById(R.id.start_sys_details_iv);
        mGameName = (TextView) findViewById(R.id.start_sys_details_gname);
        mGameType = (TextView) findViewById(R.id.start_sys_details_type);
        mLinear = (LinearLayout) findViewById(R.id.start_sys_details_linear);
        mGameSize = (TextView) findViewById(R.id.start_sys_details_size);
        mGameDescrbles = (TextView) findViewById(R.id.start_sys_details_describles);
    }
}
