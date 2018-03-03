package com.androidxx.yangjw.day31_project_mvp_demo.model.home;

import com.androidxx.yangjw.day31_project_mvp_demo.model.beans.HomeListBean;

/**
 * Created by yangjw on 2017/2/3.
 */
public interface IHomeModel {

    public static final String LIST_URL = "http://www.1688wan.com/majax.action?method=bdxqs&pageNo=0";
    /**
     * 查询首页列表数据
     */
    void queryListData(HomeModelCallback callback);

    /**
     * 接口
     * 利用此接口进行接口回调，将model层的数据传递给presenter层
     */
    public interface HomeModelCallback {
        void success(HomeListBean result);
    }
}
