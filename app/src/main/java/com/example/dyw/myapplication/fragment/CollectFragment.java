package com.example.dyw.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.activity.SomeoneActivity;
import com.example.dyw.myapplication.model.CollectHouseOwner;
import com.example.dyw.myapplication.model.RecentHouseOwner;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyw on 2017/7/4.
 */
//我的收藏
public class CollectFragment extends Fragment{
    View view =null;
    private ListView myself_collect_listView = null;
    ArrayList<String> list = new ArrayList<>();
    public List<CollectHouseOwner> collectHouseOwnerList;
    MyselfCollectAdapter myselfCollectAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.collect_layout,container,false);

        getNetData();
        return view;
    }

    private void getNetData() {
        collectHouseOwnerList = DataSupport.findAll(CollectHouseOwner.class);
        myself_collect_listView = (ListView)view.findViewById(R.id.myself_collect_listview);

        myselfCollectAdapter = new MyselfCollectAdapter(collectHouseOwnerList);
        myself_collect_listView.setAdapter(myselfCollectAdapter);
    }

    class MyselfCollectAdapter extends BaseAdapter{

        List<CollectHouseOwner> collectHouseOwnerList = DataSupport.findAll(CollectHouseOwner.class);

        public MyselfCollectAdapter(List<CollectHouseOwner> collectHouseOwnerList) {
            this.collectHouseOwnerList = collectHouseOwnerList;
        }

        @Override
        public int getCount() {
            return collectHouseOwnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return collectHouseOwnerList.get(position).getId();
        }

        @Override
        public long getItemId(int position) {
            return collectHouseOwnerList.get(position).getId();
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

            Browse_title.setText(collectHouseOwnerList.get(position).getCollectHouseOwnerTitle());
//            System.out.println("collectHouseOwnerList.get(position).getCollectHouseOwnerTitle():"+collectHouseOwnerList.get(position).getCollectHouseOwnerTitle()+"+++++++");
            Browse_content.setText(collectHouseOwnerList.get(position).getCollectHouseOwnerContent());
            Browse_location.setText(collectHouseOwnerList.get(position).getCollectHouseOwnerLocation());
            Browse_how.setText(collectHouseOwnerList.get(position).getCollectHouseOwnerHow());
            if (!TextUtils.isEmpty(collectHouseOwnerList.get(position).getCollectHouseOwnerPicUrl1()))
            {
                Picasso.with(getContext()).load(collectHouseOwnerList.get(position).getCollectHouseOwnerPicUrl1()).into(Browse_img);
            }

            myself_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerTitle());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerHow());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerDevice());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerInfo());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerLocation());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerContent());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerPicUrl1());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerPicUrl2());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerPicUrl3());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerName());
                    list.add(collectHouseOwnerList.get(position).getCollectHouseOwnerPhone());
                    list.add(String.valueOf(collectHouseOwnerList.get(position).getCollectHouseOwnerID()));
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
                    int delete = DataSupport.delete(CollectHouseOwner.class,collectHouseOwnerList.get(position).getId());
                    collectHouseOwnerList.remove(position);
                    myselfCollectAdapter.notifyDataSetChanged();
                    myself_collect_listView.invalidate();
                }
            });
            return view;
        }
    }
}
