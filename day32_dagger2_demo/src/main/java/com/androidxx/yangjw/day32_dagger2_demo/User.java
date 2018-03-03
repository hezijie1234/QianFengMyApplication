package com.androidxx.yangjw.day32_dagger2_demo;

import javax.inject.Inject;

/**
 * Created by yangjw on 2017/2/4.
 */
public class User implements IUser{

    private String name;
    Car car;

     //@Inject在无参构造器上的Inject注解表示，使用当前构造器创建User对象
    public User() {
    }

    public User(Car car) {
        this.car = car;
    }

    public User(String name) {
        this.name = name;
    }

    public void say() {
        car.drive();
        System.out.println("这是一个Dagger的demo:" + name);
    }
}
