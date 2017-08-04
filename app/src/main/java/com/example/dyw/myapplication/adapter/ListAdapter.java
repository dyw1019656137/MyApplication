package com.example.dyw.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dyw.myapplication.R;

import java.util.ArrayList;

/**
 * Created by dyw on 2017/7/10.
 */

public class ListAdapter extends BaseAdapter{
    private String[] title;
    private ArrayList<String> city_array;
    private ArrayList<Integer> flag;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListAdapter(String[] title, ArrayList<String> city_array,
                       ArrayList<Integer> flag, Context context) {
        this.title = title;
        this.city_array = city_array;
        this.flag = flag;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return city_array.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return city_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        System.out.println("position:" + position);
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list, null, false);
        }
        TextView text_title = (TextView) convertView
                .findViewById(R.id.cityitem_title);
        TextView text_content = (TextView) convertView
                .findViewById(R.id.cityitem_content);
        if (flag.get(position) >= 0) {
            //noinspection WrongConstant
            text_title.setVisibility(0);
            text_title.setText("\t" + title[flag.get(position)]);
        } else {
            //noinspection WrongConstant
            text_title.setVisibility(8);
        }
        text_title.setTextColor(Color.BLACK);
        text_content.setText("\t" + city_array.get(position));
        text_content.setTextColor(Color.BLACK);
        return convertView;
    }
}
