package com.exe.study.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.exe.study.R;
import com.exe.study.service.MyService;

/**
 * 采用Binder实现Activity与service通信，两种方式
 * 1：直接调用service中提供的方法，这种方法需要在子线程中运行
 * 2：采用回调的形式（推荐）
 * Created by Administrator on 2017/4/1.
 */

public class MyActivity extends AppCompatActivity {

    private Button btn;
    private MyService myService;
    private int progress = 0;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Intent intent = new Intent("com.exe.study.MSG_ACTION");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //开始下载
                myService.startDownLoad();
                /**
                 * 监听进度--主动调用getProgress()来获取进度值，然后隔一秒在调用一次getProgress()方法
                 * 这里使用监听，不适用当前方式
                 */
                //listenProgress();
            }
        });
    }

    private void listenProgress() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < MyService.MAX_PROGRESS) {
                    progress = myService.getProgress();
                    mProgressBar.setProgress(progress);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            myService = ((MyService.MyBinder) service).getService();
            //使用 监听形式返回进度
            myService.setOnProgressListener(new MyService.OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    mProgressBar.setProgress(progress);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
