package day23downappbyservice.day28snakegame.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import day23downappbyservice.day28snakegame.SnakesUtils;

/**
 * Created by hezijie on 2017/1/11.
 */
public class SnakeView extends View {

    private LinkedList<Point> mSnakeList;
    private Paint mPaint;
    private int size = 30;
    public static final int left = 1;
    public static final int right = 2;
    public static final int top = 3;
    public static final int bottom = 4;
    public static  int mDirection =  left;

    private int mWidth;
    private int mHeight;

    private Thread mUpdate;
    private boolean mIsRunning;
    private Point mFood;
    private int mMaxScore;
    private Context context;

    public SnakeView(Context context ,int width,int height) {
        super(context);
        this.context = context;
        mWidth = width;
        mHeight = height;
        initGame();
    }

    /**
     * 随机产生食物，
     */
    public void randomPoint(){
        Random random = new Random();
        do{
            int width = random.nextInt(mWidth/size+1)-1;
            int height = random.nextInt(mHeight/size+1)-1;
            Log.e("111", "randomPoin: "+width );
            mFood = new Point(width,height);
        }while(mSnakeList.contains(mFood));

    }

    private boolean isDead(){
        Point first = mSnakeList.getFirst();
        if(first.x>mWidth/size-1||first.x<0||first.y>mHeight/size-3||first.y<0){
            return true;
        }
        for (int i = 1; i < mSnakeList.size(); i++) {
            if(mSnakeList.get(i).equals(mSnakeList.getFirst())){
                return true;
            }
        }
        return false;
    }

    private void initGame() {
        mSnakeList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            mSnakeList.add(new Point(20,20+i));
        }
        mPaint = new Paint();
        mIsRunning = true;
        if(mUpdate==null){
            mUpdate = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(mIsRunning){
                        if(isDead()){
                            int score = (mSnakeList.size() - 5) * 10;
                            mMaxScore = SnakesUtils.getScore(context,"score");
                            mMaxScore = Math.max(mMaxScore,score);
                            SnakesUtils.putScore(context,mMaxScore);
                            stop();
                            initGame();
                            break;
                        }
                        try {
                            postInvalidate();
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mUpdate.start();
        }
    }

    public void setDirection(int direction){
        switch (direction){
            case left:
                if(right == mDirection){
                    return;
                }
                break;
            case right:
                if(left == mDirection){
                    return;
                }
                break;
            case top:
                if(bottom == mDirection){
                    return;
                }
                break;
            case bottom:
                if(top == mDirection){
                    return;
                }
                break;
        }
        mDirection = direction;
    }

    public void stop(){
        mIsRunning = false;
        mUpdate = null;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPaint.setColor(Color.BLACK);
        for(Point point : mSnakeList){
            canvas.drawRect(point.x*size,point.y*size,point.x*size+size-1,point.y*size+size-1,mPaint);
        }
        move();
        if(mFood==null){
            randomPoint();
        }
        canvas.drawRect(mFood.x*size,mFood.y*size,mFood.x*size+size,mFood.y*size+size,mPaint);
        mPaint.setTextSize(50f);
        mPaint.setColor(Color.RED);
        canvas.drawText("本关得分:"+(mSnakeList.size()-5)*10,60,50,mPaint);
        canvas.drawText("最高纪录;"+SnakesUtils.getScore(context,"score"),60,120,mPaint);
    }

    private void move() {
        Point head = mSnakeList.getFirst();
        Point newHead = new Point(head.x,head.y);
        switch(mDirection){
            case left:
                newHead.x--;
                break;
            case top:
                newHead.y--;
                break;
            case right:
                newHead.x++;
                break;
            case bottom:
                newHead.y++;
                break;
        }
        mSnakeList.addFirst(newHead);
        if(mSnakeList.getFirst().equals(mFood)){
            randomPoint();
        }else{
            mSnakeList.removeLast();
        }
    }


}
