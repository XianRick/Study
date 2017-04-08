package com.exe.study.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.exe.study.R;

/**
 * 调用广播 形式实现Activity与service通信
 * Created by Administrator on 2017/4/1.
 */

public class MyReceiverActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private Intent mIntent;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //动态注册广播接收器
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.communication.RECEIVER");
        registerReceiver(myReceiver, intentFilter);

        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //启动服务
                mIntent = new Intent("com.exe.study.MY_ACTION");
                startService(mIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //停止服务
        stopService(mIntent);
        //注销广播
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    /**
     * 广播接收器
     *
     * @author len
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            int progress = intent.getIntExtra("progress", 0);
            mProgressBar.setProgress(progress);
        }

    }
}
