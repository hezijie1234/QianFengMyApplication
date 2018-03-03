package com.androidxx.yangjw.day32_dependency_injection_demo;

/**
 * Created by yangjw on 2017/2/4.
 */
public class Classroom {
    //桌子属性
    private Desk desk;

    public Classroom() {
        //依赖注入：给当前Desk属性赋值不是在当前类中
        desk = new Desk();
    }
}
