package day23downappbyservice.day28snakegame;

import android.content.Context;

/**
 * Created by hezijie on 2017/1/11.
 */
public class SnakesUtils {

    public static void putScore(Context context,int score){
        context.getSharedPreferences("Myscore",Context.MODE_PRIVATE).edit().putInt("score",score).commit();
    }

    public static  int  getScore(Context context,String key){
        int anInt = context.getSharedPreferences("Myscore", Context.MODE_PRIVATE).getInt("score", 0);
        return anInt;
    }
}
