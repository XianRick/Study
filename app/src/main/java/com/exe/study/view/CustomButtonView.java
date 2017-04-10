package com.exe.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.exe.study.R;

/**
 * Created by Administrator on 2017/4/8.
 */

public class CustomButtonView extends Button {
    public CustomButtonView(Context context) {
        super(context);
    }

    public CustomButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CustomButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    /**
     * 自定义属性，取得定义后的属性值 后需要再把属性值手动赋予（只赋予一次即可）
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButtonView);
        int i = typedArray.getInt(R.styleable.CustomButtonView_buttonNum, 10);
        int resourceId = typedArray.getResourceId(R.styleable.CustomButtonView_itemBackground, -1);
        setText(i + "");
        setBackgroundResource(resourceId);
        typedArray.recycle();
    }

}
