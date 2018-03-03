package com.qianfeng.day28_snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.qianfeng.day28_snake.widgets.SnakeView;

public class MainActivity extends AppCompatActivity {

    //最小移动距离
    public static final int MIN_DISTANCE = 30;
    //最小滑动速度
    public static final int MIN_SPEED = 30;
    //手势探测器
    private GestureDetector mGd;
    //游戏视图
    private SnakeView mSnakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        //创建游戏视图
        mSnakeView = new SnakeView(this,display.getWidth(),display.getHeight());
        //使用游戏视图
        setContentView(mSnakeView);
        //实例化手势探测器
        mGd = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            //快速滑动 e1开始滑动时的事件，e2结束滑动时的事件，velocityX横向滑动的速度，velocityY纵向滑动的速度
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //判断滑动方向
                if(e2.getX() - e1.getX() >= MIN_DISTANCE
                        && Math.abs(velocityX) >= MIN_SPEED){
                    //向右滑动
//                    Toast.makeText(MainActivity.this, "RIGHT", Toast.LENGTH_SHORT).show();
                    mSnakeView.setDirection(SnakeView.DIRECTION_RIGHT);
                }else if(e1.getX() - e2.getX() >= MIN_DISTANCE
                        && Math.abs(velocityX) >= MIN_SPEED){
                    //向左滑动
//                    Toast.makeText(MainActivity.this, "LEFT", Toast.LENGTH_SHORT).show();
                    mSnakeView.setDirection(SnakeView.DIRECTION_LEFT);
                }else if(e2.getY() - e1.getY() >= MIN_DISTANCE
                        && Math.abs(velocityY) >= MIN_SPEED){
                    //向下滑动
//                    Toast.makeText(MainActivity.this, "DOWN", Toast.LENGTH_SHORT).show();
                    mSnakeView.setDirection(SnakeView.DIRECTION_DOWN);
                }else if(e1.getY() - e2.getY() >= MIN_DISTANCE
                        && Math.abs(velocityY) >= MIN_SPEED){
                    //向上滑动
//                    Toast.makeText(MainActivity.this, "UP", Toast.LENGTH_SHORT).show();
                    mSnakeView.setDirection(SnakeView.DIRECTION_UP);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止游戏线程
        mSnakeView.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件交给手势探测器处理
        return mGd.onTouchEvent(event);
    }
}
