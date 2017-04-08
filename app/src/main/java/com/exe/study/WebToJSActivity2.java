package com.exe.study;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * WebView与JS交互2
 */
public class WebToJSActivity2 extends Activity {

    private WebView webview;
    private ProgressDialog pd;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    pd.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_js2);
        pd = new ProgressDialog(this);
        pd.setMessage("正在加载...");
        //webview的简单设置
        webview = (WebView) findViewById(R.id.webView);
        //http://100.65.187.106/test.php
        webview.loadUrl("file:///android_asset/demo2.html");
        WebSettings websettings = webview.getSettings();
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true);
        //js交互
        new MyJavascript().showToast("111");
        websettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavascript(), "Android");
        webview.loadUrl("javascript:documentWrite('测试')");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }
        });
    }

    //暴露给js的功能接口
    public class MyJavascript {
        //显示吐司
        // 如果target 大于等于API 17，则需要加上如下注解
        @JavascriptInterface
        public void showToast(String text) {
            Toast.makeText(WebToJSActivity2.this, text, Toast.LENGTH_LONG).show();
        }

        //显示loading
        @JavascriptInterface
        public void showProgressDialog(String text) {
            pd.setMessage(text);
            pd.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageAtTime(0, 2000);
                }
            }).start();
        }
    }

    //后退键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //菜单键
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "刷新");
        menu.add(0, 0, 1, "后退");
        menu.add(0, 0, 2, "前进");
        return super.onCreateOptionsMenu(menu);
    }

    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getOrder()) {
            case 0:
                webview.reload();
                break;
            case 1:
                if (webview.canGoBack()) {
                    webview.goBack();
                }
                break;
            case 2:
                if (webview.canGoForward()) {
                    webview.goForward();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}