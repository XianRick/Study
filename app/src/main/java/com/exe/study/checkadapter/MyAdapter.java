package com.exe.study.checkadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.exe.study.R;
import com.exe.study.checkactivity.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Function
 * @Author Xian
 * @Data 2017/4/10 19:08
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private JSONArray jsonArray;
    private View view;
    private Map<String, Boolean> map = new HashMap<String, Boolean>();

    private OnCheckListener onCheckListener;

    public interface OnCheckListener {
        void onCheck(boolean check);
    }

    public void setCheck(boolean checkFlag) {
        map.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String id = jsonArray.getJSONObject(i).getString("ID");
                map.put(id, checkFlag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyCheck(boolean checked) {
        setCheck(checked);
        notifyDataSetChanged();
    }

    public void setData(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        //每次刷新数据都需要重新设置选中状态
        setCheck(false);
        notifyDataSetChanged();
    }

    public MyAdapter(Context context, JSONArray jsonArray, OnCheckListener onCheckListener) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.onCheckListener = onCheckListener;
        setCheck(false);
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return jsonArray.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return position;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.adapter_check, null);
            holder = new ViewHolder();
            holder.check = (CheckBox) view.findViewById(R.id.item_check);
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.iv = (ImageView) view.findViewById(R.id.iv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            final String id = jsonObject.getString("ID");
            holder.tv_title.setText(jsonObject.getString("TITLE"));
            Picasso.with(context).load(jsonObject.getString("IMAGEURL")).into(holder.iv);
            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();
                    map.put(id, checked);
                    boolean isChecked = true;
                    for (String key : map.keySet()) {
                        Boolean value = map.get(key);
                        if (!value) {
                            isChecked = false;
                            onCheckListener.onCheck(isChecked);
                        }
                    }
                    if (isChecked) onCheckListener.onCheck(isChecked);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private class ViewHolder {
        CheckBox check;
        TextView tv_title;
        ImageView iv;
    }
}
