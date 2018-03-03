package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews() {
     image = (ImageView)findViewById(R.id.image);
//        ObjectAnimator.ofFloat(image,"rotation",0,-1080,0).setDuration(5000).start();
//        ObjectAnimator.ofFloat(image,"alpha",1,0).setDuration(2000).start();
//        ObjectAnimator.ofInt(image,"backgroundColor", Color.RED,Color.BLUE,Color.GREEN).setDuration(3000).start();
//  通过间接的类来更新参数
// MyImage myimage = new MyImage(image);

//        ObjectAnimator.ofInt(myimage,"width",800).setDuration(3000).start();

        ValueAnimator anim = ValueAnimator.ofInt(800);
        anim.setDuration(3000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                image.getLayoutParams().width = (int)animation.getAnimatedValue();
                image.requestLayout();
            }
        });
        anim.start();
    }
    class MyImage{
        private ImageView image;
        public MyImage(ImageView image){
            this.image = image;
        }
        public void setWidth(int value){
            image.getLayoutParams().width = value;
            //更新布局参数
            image.requestLayout();
        }
        public int getWidth(){
            return image.getLayoutParams().width;
        }
    }
}
