package com.androidxx.yangjw.mvpdemo.model;

/**
 * Created by yangjw on 2017/1/16.
 */
public interface IMainModel {

    void queryData();

    /**
     * 用来在Model和Presenter间进行数据的传递，相当于是一个鸽子
     */
    public interface IModelCallback{
        public void setResult(String result);
    }
}
