package com.exe.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Function 自定义圆
 * @Author Xian
 * @Data 2017/4/8 11:38
 */

public class CustomCircleView extends View {

    private float currentX = 100;
    private float currentY = 100;

    public CustomCircleView(Context context) {
        super(context);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        //自定义画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setAlpha(3);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(currentX, currentY, 50, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();
        //组件移动时的监控
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("xxx","按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("xxx","移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("xxx","弹起");
                break;
        }
        return true;
    }
}
