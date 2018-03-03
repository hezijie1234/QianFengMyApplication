package com.qianfeng.day28_snake.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.qianfeng.day28_snake.utils.GameUtils;

import java.util.LinkedList;
import java.util.Random;

/**
 * 贪食蛇游戏视图
 * Created by xray on 17/1/11.
 */

public class SnakeView extends View{

    public static final int GAME_DELAY = 300; //游戏延迟
    public static final int SIZE = 30;        //身体格子的尺寸
    public static final int DIRECTION_LEFT = 1;  //方向左
    public static final int DIRECTION_RIGHT = 2; //方向右
    public static final int DIRECTION_UP = 3;    //方向上
    public static final int DIRECTION_DOWN = 4;  //方向下
    private int mDirection = DIRECTION_LEFT;     //移动方向
    private int mWidth; //屏幕的宽度
    private int mHeight;//屏幕的高度
    private LinkedList<Point> mBody;           //蛇身体的坐标集合
    private Point mFood;                //食物的坐标
    private Paint mPaint;
    private Thread mUpdate;
    private boolean isRunning = false; //线程是否运行
    private boolean isDead = false;    //是否死亡
    private boolean isMove = false;    //能否移动
    private int mRecord = 0; //最高分
    private Context mContext;

    public SnakeView(Context context,int mWidth,int mHeight) {
        super(context);
        this.mContext = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        initGame();
    }

    //设置方向的方法
    public void setDirection(int direction){
        //判断不能反方向调头
        switch (direction){
            case DIRECTION_DOWN:
                if(mDirection == DIRECTION_UP){
                    return;
                }
                break;
            case DIRECTION_UP:
                if(mDirection == DIRECTION_DOWN){
                    return;
                }
                break;
            case DIRECTION_LEFT:
                if(mDirection == DIRECTION_RIGHT){
                    return;
                }
                break;
            case DIRECTION_RIGHT:
                if(mDirection == DIRECTION_LEFT){
                    return;
                }
                break;
        }
        mDirection = direction;
        //蛇开始移动
        isMove = true;
    }

    //随机产生食物
    private void createFood(){
        do {
            Random random = new Random();
            int x = 1 + random.nextInt(mWidth / SIZE-1);
            int y = 1 + random.nextInt(mHeight / SIZE-2) ;
            mFood = new Point(x, y);
        }while(mBody.contains(mFood)); //避免食物和蛇身坐标重合
    }

    //判断死亡状态
    private void checkDead(){
        Point head = mBody.getFirst();
        if(head.x < 0 || head.x > mWidth / SIZE - 1 ||
                head.y < 0 || head.y > mHeight / SIZE - 2){
            isDead = true;
        }
        for (int i = 1; i < mBody.size(); i++) {
            if(mBody.get(i).equals(head)){
                isDead = true;
                break;
            }
        }
    }

    //初始化游戏数据
    private void initGame(){
        mBody = new LinkedList<>();
        //添加5个格子作为身体
        for (int i = 0; i < 5; i++) {
            mBody.add(new Point(10,10 + i));
        }
        //创建食物
        createFood();
        //初始化画笔
        mPaint = new Paint();
        isRunning = true;
        isDead = false;
        isMove = false;
        mDirection = DIRECTION_LEFT;
        //读取最高分
        mRecord = GameUtils.readRecord(mContext);
        //启动线程，对视图进行刷新
        if(mUpdate == null){
            mUpdate = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(isRunning) {
                        //判断死亡状态
                        checkDead();
                        //如果死亡，就停止线程，重新游戏
                        if(isDead){
                            //保存最高纪录
                            int score = (mBody.size() - 5) * 10;
                            if(score > mRecord){
                                GameUtils.saveRecord(mContext,score);
                            }
                            stop();
                            initGame();
                            break;
                        }
                        //刷新界面
                        postInvalidate();
                        try {
                            Thread.sleep(GAME_DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mUpdate.start();
        }
    }

    //停止游戏线程
    public void stop(){
        isRunning = false;
        mUpdate = null;
    }

    //图形绘制
    @Override
    protected void onDraw(Canvas canvas) {
        //移动蛇
        if(isMove) {
            move();
        }
        mPaint.setColor(Color.BLUE);
        //绘制蛇身体
        for(Point point : mBody){
            //绘制矩形
            canvas.drawRect(point.x * SIZE,point.y * SIZE,
                    (point.x + 1) * SIZE - 1,(point.y + 1) * SIZE - 1,
                    mPaint);
        }
        mPaint.setColor(Color.RED);
        //画食物
        canvas.drawRect(mFood.x * SIZE,mFood.y * SIZE,
                (mFood.x + 1) * SIZE - 1,(mFood.y + 1) * SIZE - 1,
                mPaint);
        //画分数
        mPaint.setColor(Color.GREEN);
        int score = (mBody.size() - 5) * 10;
        mPaint.setTextSize(18);
        canvas.drawText("当前分数:"+score+",最高纪录:"+mRecord,20,20,mPaint);
    }

    //移动蛇
    private void move(){
        //获得原来的蛇头
        Point head = mBody.getFirst();
        //创建新的蛇头
        Point newHead = new Point(head.x,head.y);
        //移动新的蛇头
        switch (mDirection){
            case DIRECTION_DOWN:
                newHead.y++;
                break;
            case DIRECTION_LEFT:
                newHead.x--;
                break;
            case DIRECTION_RIGHT:
                newHead.x++;
                break;
            case DIRECTION_UP:
                newHead.y--;
                break;
        }
        //将新的蛇头添加到头部
        mBody.addFirst(newHead);
        //判断是否吃到食物
        if(newHead.equals(mFood)){
            createFood(); //创建新的食物
        }else {
            //删除尾部
            mBody.removeLast();
        }
    }
}
