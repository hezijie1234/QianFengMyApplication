package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.entity.Gift;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Gift mGift;
    private ViewPager mAdsViewPager;
    private List<View> mViewList;
    private LinearLayout mDotLinear;
    private List<ImageView> mDotsImages;
    private int mCurrentDot;
    private int mCurrentPage;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        listener();
    }
    //设置viewpager的监听，改变dot的状态
    private void listener() {
        mAdsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将原来的远点设置true
                mDotsImages.get(mCurrentDot).setEnabled(true);
                //然后将现在的远点设置成false
                mDotsImages.get(position).setEnabled(false);
                mCurrentDot = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mAdsViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewList = new ArrayList<>();
        mDotLinear = (LinearLayout) findViewById(R.id.dots_line_ll);
    }

    private void initDotsView(int count){
        mDotsImages = new ArrayList<>();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(3,0,3,0);
        for(int i=0;i<count;i++){
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.dot);
            mDotsImages.add(image);
            mDotLinear.addView(image,ll);
        }
        //默认选中第一页
        mDotsImages.get(0).setEnabled(false);
        mCurrentDot = 0;
    }

    /*根据json中的数据来初始化视图容器
       *  */
    private void initViewList(List<Gift.AdBean> list){
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i=0;i<list.size();i++){
            View view = inflater.inflate(R.layout.image, null);
            //image不能设置成成员变量，final类型。
            final ImageView image = (ImageView) view.findViewById(R.id.image);
            new LoadBtimap().getBitmap(Constants.BASE_URL + list.get(i).getIconurl(), new LoadBtimap.LoadBitmapListener() {
                @Override
                public void disPlayBitmap(Bitmap bitmap) {
                    if(bitmap!=null){
                        image.setImageBitmap(bitmap);
                    }
                }
            });
            //这里必须添加image的根视图
            mViewList.add(view);
        }
    }

    private void initData() {
        handler = new Handler();
        final Gson json = new Gson();
        LoaderJsonData loaderJsonData = new LoaderJsonData();
        loaderJsonData.getGift(Constants.GIFT_URL + 1, new LoaderJsonData.GetJsonData() {


            @Override
            public void getJsonData(String jsonData) {
                mGift = json.fromJson(jsonData, Gift.class);
                initViewList(mGift.getAd());
                mAdsViewPager.setAdapter(new MyViewPager());
                initDotsView(mGift.getAd().size());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mCurrentPage==mGift.getAd().size()){
                            mCurrentPage=0;
                        }else{
                            mCurrentPage++;
                        }
                        mAdsViewPager.setCurrentItem(mCurrentPage);
                        handler.postDelayed(this,4000);
                    }
                },4000);
            }
        });
    }

    class MyViewPager extends PagerAdapter{

        @Override
        public int getCount() {
            return mViewList==null?0:mViewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

}
