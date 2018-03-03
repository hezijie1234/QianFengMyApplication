package com.androidxx.yangjw.mvpdemo.presenter;

/**
 * Created by yangjw on 2017/1/16.
 */
public interface IMainPresenter {

    public void queryList(int no);

    /**
     * 此接口用来在Presenter和View之间进行数据传递
     */
    public interface IPresenterCallback{
        public void refreshView(String data);
    }
}
