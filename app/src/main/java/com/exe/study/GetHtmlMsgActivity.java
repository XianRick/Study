package com.exe.study;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * 获取HTML中的值
 * Created by Administrator on 2017/3/28.
 */

@SuppressWarnings("unused")
public class GetHtmlMsgActivity extends AppCompatActivity {

    private final static int HTML_TO_TEXTVIEW = 0;

    private TextView tv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HTML_TO_TEXTVIEW:
                    Spanned spanned = (Spanned) msg.obj;
                    tv.setText(spanned);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Elements elements = null;
                    //使用Jsoup解析HTML文档
                    Document document = Jsoup.connect("http://home.meishichina.com/recipe/tanggeng/").get();
                    elements = document.select("div.pic");
                    for (int i = 0; i < elements.size(); i++) {
                        String src = elements.get(i).select("a").select("img").attr("data-src");
                        Log.i("xxx", "src" + src);
                    }
                    elements = document.select("div.detail").select("h2").select("a");
                    for (int i = 0; i < elements.size(); i++) {
                        String name = elements.get(i).text();
                        String href = elements.get(i).attr("href");
                        Log.i("xxx", "name" + name);
                        Log.i("xxx", "href" + href);
                    }
                    elements = document.select("p.subcontent");
                    for (int i = 0; i < elements.size(); i++) {
                        String content = elements.get(i).text();
                        Log.i("xxx", "content" + content);
                    }

                    //从HTML中获取图片然后传入TextView中显示
                    String html = "<img src='http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg'/></body></html>";
                    Spanned fromHtml = Html.fromHtml(html, imgGetter, null);
                    Message message = Message.obtain(handler, HTML_TO_TEXTVIEW, fromHtml);
                    message.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            Log.d("Image Path", source);
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };
}
