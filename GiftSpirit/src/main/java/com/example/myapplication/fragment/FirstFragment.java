package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.entities.Gift;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2016/12/27.
 */
public class FirstFragment  extends Fragment{
    //数据的来源对象
    private Gift gift;

    private ViewPager mAdsViewPager;
    //轮播广告的视图集合
    private List<ImageView> mAdsImageList;
    //动态的添加点到这个布局中
    private LinearLayout mDotsLinear;
    //点的视图集合
    private List<View> mDotImageList;
    //当前远点的位置
    private int mCurrentDot;
    //当前页面的位置
    private int mCurrentPage;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        initData(view);
        initView(view);
        listener();
        return view;
    }
    /*
    * 监听页面的滑动
    * */
    private void listener() {
        mAdsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //页面滑动完成后执行
            @Override
            public void onPageSelected(int position) {
                mDotImageList.get(mCurrentDot).setEnabled(true);
                mDotImageList.get(position).setEnabled(false);
                mCurrentDot = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView(View view) {
        mAdsViewPager = (ViewPager) view.findViewById(R.id.first_fragment_tv);
        mDotsLinear = (LinearLayout) view.findViewById(R.id.dot_linear);
    }

    /**
     * 在这个方法初始化mDotImageList，点的数量又实际而定
     * @param count
     */
    private void initDotsView(int count){
        mDotImageList = new ArrayList<>();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(3,0,3,0);
        for(int i=0;i<count;i++ ){
            ImageView image = new ImageView(getContext());
            image.setImageResource(R.drawable.dot_selector);
            mDotImageList.add(image);
            mDotsLinear.addView(image,ll);
        }
        mDotImageList.get(0).setEnabled(false);
        mCurrentDot = 0;
    }


    /**
     * @param view
     */
    private void initData(View view) {
        handler = new Handler();
        final Gson gson = new Gson();
        mAdsImageList = new ArrayList<>();
        new LoaderJsonData().getGift(Constants.GIFT_URL+1, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                //通过反射加载gift的数据，由系统为我们将gift中的数据初始化
                gift = gson.fromJson(jsonData, Gift.class);
                initAdsView(gift.getAd());
                mAdsViewPager.setAdapter(new MyPagerAdapter());
                initDotsView(gift.getAd().size());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mCurrentPage==gift.getAd().size()){
                            mCurrentPage=0;
                        }else{
                            mCurrentPage++;
                        }
                        mAdsViewPager.setCurrentItem(mCurrentPage);
                        handler.postDelayed(this,3000);
                    }
                },3000);
            }
        });
    }

    private void initAdsView(List<Gift.AdBean> list){
        for(int i=0;i<list.size();i++){
            final ImageView image = new ImageView(getContext());
            new LoadBtimap().getBitmap(Constants.BASE_URL + list.get(i).getIconurl(), new LoadBtimap.LoadBitmapListener() {
                @Override
                public void disPlayBitmap(Bitmap bitmap) {
                    if(null!=bitmap){
                        image.setImageBitmap(bitmap);
                    }
                }
            });
          mAdsImageList.add(image);
        }
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mAdsImageList==null ? 0 :mAdsImageList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mAdsImageList.get(position));
            return mAdsImageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mAdsImageList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
