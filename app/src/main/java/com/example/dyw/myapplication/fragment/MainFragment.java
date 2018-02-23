package com.example.dyw.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.LocationAvtivity;
import com.example.dyw.myapplication.activity.MapActivity;
import com.example.dyw.myapplication.activity.PublishActivity;
import com.example.dyw.myapplication.activity.SomeoneActivity;
import com.example.dyw.myapplication.adapter.HousesAdapter;
import com.example.dyw.myapplication.model.Houses;
import com.example.dyw.myapplication.model.HousesItem;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.utils.LocationsUntil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dyw on 2017/7/4.
 */
//初始主界面
public class MainFragment extends BaseFragment implements SearchView.OnQueryTextListener {
    View view = null;
    private TextView location;
    private ListView listView;
    ArrayList<String> list = new ArrayList<>();
    private List<HousesItem> data_houses;
    Context mContext;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        location = (TextView) view.findViewById(R.id.location);
        SearchView searchview =(SearchView) view.findViewById(R.id.searchview);
        searchview.setOnQueryTextListener(this);
        searchview.setSubmitButtonEnabled(false);
        mContext = this.getActivity();
        View area = view.findViewById(R.id.publish);
        View map = view.findViewById(R.id.map);
        location.setOnClickListener(new MyClickListener());
        searchview.setOnClickListener(new MyClickListener());
        area.setOnClickListener(new MyClickListener());
        map.setOnClickListener(new MyClickListener());

        return view;
    }
    private void getNetData(){
        OkGo.get(Net.showHouseAllIP).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                listView = (ListView)view.findViewById(R.id.main_listview);
                //解析返回数据
                Gson gson = new Gson();
                Houses main= gson.fromJson(s,Houses.class);
                data_houses  = main.getData();
                HousesAdapter adapter = new HousesAdapter(main.getData(),mContext);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        list.clear();
                        HousesItem item = data_houses.get(position);
                        list.add(item.getHow());
                        list.add(item.getMoney());
                        list.add(item.getTitle());
                        list.add(item.getPicurl1());
                        list.add(String.valueOf(item.getHouse_id()));
                        Bundle data = new Bundle();
                        data.putInt("house_id", item.getHouse_id());
                        Intent intent = new Intent(getContext(),SomeoneActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getNetData();
    }

    //搜索
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText!=null&&newText!="")
        {
            OkGo.post(Net.searchHouseIP)
                    .params("searchKey",newText)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            listView = (ListView)view.findViewById(R.id.main_listview);
                            Gson gson = new Gson();
                            Houses main= gson.fromJson(s,Houses.class);
                            data_houses  = main.getData();
                            HousesAdapter adapter = new HousesAdapter(main.getData(),mContext);
                            listView.setAdapter(adapter);
                        }
                    });
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.location:
                    Intent intent_location = new Intent(getActivity(), LocationAvtivity.class);
                    startActivityForResult(intent_location,100);
                    break;
                case R.id.publish:
                    Intent intent_publish = new Intent(getActivity(), PublishActivity.class);
                    startActivity(intent_publish);
                    break;
                case R.id.map:
                    Intent intent_map = new Intent(getActivity(), MapActivity.class);
                    startActivity(intent_map);
                    break;
            }
        }
    }
}
