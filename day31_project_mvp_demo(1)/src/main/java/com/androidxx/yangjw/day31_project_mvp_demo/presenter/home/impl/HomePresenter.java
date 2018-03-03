package com.androidxx.yangjw.day31_project_mvp_demo.presenter.home.impl;

import com.androidxx.yangjw.day31_project_mvp_demo.model.beans.HomeListBean;
import com.androidxx.yangjw.day31_project_mvp_demo.model.home.IHomeModel;
import com.androidxx.yangjw.day31_project_mvp_demo.model.home.impl.HomeModel;
import com.androidxx.yangjw.day31_project_mvp_demo.presenter.home.IHomePresenter;
import com.androidxx.yangjw.day31_project_mvp_demo.view.home.callback.IHomeView;

/**
 * Created by yangjw on 2017/2/3.
 */
public class HomePresenter implements IHomePresenter,IHomeModel.HomeModelCallback {

    private IHomeModel homeModel;
    private IHomeView homeView;

    public HomePresenter(IHomeView homeView) {
        homeModel = new HomeModel();
        this.homeView = homeView;
    }

    @Override
    public void queryListData() {
        homeModel.queryListData(this);
    }

    @Override
    public void success(HomeListBean result) {
        this.homeView.success(result);
    }
}
