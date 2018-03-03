package com.androidxx.yangjw.mvpdemo.model.impl;

import com.androidxx.yangjw.mvpdemo.http.AsyncTaskTool;
import com.androidxx.yangjw.mvpdemo.model.IMainModel;

/**
 * Created by yangjw on 2017/1/16.
 */
public class MainModel implements IMainModel {

    public static final String URL = "http://www.1688wan.com/majax.action?method=getGiftList";
    private IModelCallback modelCallback;

    public MainModel(IModelCallback modelCallback) {
        this.modelCallback = modelCallback;
    }

    @Override
    public void queryData() {
        //异步网络请求
        AsyncTaskTool.load(URL).post("pageno=1").execute(new AsyncTaskTool.IMyCallback() {
            @Override
            public void success(String result) {
                //利用鸽子传递数据
                modelCallback.setResult(result);
            }
        });
    }
}
