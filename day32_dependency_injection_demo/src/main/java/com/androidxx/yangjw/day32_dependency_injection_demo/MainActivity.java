package com.androidxx.yangjw.day32_dependency_injection_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 依赖注入（DI）：也叫做控制反转（IOC）。
 * 什么是依赖？
 */
public class MainActivity extends AppCompatActivity {

    //User就是MainActivity的一个依赖。MainActivity依赖于User
    User user;

    @BindString(R.string.app_name)
    String str;
    @BindView(R.id.main_show_txt)
    TextView mMainShowTxt;
    @BindView(R.id.main_txt)
    TextView showTxt;
    @BindView(R.id.main_btn)
    Button btn;
    @BindView(R.id.main_image)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User();
        user.print();
        Binder.bind(this);
        mMainShowTxt.setText("这是通过依赖注入赋值的对象");
        showTxt.setText("sssss:" + str);
        btn.setText("这是一个按钮");
        imageView.setImageResource(R.mipmap.ic_launcher);
    }
}
