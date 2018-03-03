package com.example.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DetailsActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SearchActivity;
import com.example.myapplication.adapter.GiftAdapter;
import com.example.myapplication.entities.Gift;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hezijie on 2016/12/27.
 */
public class FirstFragment  extends Fragment{
    //礼包listview
    private PullToRefreshListView mPullListView;
    private GiftAdapter mGiftAdapter;
    //数据的来源对象
    private Gift gift;
    private View mAdsView;
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
    //礼包网页编号
    private int mUrlPage=1;
    private List<Gift.ListBean> mGiftOfAll;

    private Toolbar mToolbar;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mAdsViewPager.setCurrentItem(msg.arg1);
                    break;
            }
        }
    };;
    private Timer time;
    private int mTimeNum;
    private android.app.FragmentManager mFragmentMan;
    //搜索按钮
    private TextView mSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        mTimeNum=0;
        initView(view);
        initData();
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
        mPullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity ac = (MainActivity) getActivity();
                Intent intent = new Intent(ac,DetailsActivity.class);
                intent.putExtra("id",mGiftOfAll.get(position).getId());
                startActivity(intent);
            }
        });
        mPullListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mUrlPage=1;
                final Gson gson = new Gson();
                new LoaderJsonData().getGift(Constants.GIFT_URL + mUrlPage, new LoaderJsonData.GetJsonData() {
                    @Override
                    public void getJsonData(String jsonData) {
                        Gift gift = gson.fromJson(jsonData,Gift.class);
                        mGiftOfAll.clear();
                        mGiftOfAll.addAll(gift.getList());
                        mGiftAdapter.notifyDataSetChanged();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullListView.onRefreshComplete();
                            }
                        }, 1000);
                    }
                });

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mUrlPage++;
                final Gson gson = new Gson();
                new LoaderJsonData().getGift(Constants.GIFT_URL + mUrlPage, new LoaderJsonData.GetJsonData() {
                    @Override
                    public void getJsonData(String jsonData) {
                        Gift gift = gson.fromJson(jsonData,Gift.class);
                        mGiftOfAll.addAll(gift.getList());
                        mGiftAdapter.notifyDataSetChanged();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullListView.onRefreshComplete();
                            }
                        }, 1000);
                    }
                });
                Log.e("111", "onPullUpToRefresh: "+mUrlPage );
            }
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity)getActivity();
                Intent intent = new Intent(main, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        mPullListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_lv);
        mAdsView = LayoutInflater.from(getContext()).inflate(R.layout.ads_viewpager,null);
        mAdsViewPager = (ViewPager) mAdsView.findViewById(R.id.first_fragment_tv);
        mDotsLinear = (LinearLayout) mAdsView.findViewById(R.id.dot_linear);
        mToolbar = (Toolbar) view.findViewById(R.id.first_frag_toolbar);
        mSearchView = (TextView) view.findViewById(R.id.first_toolbar_search_tv);

        mToolbar.setTitleTextColor(Color.WHITE);

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
     * @param
     */
    private void initData() {
        mGiftOfAll = new ArrayList<>();
        final Gson gson = new Gson();
        new LoaderJsonData().getGift(Constants.GIFT_URL+mUrlPage, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                //通过反射加载gift的数据，由系统为我们将gift中的数据初始化
                gift = gson.fromJson(jsonData, Gift.class);
                initAdsView(gift.getAd());
                mAdsViewPager.setAdapter(new MyPagerAdapter());
                initDotsView(gift.getAd().size());
                mGiftOfAll.addAll(gift.getList());
                mGiftAdapter = new GiftAdapter(getContext(), mGiftOfAll);
                ListView listView = mPullListView.getRefreshableView();
                listView.addHeaderView(mAdsView);
                mPullListView.setAdapter(mGiftAdapter);
            }
        });
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mTimeNum == (gift.getAd().size()-1)){
                    mTimeNum=0;
                }else{
                    mTimeNum++;
                }
                Message message = handler.obtainMessage(1, mTimeNum ,0);
                handler.sendMessage(message);
            }
        },3000,3000 );
    }

    private void initAdsView(List<Gift.AdBean> list){
        mAdsImageList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            final ImageView image = new ImageView(getContext());
            new LoadBtimap().getBitmap(Constants.BASE_URL + list.get(i).getIconurl(), new LoadBtimap.LoadBitmapListener() {
                @Override
                public void disPlayBitmap(Bitmap bitmap,String url) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        time.cancel();
    }
}
