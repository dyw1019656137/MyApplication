package com.example.dyw.myapplication.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.HouseItem;
import com.example.dyw.myapplication.model.HousesItem;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.utils.LocationsUntil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;

/**
 * Created by dyw on 2017/7/17.
 */

public class HousesAdapter extends BaseAdapter {
    private List<HousesItem> data_houses;
    Context mContext;

    public HousesAdapter(List<HousesItem> list,Context mContext){
        this.data_houses = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data_houses.size();
    }

    @Override
    public Object getItem(int position) {
        return data_houses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_main_list,null);
        ImageView houseOwner_img = (ImageView)view.findViewById(R.id.houseOwner_img);
        TextView houseOwner_title = (TextView)view.findViewById(R.id.houseOwner_title);
        TextView houseOwner_money = (TextView)view.findViewById(R.id.houseOwner_content);
        final TextView houseOwner_location = (TextView)view.findViewById(R.id.houseOwner_location);
        TextView houseOwner_how = (TextView)view.findViewById(R.id.houseOwner_how);
        HousesItem item = data_houses.get(position);
        double longitude = item.getLongitude();
        double latitude = item.getLatitude();
        final String[] locations = new String[1];
//        //正式启动，打开
//        OkGo.post(Net.locationsIP)
//                .params("lat",longitude)
//                .params("lng",latitude)
//                .params("type","baidu")
//                .params("appkey","c739842be9f46c94")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        try {
//                            JSONObject obt = new JSONObject(s);
//                            JSONObject obt1 = obt.getJSONObject("result");
//                            locations[0] = obt1.getString("address");
//                            houseOwner_location.setText(locations[0]);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
        houseOwner_title.setText(item.getTitle());
        houseOwner_money.setText(item.getMoney());
        houseOwner_how.setText(item.getHow());
        //加载图片
        if (!TextUtils.isEmpty(item.getPicurl1()))
        {
            Picasso.with(getContext()).load(item.getPicurl1()).into(houseOwner_img);
        }
        return view;
    }
}
