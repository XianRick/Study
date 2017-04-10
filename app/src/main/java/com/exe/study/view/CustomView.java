package com.exe.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.exe.study.R;

/**
 * @Function
 * @Author Xian
 * @Data 2017/4/10 9:12
 */

public class CustomView extends LinearLayout {

    public CustomView(Context context) {
        super(context);
        initView();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 自定义类中添加其他布局时，需要把inflate的最后的参数ViewGroup设置成this，而不是null
     */
    private void initView() {
//        LinearLayout.inflate(getContext(), R.layout.activity_main, this);
//        LayoutInflater.from(getContext()).inflate(R.layout.activity_main, this);
        inflate(getContext(), R.layout.activity_main, this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
