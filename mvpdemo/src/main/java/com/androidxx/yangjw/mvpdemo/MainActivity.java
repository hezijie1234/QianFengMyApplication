package com.androidxx.yangjw.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.androidxx.yangjw.mvpdemo.presenter.IMainPresenter;
import com.androidxx.yangjw.mvpdemo.presenter.impl.MainPresenter;

import org.w3c.dom.Text;

/**
 * MVP分层设计模式
 * View：Activity+布局资源文件
 * Model：数据访问层
 * P：View和Model之间的一个桥梁
 */
public class MainActivity extends AppCompatActivity implements IMainPresenter.IPresenterCallback{

    private TextView mShowText;
    private IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowText = (TextView) findViewById(R.id.main_show_txt);
        mainPresenter = new MainPresenter(this);
        mainPresenter.queryList(1);
    }

    @Override
    public void refreshView(String data) {
        mShowText.setText(data);
    }
}
