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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.SomeoneActivity;
import com.example.dyw.myapplication.model.RecentHouseOwner;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dyw on 2017/7/4.
 */
//最近浏览
public class BrowseFragment extends BaseFragment{
    View view = null;
    private ListView myself_browse_listView = null;
    ArrayList<String> list = new ArrayList<>();
    MyselfBrowseAdapter myselfBrowseAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myself_browse_fragment,container,false);
        getNetData();

        return view;
    }
    private void getNetData(){
        List<RecentHouseOwner> recentHouseOwnerList = DataSupport.findAll(RecentHouseOwner.class);
        myself_browse_listView = (ListView)view.findViewById(R.id.myself_browse_listview);

        myselfBrowseAdapter = new MyselfBrowseAdapter(recentHouseOwnerList);
        myself_browse_listView.setAdapter(myselfBrowseAdapter);
    }

    class MyselfBrowseAdapter extends BaseAdapter{
        List<RecentHouseOwner> recentHouseOwnerList = DataSupport.findAll(RecentHouseOwner.class);

        public MyselfBrowseAdapter(List<RecentHouseOwner> recentHouseOwnerList) {
            this.recentHouseOwnerList = recentHouseOwnerList;
        }

        @Override
        public int getCount() {
            return recentHouseOwnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return recentHouseOwnerList.get(position).getId();
        }

        @Override
        public long getItemId(int position) {
            return recentHouseOwnerList.get(position).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_myself_layout,null);

            View myself_linear = (View)view.findViewById(R.id.myself_linear);
            ImageView Browse_img = (ImageView)view.findViewById(R.id.houseOwner_img);
            TextView Browse_title = (TextView)view.findViewById(R.id.houseOwner_title);
            TextView Browse_content = (TextView)view.findViewById(R.id.houseOwner_content);
            TextView Browse_location = (TextView)view.findViewById(R.id.houseOwner_location);
            TextView Browse_how = (TextView)view.findViewById(R.id.houseOwner_how);
            Button btn_myself_Delete = (Button)view.findViewById(R.id.btn_myself_Delete);

            Browse_title.setText(recentHouseOwnerList.get(position).getRecentHouseOwnerTitle());
            System.out.println("recentHouseOwnerList.get(i).getRecentHouseOwnerTitle():"+recentHouseOwnerList.get(position).getRecentHouseOwnerTitle()+"+++++++");
            Browse_content.setText(recentHouseOwnerList.get(position).getRecentHouseOwnerContent());
            Browse_location.setText(recentHouseOwnerList.get(position).getRecentHouseOwnerLocation());
            Browse_how.setText(recentHouseOwnerList.get(position).getRecentHouseOwnerHow());
            if (!TextUtils.isEmpty(recentHouseOwnerList.get(position).getRecentHouseOwnerPicUrl1()))
            {
                Picasso.with(getContext()).load(recentHouseOwnerList.get(position).getRecentHouseOwnerPicUrl1()).into(Browse_img);
            }

            myself_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    List<RecentHouseOwner> recentHouseOwnerList = DataSupport.findAll(RecentHouseOwner.class);
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerTitle());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerHow());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerDevice());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerInfo());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerLocation());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerContent());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerPicUrl1());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerPicUrl2());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerPicUrl3());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerName());
                    list.add(recentHouseOwnerList.get(position).getRecentHouseOwnerPhone());
                    list.add(String.valueOf(recentHouseOwnerList.get(position).getRecentHouseOwnerID()));
                    Bundle data = new Bundle();
                    data.putSerializable("idbl", list);
                    Intent intent = new Intent(getContext(),SomeoneActivity.class);
                    intent.putExtras(data);
                    startActivity(intent);
                }
            });

            btn_myself_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("btndelte+id:"+position);
                    System.out.println("recentHouseOwnerList.get(position).getRecentHouseOwnerID()"+recentHouseOwnerList.get(position).getId());
                    int delete = DataSupport.delete(RecentHouseOwner.class,recentHouseOwnerList.get(position).getId());
                    System.out.println("delete:"+delete);

                    recentHouseOwnerList.remove(position);
                    myselfBrowseAdapter.notifyDataSetChanged();
                    myself_browse_listView.invalidate();
                }
            });
            return view;
        }
    }

}
