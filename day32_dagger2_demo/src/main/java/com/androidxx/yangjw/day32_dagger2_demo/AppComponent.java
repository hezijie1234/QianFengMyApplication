package com.androidxx.yangjw.day32_dagger2_demo;

import dagger.Component;

/**
 * Created by yangjw on 2017/2/4.
 */
//使用Component注解标识的接口，作为针管使用
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
