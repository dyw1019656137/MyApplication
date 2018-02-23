package com.example.dyw.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.MyselfBrowseActivity;
import com.example.dyw.myapplication.activity.MyselfCollectActivity;
import com.example.dyw.myapplication.activity.MyselfPhoneActivity;
import com.example.dyw.myapplication.model.PhoneHouseOwner;
import com.example.dyw.myapplication.model.RecentHouseOwner;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dyw on 2017/7/4.
 */
//我的
public class MyselfFragment extends BaseFragment{
    View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myself_fragment, container, false);
        View myself_collect = view.findViewById(R.id.myself_collect);
        View myself_browse = view.findViewById(R.id.myself_browse);
        View myself_phone = view.findViewById(R.id.myself_phone);
        TextView myself_collect_num = (TextView)view.findViewById(R.id.myself_collect_num);
        TextView myself_browse_num = (TextView)view.findViewById(R.id.myself_browse_num);
        TextView myself_phone_num = (TextView)view.findViewById(R.id.myself_phone_num);
        myself_collect.setOnClickListener(new MyClickListener());
        myself_browse.setOnClickListener(new MyClickListener());
        myself_phone.setOnClickListener(new MyClickListener());


        return view;

    }
    class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.myself_collect:
                    Intent intent_collect = new Intent(getActivity(), MyselfCollectActivity.class);
                    startActivity(intent_collect);
                    break;
                case R.id.myself_browse:
                    Intent intent_browse = new Intent(getActivity(), MyselfBrowseActivity.class);
                    startActivity(intent_browse);
                    break;
                case R.id.myself_phone:
                    Intent intent_phone = new Intent(getActivity(), MyselfPhoneActivity.class);
                    startActivity(intent_phone);
                    break;
            }
        }
    }
}
