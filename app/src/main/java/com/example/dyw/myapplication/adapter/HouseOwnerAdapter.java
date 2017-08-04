package com.example.dyw.myapplication.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.MainFragment;
import com.example.dyw.myapplication.model.HouseOwnerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.lzy.okgo.OkGo.getContext;

/**
 * Created by dyw on 2017/7/17.
 */

public class HouseOwnerAdapter extends BaseAdapter {
    private List<HouseOwnerItem> data_houseOwner;

    public HouseOwnerAdapter(List<HouseOwnerItem> list){
        this.data_houseOwner = list;
    }

    @Override
    public int getCount() {
        return data_houseOwner.size();
    }

    @Override
    public Object getItem(int position) {
        return data_houseOwner.get(position);
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
        TextView houseOwner_content = (TextView)view.findViewById(R.id.houseOwner_content);
        TextView houseOwner_location = (TextView)view.findViewById(R.id.houseOwner_location);
        TextView houseOwner_how = (TextView)view.findViewById(R.id.houseOwner_how);
        Log.d("----houseOwner----",houseOwner_title+"");
        HouseOwnerItem item = data_houseOwner.get(position);
        houseOwner_title.setText(item.getHouseOwnerTitle());
        houseOwner_content.setText(item.getHouseOwnerContent());
        houseOwner_location.setText(item.getHouseOwnerLocation());
        houseOwner_how.setText(item.getHouseOwnerHow());
        //加载图片
        if (!TextUtils.isEmpty(item.getHouseOwnerPicurl1()))
        {
            Picasso.with(getContext()).load(item.getHouseOwnerPicurl1()).into(houseOwner_img);
        }
        return view;
    }
}
