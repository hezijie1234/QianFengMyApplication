package com.qianfeng.day16_objectanimator_use;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_view);
    }

    public void onClickAnimator(View view) {
        //使用属性动画
//        ObjectAnimator
//                .ofFloat(mImageView,"rotation",0,-1080,0)
//                .setDuration(3000)
//                .start();
//        ObjectAnimator
//                .ofInt(mImageView,"backgroundColor",
//                        Color.RED,Color.YELLOW,Color.GREEN)
//                .setDuration(3000)
//                .start();
//        ObjectAnimator
//                  .ofFloat(mImageView,"width", 200)
//                .setDuration(2000)
//                .start();
//        ObjectAnimator.ofFloat(mImageView,"alpha",1.0f,0f,1.0f)
//                .setDuration(4000)
//                .start();
        //使用间接类，修改属性
//        MyView myView = new MyView(mImageView);
//        ObjectAnimator.ofInt(myView,"width",800)
//                .setDuration(3000)
//                .start();
        //使用ValueAnimator,在更新值的过程中，修改属性

        ValueAnimator anim = ValueAnimator.ofInt(800);
        anim.setDuration(3000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //修改ImageView的width
                mImageView.getLayoutParams().width = (int)animation.getAnimatedValue();
                //更新布局
                mImageView.requestLayout();
            }
        });
        anim.start();
    }

    public void onClickAnimatorSet(View view) {
        ObjectAnimator anim1 = ObjectAnimator
                .ofFloat(mImageView,"rotation",0,-1080,0);

        ObjectAnimator anim2 = ObjectAnimator
                .ofInt(mImageView,"backgroundColor",
                        Color.RED,Color.YELLOW,Color.GREEN);

        ObjectAnimator anim3 =
                ObjectAnimator.ofFloat(mImageView,"alpha",1.0f,0f,1.0f);

        //使用动画集合
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(anim1).with(anim2).after(anim3);
        set.start();
    }

    //自定义类，间接修改控件的width属性
    class MyView {
        private ImageView imageView;
        public MyView(ImageView imageView){
            this.imageView = imageView;
        }
        public void setWidth(int width){
            //修改ImageView的width
            this.imageView.getLayoutParams().width = width;
            //更新布局
            this.imageView.requestLayout();
        }
        public int getWidth(){
            return this.imageView.getLayoutParams().width;
        }
    }
}
