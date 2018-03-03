package com.androidxx.yangjw.day32_dagger2_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

/**
 * Dagger2的使用步骤：
 * 1、导包
 *  a、project level build.gradle中加入需要配置的内容
 *  b、module level build.gradle中加入需要配置的内容
 * 2、给需要注入的对象标注注解
 * 3、创建一个接口，使用component注解进行注解。作为入口使用。
 * 4、编译module工程
 * 总结：
 * inject：
 *  1、用来标识需要注入的变量
 *  2、作用于无参构造器上，用来表示使用此构造器创建对象
 * component：
 *  dagger的一个入口，任何对象的注入，是由此入口开始的
 * module：
 *  好比是一个弹药库，可以提供一些对象，用来注入。
 * provides
 *  和module结合使用的，用来作用来方法上，表示此方法可以提供对象
 *
 */
public class MainActivity extends AppCompatActivity {

    @Inject //此处需要Dagger进行注入
    IUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始注入
        DaggerAppComponent.create().inject(this);
        user.say();
    }
}
