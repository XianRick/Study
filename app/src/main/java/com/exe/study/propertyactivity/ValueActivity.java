package com.exe.study.propertyactivity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.exe.study.R;

/**
 * @Function
 * @Author Xian
 * @Data 2017/4/15 10:35
 */

public class ValueActivity extends Activity implements View.OnClickListener {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);
        iv = (ImageView) findViewById(R.id.iv);
        Button btn_verticalRun = (Button) findViewById(R.id.btn_verticalRun);
        Button btn_parabola = (Button) findViewById(R.id.btn_parabola);
        btn_verticalRun.setOnClickListener(this);
        btn_parabola.setOnClickListener(this);


    }

    public int getWindowsHeight() {
        //获取屏幕
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        return heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verticalRun:
                ValueAnimator animator = ValueAnimator.ofFloat(0, getWindowsHeight() - iv.getHeight());
                animator.setTarget(iv);
                animator.setDuration(3000).start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float height = (float) animation.getAnimatedValue();
                        iv.setTranslationY(height);
                    }
                });
                break;
            case R.id.btn_parabola:
                ValueAnimator animator1 = new ValueAnimator();
                animator1.setDuration(3000);
                animator1.setObjectValues(new PointF(0, 0));
                animator1.setInterpolator(new LinearInterpolator());
                animator1.setEvaluator(new TypeEvaluator<PointF>() {

                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF pointF = new PointF();
                        pointF.x = 200 * fraction * 3;
                        pointF.y = 100 * (fraction * 3) * (fraction * 3);
                        return pointF;
                    }
                });
                animator1.start();
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF = (PointF) animation.getAnimatedValue();
                        iv.setX(pointF.x);
                        iv.setY(pointF.y);
                    }
                });
                break;
        }
    }
}
