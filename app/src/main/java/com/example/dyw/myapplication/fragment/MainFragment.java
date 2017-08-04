package com.example.dyw.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.LocationAvtivity;
import com.example.dyw.myapplication.activity.MapActivity;
import com.example.dyw.myapplication.activity.PublishActivity;
import com.example.dyw.myapplication.activity.SomeoneActivity;
import com.example.dyw.myapplication.adapter.HouseOwnerAdapter;
import com.example.dyw.myapplication.model.HouseOwner;
import com.example.dyw.myapplication.model.HouseOwnerItem;
import com.example.dyw.myapplication.model.RecentHouseOwner;
import com.example.dyw.myapplication.net.Net;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dyw on 2017/7/4.
 */
//初始主界面
public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {
    View view = null;
    private TextView location;
    private ListView listView;
    ArrayList<String> list = new ArrayList<>();
    private List<HouseOwnerItem> data_houseOwner;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_main, container, false);
        location = (TextView) view.findViewById(R.id.location);
        SearchView searchview =(SearchView) view.findViewById(R.id.searchview);
        searchview.setOnQueryTextListener(this);
        searchview.setSubmitButtonEnabled(false);

        View area = view.findViewById(R.id.publish);
        View map = view.findViewById(R.id.map);
        location.setOnClickListener(new MyClickListener());
        searchview.setOnClickListener(new MyClickListener());
        area.setOnClickListener(new MyClickListener());
        map.setOnClickListener(new MyClickListener());

        return view;
    }
    private void getNetData(){
        OkGo.get(Net.showHouseOwnerIP).tag(getContext()).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                listView = (ListView)view.findViewById(R.id.main_listview);

                //解析返回数据
                Gson gson = new Gson();
                HouseOwner main= gson.fromJson(s,HouseOwner.class);
                data_houseOwner  = main.getData();
//                System.out.println("main:"+main.getData());
                HouseOwnerAdapter adapter = new HouseOwnerAdapter(main.getData());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        list.clear();
                        HouseOwnerItem item = data_houseOwner.get(position);
                        list.add(item.getHouseOwnerTitle());
                        list.add(item.getHouseOwnerHow());
                        list.add(item.getHouseOwnerDevice());
                        list.add(item.getHouseOwnerInfo());
                        list.add(item.getHouseOwnerLocation());
                        list.add(item.getHouseOwnerContent());
                        list.add(item.getHouseOwnerPicurl1());
                        list.add(item.getHouseOwnerPicurl2());
                        list.add(item.getHouseOwnerPicurl3());
                        list.add(item.getHouseOwnerName());
                        list.add(item.getHouseOwnerPhone());
                        list.add(item.getHouseOwnerID());
                        //LitePal存储数据
                        List<RecentHouseOwner> recentHouseOwnerList = DataSupport.findAll(RecentHouseOwner.class);
                        boolean flag = false;
                        if (recentHouseOwnerList.size()==0)
                        {
                            RecentHouseOwner recentHouseOwner = new RecentHouseOwner();
                            recentHouseOwner.setRecentHouseOwnerID(Integer.parseInt(item.getHouseOwnerID()));
                            recentHouseOwner.setRecentHouseOwnerContent(item.getHouseOwnerContent());
                            recentHouseOwner.setRecentHouseOwnerDevice(item.getHouseOwnerDevice());
                            recentHouseOwner.setRecentHouseOwnerHow(item.getHouseOwnerHow());
                            recentHouseOwner.setRecentHouseOwnerInfo(item.getHouseOwnerInfo());
                            recentHouseOwner.setRecentHouseOwnerLocation(item.getHouseOwnerLocation());
                            recentHouseOwner.setRecentHouseOwnerName(item.getHouseOwnerName());
                            recentHouseOwner.setRecentHouseOwnerPhone(item.getHouseOwnerPhone());
                            recentHouseOwner.setRecentHouseOwnerTitle(item.getHouseOwnerTitle());
                            recentHouseOwner.setRecentHouseOwnerPicUrl1(item.getHouseOwnerPicurl1());
                            recentHouseOwner.setRecentHouseOwnerPicUrl2(item.getHouseOwnerPicurl2());
                            recentHouseOwner.setRecentHouseOwnerPicUrl3(item.getHouseOwnerPicurl3());
                            recentHouseOwner.save();
//                            if (recentHouseOwner.isSaved())
//                            {
//                                System.out.println("litepal存储成功1");
//                            }
                        }else {
                            for (int i = 0; i< recentHouseOwnerList.size() ; i++){
                                if (recentHouseOwnerList.get(i).getRecentHouseOwnerID()==Integer.parseInt(item.getHouseOwnerID()))
                                {
//                                    System.out.println("recentHouseOwnerList.get(i).getRecentHouseOwnerID()"+recentHouseOwnerList.get(i).getRecentHouseOwnerID());
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag == false)
                            {
                                RecentHouseOwner recentHouseOwner = new RecentHouseOwner();
                                recentHouseOwner.setRecentHouseOwnerID(Integer.parseInt(item.getHouseOwnerID()));
                                recentHouseOwner.setRecentHouseOwnerContent(item.getHouseOwnerContent());
                                recentHouseOwner.setRecentHouseOwnerDevice(item.getHouseOwnerDevice());
                                recentHouseOwner.setRecentHouseOwnerHow(item.getHouseOwnerHow());
                                recentHouseOwner.setRecentHouseOwnerInfo(item.getHouseOwnerInfo());
                                recentHouseOwner.setRecentHouseOwnerLocation(item.getHouseOwnerLocation());
                                recentHouseOwner.setRecentHouseOwnerName(item.getHouseOwnerName());
                                recentHouseOwner.setRecentHouseOwnerPhone(item.getHouseOwnerPhone());
                                recentHouseOwner.setRecentHouseOwnerTitle(item.getHouseOwnerTitle());
                                recentHouseOwner.setRecentHouseOwnerPicUrl1(item.getHouseOwnerPicurl1());
                                recentHouseOwner.setRecentHouseOwnerPicUrl2(item.getHouseOwnerPicurl2());
                                recentHouseOwner.setRecentHouseOwnerPicUrl3(item.getHouseOwnerPicurl3());
                                recentHouseOwner.save();
//                                if (recentHouseOwner.isSaved())
//                                {
//                                    System.out.println("litepal存储成功");
//                                }
                            }
                        }
                        Bundle data = new Bundle();
                        data.putSerializable("idbl", list);
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
            OkGo.post(Net.searchHouseOwner)
                    .params("searchKey",newText)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            listView = (ListView)view.findViewById(R.id.main_listview);
                            Gson gson = new Gson();
                            HouseOwner main= gson.fromJson(s,HouseOwner.class);
                            data_houseOwner  = main.getData();
                            HouseOwnerAdapter adapter = new HouseOwnerAdapter(main.getData());
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


//    class HouseOwnerAdapter extends BaseAdapter {
//        private List<HouseOwnerItem> data_houseOwner;
//
//        public HouseOwnerAdapter(List<HouseOwnerItem> list){
//            this.data_houseOwner = list;
//        }
//
//        @Override
//        public int getCount() {
//            return data_houseOwner.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return data_houseOwner.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_main_list,null);
//            ImageView houseOwner_img = (ImageView)view.findViewById(R.id.houseOwner_img);
//            TextView houseOwner_title = (TextView)view.findViewById(R.id.houseOwner_title);
//            TextView houseOwner_content = (TextView)view.findViewById(R.id.houseOwner_content);
//            TextView houseOwner_location = (TextView)view.findViewById(R.id.houseOwner_location);
//            TextView houseOwner_how = (TextView)view.findViewById(R.id.houseOwner_how);
//            Log.d("----houseOwner----",houseOwner_title+"");
//            HouseOwnerItem item = data_houseOwner.get(position);
//            houseOwner_title.setText(item.getHouseOwnerTitle());
//            houseOwner_content.setText(item.getHouseOwnerContent());
//            houseOwner_location.setText(item.getHouseOwnerLocation());
//            houseOwner_how.setText(item.getHouseOwnerHow());
//            //加载图片
//            if (!TextUtils.isEmpty(item.getHouseOwnerPicurl1()))
//            {
//                Picasso.with(getContext()).load(item.getHouseOwnerPicurl1()).into(houseOwner_img);
//            }
//            return view;
//        }
//    }

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
