package com.exe.study.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 广播形式实现Activity与service通信
 * Created by Administrator on 2017/4/1.
 */

public class MyReceiverService extends Service {
    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;

    /**
     * 进度条的进度值
     */
    private int progress = 0;

    private Intent intent = new Intent("com.exe.study.RECEIVER");

    /**
     * 增加get()方法，供Activity调用
     *
     * @return 下载进度
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    progress += 5;

                    //发送Action为com.example.communication.RECEIVER的广播
                    intent.putExtra("progress", progress);
                    sendBroadcast(intent);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDownLoad();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
