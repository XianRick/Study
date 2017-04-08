package com.exe.study;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 屏幕左右上下活动的监听
 */
public class GestureDetectorActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //向左滑动
                if((e1.getRawX()-e2.getRawX())>100){
                    return true;
                }
                //向右滑动
                if((e2.getRawX()-e1.getRawX())>100){
                    return true;
                }

                //判断手指下滑距离
                if (Math.abs(velocityY) < 100) {
                    //手指移动的太慢了
                    return true;
                }
                //手势向下 down
                if ((e2.getRawY() - e1.getRawY()) > 200) {
                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                    finish();
                    return true;
                }
                //手势向上 up
                if ((e1.getRawY() - e2.getRawY()) > 200) {
                    /**
                     * Android调用浏览器
                     * 默认浏览器："com.android.browser", "com.android.browser.BrowserActivity"可以不调用
                     * uc浏览器："com.uc.browser", "com.uc.browser.ActivityUpdate“
                     * opera："com.opera.mini.android", "com.opera.mini.android.Browser"
                     * qq浏览器："com.tencent.mtt", "com.tencent.mtt.GestureDetectorActivity"
                     */
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://www.baidu.com/");
                    intent.setData(content_url);
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    startActivity(intent);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
