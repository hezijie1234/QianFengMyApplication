package com.androidxx.yangjw.day31_project_mvp_demo.model.home.impl;

import com.androidxx.yangjw.day31_project_mvp_demo.http.AsyncTaskTool;
import com.androidxx.yangjw.day31_project_mvp_demo.model.beans.HomeListBean;
import com.androidxx.yangjw.day31_project_mvp_demo.model.home.IHomeModel;
import com.google.gson.Gson;

/**
 * Created by yangjw on 2017/2/3.
 */
public class HomeModel implements IHomeModel {





    @Override
    public void queryListData(final HomeModelCallback callback) {
        //异步网络请求，所以不能通过return返回数据
        AsyncTaskTool.load(LIST_URL).execute(new AsyncTaskTool.IMyCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HomeListBean homeListBean = gson.fromJson(result, HomeListBean.class);
                callback.success(homeListBean);
            }
        });
    }
}
