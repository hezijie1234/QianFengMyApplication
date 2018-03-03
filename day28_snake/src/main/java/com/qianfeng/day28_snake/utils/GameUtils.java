package com.qianfeng.day28_snake.utils;

import android.content.Context;

/**
 * Created by xray on 17/1/11.
 */

public class GameUtils {

    public static void saveRecord(Context context, int record){
        context.getSharedPreferences("snake",Context.MODE_PRIVATE)
                .edit()
                .putInt("record",record)
                .commit();
    }

    public static int readRecord(Context context){
        return context.getSharedPreferences("snake",Context.MODE_PRIVATE)
                .getInt("record",0);
    }
}
