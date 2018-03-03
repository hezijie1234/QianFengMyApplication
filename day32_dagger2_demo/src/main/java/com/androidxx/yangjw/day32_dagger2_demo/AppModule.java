package com.androidxx.yangjw.day32_dagger2_demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangjw on 2017/2/4.
 */
//使用Module注解的类表示“弹药库”
@Module
public class AppModule {


    @Provides
    public Car provideCar() {
        return new Car();
    }

    //使用Provides注解标注的方法表示提供需要注入的对象
    @Provides
    public IUser provideUser(Car car) {
        System.out.println("收费");
        return new User(car);
    }
}
