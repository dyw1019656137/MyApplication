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
import com.example.dyw.myapplication.model.PhoneHouseOwner;
import com.example.dyw.myapplication.model.RecentHouseOwner;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyw on 2017/7/4.
 */
//通话记录
public class PhoneFragment extends BaseFragment{
    private ListView myself_phone_listview = null;
    ArrayList<String> list = new ArrayList<>();
    public List<PhoneHouseOwner> phoneHouseOwnerList;
    MyselfPhoneAdapter myselfPhoneAdapter;
    View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myself_phone_fragment,container,false);

        getNetData();
        return view;
    }
    private void getNetData(){
        phoneHouseOwnerList = DataSupport.findAll(PhoneHouseOwner.class);
        myself_phone_listview = (ListView)view.findViewById(R.id.myself_phone_listview);

        myselfPhoneAdapter = new PhoneFragment.MyselfPhoneAdapter(phoneHouseOwnerList);
        myself_phone_listview.setAdapter(myselfPhoneAdapter);
    }

    class MyselfPhoneAdapter extends BaseAdapter{
        List<PhoneHouseOwner> phoneHouseOwnerList =DataSupport.findAll(PhoneHouseOwner.class);
        public MyselfPhoneAdapter(List<PhoneHouseOwner> phoneHouseOwnerList) {
            this.phoneHouseOwnerList = phoneHouseOwnerList;
        }

        @Override
        public int getCount() {
            return phoneHouseOwnerList.size();
        }

        @Override
        public Object getItem(int position) {
            return phoneHouseOwnerList.get(position).getId();
        }

        @Override
        public long getItemId(int position) {
            return phoneHouseOwnerList.get(position).getId();
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

            Browse_title.setText(phoneHouseOwnerList.get(position).getPhoneHouseOwnerTitle());
//            System.out.println("phoneHouseOwnerList.get(i).getPhoneHouseOwnerTitle():"+phoneHouseOwnerList.get(position).getPhoneHouseOwnerTitle()+"+++++++");
            Browse_content.setText(phoneHouseOwnerList.get(position).getPhoneHouseOwnerContent());
            Browse_location.setText(phoneHouseOwnerList.get(position).getPhoneHouseOwnerLocation());
            Browse_how.setText(phoneHouseOwnerList.get(position).getPhoneHouseOwnerHow());
            if (!TextUtils.isEmpty(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPicUrl1()))
            {
                Picasso.with(getContext()).load(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPicUrl1()).into(Browse_img);
            }

            myself_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    List<PhoneHouseOwner> phoneHouseOwnerList = DataSupport.findAll(PhoneHouseOwner.class);
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerTitle());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerHow());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerDevice());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerInfo());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerLocation());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerContent());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPicUrl1());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPicUrl2());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPicUrl3());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerName());
                    list.add(phoneHouseOwnerList.get(position).getPhoneHouseOwnerPhone());
                    list.add(String.valueOf(phoneHouseOwnerList.get(position).getPhoneHouseOwnerID()));
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
//                    System.out.println("btndelte+id:"+position);
//                    System.out.println("phoneHouseOwnerList.get(position).getPhoneHouseOwnerID()"+phoneHouseOwnerList.get(position).getId());
                    int delete = DataSupport.delete(PhoneHouseOwner.class,phoneHouseOwnerList.get(position).getId());
//                    System.out.println("delete:"+delete);

                    phoneHouseOwnerList.remove(position);
                    myselfPhoneAdapter.notifyDataSetChanged();
                    myself_phone_listview.invalidate();
                }
            });
            return view;
        }
    }
}
