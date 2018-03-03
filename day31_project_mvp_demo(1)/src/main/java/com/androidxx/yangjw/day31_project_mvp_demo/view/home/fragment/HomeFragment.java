package com.androidxx.yangjw.day31_project_mvp_demo.view.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidxx.yangjw.day31_project_mvp_demo.R;
import com.androidxx.yangjw.day31_project_mvp_demo.model.beans.HomeListBean;
import com.androidxx.yangjw.day31_project_mvp_demo.presenter.home.IHomePresenter;
import com.androidxx.yangjw.day31_project_mvp_demo.presenter.home.impl.HomePresenter;
import com.androidxx.yangjw.day31_project_mvp_demo.view.home.adapter.HomeListAdapter;
import com.androidxx.yangjw.day31_project_mvp_demo.view.home.callback.IHomeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjw on 2017/2/3.
 */
public class HomeFragment extends Fragment implements IHomeView{

    private static final String TAG = "androidxx";
    private Context mContext;
    private PullToRefreshListView mRefreshListView;
    private HomeListAdapter homeListAdapter;
    private IHomePresenter homePresenter;
    private List<HomeListBean.ListBean> dataList = new ArrayList<>();

    /**
     * 工厂方法
     * 用来创建当前Fragment的实例对象
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        homePresenter = new HomePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        homePresenter.queryListData();
        return view;
    }

    private void initView(View view) {
        mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.home_fragment_list_view);
        homeListAdapter = new HomeListAdapter(mContext,dataList);
        mRefreshListView.setAdapter(homeListAdapter);
        mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        setupListener();
    }

    private void setupListener() {
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }


    @Override
    public void success(HomeListBean result) {
        Log.i(TAG, "success: " + result);
        List<HomeListBean.ListBean> list = result.getList();
        dataList.addAll(list);
        homeListAdapter.notifyDataSetChanged();
//        Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
    }
}
