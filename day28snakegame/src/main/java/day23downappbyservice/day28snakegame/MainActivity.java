package day23downappbyservice.day28snakegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import day23downappbyservice.day28snakegame.widgets.SnakeView;

public class MainActivity extends AppCompatActivity {

    public static final int SPEED = 5;
    public static final int DISTANCE = 5;
    private GestureDetector mGesture;
    private SnakeView snakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        snakeView = new SnakeView(this, defaultDisplay.getWidth(), defaultDisplay.getHeight());
        setContentView(snakeView);
        mGesture = new GestureDetector(this, new GestureDetector.OnGestureListener() {
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

            /**这是快速滑动的监听，，
             * @param e1:开始滑动时 的时间
             * @param e2：结束滑动时的时间
             * @param velocityX：横向滑动速度
             * @param velocityY：纵向滑动速度
             *
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e1.getX()-e2.getX()>DISTANCE*2&&Math.abs(velocityX)>SPEED){
                    //Toast.makeText(MainActivity.this,"Left",Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(snakeView.left);
                }else if(e2.getX()-e1.getX()>DISTANCE*2 &&Math.abs(velocityX)>SPEED){
                    //Toast.makeText(MainActivity.this,"Right",Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(snakeView.right);
                }else if(e1.getY()-e2.getY()>DISTANCE&&Math.abs(velocityY)>SPEED){
//                    Toast.makeText(MainActivity.this,"Top",Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(snakeView.top);
                }else if(e2.getY()-e1.getY()>DISTANCE&&Math.abs(velocityY)>SPEED){
//                    Toast.makeText(MainActivity.this,"Bottom",Toast.LENGTH_SHORT).show();
                    snakeView.setDirection(snakeView.bottom);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snakeView.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把点击事件交给手势探测器
        return mGesture.onTouchEvent(event);
    }
}
