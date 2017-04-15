package com.exe.study.propertyactivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.exe.study.R;

/**
 * @Function
 * @Author Xian
 * @Data 2017/4/15 10:11
 */

public class ObjectActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一 自定义实现属性动画
                //实现透明图渐变动画
                //ObjectAnimator.ofFloat(iv, "alpha", 1.0f, 0.3f).setDuration(2000).start();
                //实现旋转动画
                //ObjectAnimator.ofFloat(iv, "rotationX", 0.0f, 30f).setDuration(2000).start();
                //二 自定义+监听实现属性动画
                /*ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "xian", 1.0f, 0.3f);
                animator.setDuration(3000);
                animator.start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float anim = (float) animation.getAnimatedValue();
                        iv.setAlpha(anim);
                        iv.setScaleX(anim);
                        iv.setScaleY(anim);
                    }
                });*/
                //三 使用PropertyViewHolder实现属性动画
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0.3f);
                PropertyValuesHolder rotationX = PropertyValuesHolder.ofFloat("rotationX", 0f, 30f);
                PropertyValuesHolder rotationY = PropertyValuesHolder.ofFloat("rotationY", 0f, 30f);
                ObjectAnimator.ofPropertyValuesHolder(iv, alpha, rotationX, rotationY)
                        .setDuration(2000).start();
            }
        });

    }
}
