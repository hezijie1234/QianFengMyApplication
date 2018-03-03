package com.androidxx.yangjw.day32_dependency_injection_demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangjw on 2017/2/4.
 * 定义了一个注解用来标识Android的控件
 */
//表示当前的BIndView注解只能作用于属性之上
@Target(ElementType.FIELD)
//表示当前注解在运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int value();
}
