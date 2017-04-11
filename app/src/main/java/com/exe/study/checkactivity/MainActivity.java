package com.exe.study.checkactivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;
import com.exe.study.R;
import com.exe.study.checkadapter.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Function
 * @Author Xian
 * @Data 2017/4/10 19:08
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipyRefreshLayout srl;
    private ListView lv;
    private CheckBox checkBox;
    private int startNum = 0;
    private Handler handler = new Handler();
    private JSONArray jsonArray;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_check);
        srl = (SwipyRefreshLayout) findViewById(R.id.srl);
        lv = (ListView) findViewById(R.id.lv);
        checkBox = (CheckBox) findViewById(R.id.check);
        checkBox.setOnClickListener(this);

        srl.setColorSchemeColors(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, android.R.color.holo_blue_dark);
        srl.setDirection(SwipyRefreshLayoutDirection.BOTH);
        srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                //每次刷新都重置CheckBox
                checkBox.setChecked(false);
                startNum = 0;
                getData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onLoad(int index) {
                //每次加载都重置CheckBox
                checkBox.setChecked(false);
                startNum++;
                getData();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private void getData() {
        String url = "http://www.93.gov.cn/93app/data.do?" + "channelId=" + 0 + "&startNum=" + startNum;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (startNum == 0){
                        jsonArray = new JSONArray();
                    }
                    for (int i = 0; i<data.length();i++){
                        jsonArray.put(data.get(i));
                    }
                    //构建ListView
                    initListView(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(stringRequest);
    }

    private void initListView(JSONArray jsonArray) {
        if (adapter == null){
            adapter = new MyAdapter(this, jsonArray, new MyAdapter.OnCheckListener() {
                @Override
                public void onCheck(boolean check) {
                    checkBox.setChecked(check);
                }
            });
            adapter.setData(jsonArray);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(jsonArray);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check:
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    //全选
                    adapter.notifyCheck(checked);
                } else {
                    //取消全选
                    adapter.notifyCheck(checked);
                }
                break;
        }
    }
}
