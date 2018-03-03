package com.androidxx.yangjw.mvpdemo.presenter.impl;

import android.util.Log;

import com.androidxx.yangjw.mvpdemo.model.IMainModel;
import com.androidxx.yangjw.mvpdemo.model.impl.MainModel;
import com.androidxx.yangjw.mvpdemo.presenter.IMainPresenter;

/**
 * Created by yangjw on 2017/1/16.
 */
public class MainPresenter implements IMainPresenter,IMainModel.IModelCallback{

    private static final String TAG = "androidxx";
    private IMainModel mainModel;
    private IPresenterCallback presenterCallback;

    public MainPresenter(IPresenterCallback presenterCallback) {
        mainModel = new MainModel(this);
        this.presenterCallback = presenterCallback;
    }

    @Override
    public void queryList(int no) {
        if(no > 5){
            mainModel.queryData();
        } else {
            Log.i(TAG, "queryList: no < 5 ");
        }

    }

    /**
     * 鸽子带回的数据
     * @param result
     */
    @Override
    public void setResult(String result) {
        presenterCallback.refreshView(result);
    }
}
