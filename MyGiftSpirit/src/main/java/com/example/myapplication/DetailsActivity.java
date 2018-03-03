package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entities.GiftDetails;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    private GiftDetails mGiftDetails;
    private ImageView mCircleImage;
    private TextView mOverTime;
    private TextView mNumOfGift;
    private TextView mExplainOfGift;
    private TextView mGetMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        initData();
    }

    private void initViews() {
        mCircleImage = (ImageView) findViewById(R.id.image_bg);
        mOverTime = (TextView)findViewById(R.id.overtime);
        mNumOfGift = (TextView)findViewById(R.id.number_gift);
        mExplainOfGift = (TextView)findViewById(R.id.gift_explain_detail);
        mGetMethod = (TextView)findViewById(R.id.gift_get_detail);
    }

    private void initData() {
        Intent intent = getIntent();
        final Gson gson = new Gson();
        String id = intent.getStringExtra("id");
        new LoaderJsonData().getGift(Constants.GIFT_DETAILS + id, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                mGiftDetails = gson.fromJson(jsonData,GiftDetails.class);
                GiftDetails.InfoBean info = mGiftDetails.getInfo();
                mOverTime.setText(info.getOvertime());
                mNumOfGift.setText(info.getNumber()+"");
                mExplainOfGift.setText(info.getExplains());
                mGetMethod.setText(info.getDescs());
                LoadBtimap.getBitmap(Constants.BASE_URL + info.getIconurl(), new LoadBtimap.LoadBitmapListener() {
                    @Override
                    public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                        if(null!=bitmap){
                            mCircleImage.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }
}
