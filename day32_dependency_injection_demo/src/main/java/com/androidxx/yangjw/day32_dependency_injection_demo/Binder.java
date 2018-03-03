package com.androidxx.yangjw.day32_dependency_injection_demo;

import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by yangjw on 2017/2/4.
 */
public class Binder {

    public static void bind(MainActivity activity) {

        //获取Activity的class对象。因为如果要使用反射，必须要通过Class对象来操作。
        Class<? extends MainActivity> activityClass = activity.getClass();
        //获取MainActivity中的所有属性（反射）
        Field[] declaredFields = activityClass.getDeclaredFields();
        //遍历MainActivity中的所有属性
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //获取属性上的BindView注解
            BindView annotation = field.getAnnotation(BindView.class);
            BindString bindString = field.getAnnotation(BindString.class);
            //如果有BindView注解则annotation不为空，否则为空
            if (annotation != null) {
                int id = annotation.value();
                View view = activity.findViewById(id);
                try {
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (bindString != null) {
                int resId = bindString.value();
                String string = activity.getResources().getString(resId);
                try {
                    field.set(activity,string);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            //获取属性的名称
//            String fieldName = field.getName();
//            if ("mMainShowTxt".equals(fieldName)) {
//                //获取一个TextView对象
//                View view = activity.findViewById(R.id.main_show_txt);
//                //给指定的Field赋值
//                /**
//                 * 参数1：表示此Field属性是属于哪一个对象
//                 * 参数2：表示此Field的值
//                 */
//                try {
//                    field.set(activity,view);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
