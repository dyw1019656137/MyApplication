package com.example.dyw.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.CollectHouseOwner;
import com.example.dyw.myapplication.model.PhoneHouseOwner;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.viewpager.MySomeoneViewpager;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SomeoneActivity extends BaseActivity {
    private String[] mImgUrl;
    private String houseOwnerPhone;
    private MySomeoneViewpager mySomeoneViewpager;
    List<CollectHouseOwner> collectHouseOwnerList;
    private int house_id;
    private int user_id;
    private String picurl1;
    private String picurl2;
    private String picurl3;
    private String title;
    private String money;
    private String how;
    private String info;
    private String device;
    private Double latitude;
    private Double longitude;
    private int isCollection;
    TextView main_someone_title;
    TextView main_someone_money;
    TextView main_someone_location;
    TextView main_someone_how;
    TextView main_someone_device;
    TextView main_someone_info;
    TextView main_someone_name;
    View main_someone_phone;
    LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_someone);
        Bundle bundle = this.getIntent().getExtras();
        house_id = bundle.getInt("house_id");
        initViews();
        getData();

//        collectHouseOwnerList = DataSupport.findAll(CollectHouseOwner.class);
//        System.out.println("SomeoneActivity collectHouseOwnerList:"+collectHouseOwnerList.size());
//        if (collectHouseOwnerList.size()!=0)
//        {
//            List<CollectHouseOwner> collectHouseOwnerList = DataSupport.where("collectHouseOwnerID = ?", String.valueOf(list.get(11))).find(CollectHouseOwner.class);
//            System.out.println("SomeoneActivity isCollected collectHouseOwnerID:"+list.get(11));
//            boolean flag = false;
//            if (collectHouseOwnerList.size()!=0)
//            {
//                for (int i = 0;i<collectHouseOwnerList.size();i++)
//                {
//                    if (collectHouseOwnerList.get(i).getIsCollect()==1)
//                    {
//                        flag = true;
//                        break;
//                    }
//                }
//                if (flag)
//                {
//                    likeButton.setLiked(true);
//                }
//            }
//        }

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
//                //LitePal存储数据
//                collectHouseOwnerList = DataSupport.findAll(CollectHouseOwner.class);
//                CollectHouseOwner collectHouseOwner = new CollectHouseOwner();
//                boolean flag = false;
//                if (collectHouseOwnerList.size()==0)
//                {
//                    collectHouseOwner.setCollectHouseOwnerID(Integer.parseInt(String.valueOf(list.get(11))));
//                    collectHouseOwner.setCollectHouseOwnerContent((String) list.get(5));
//                    collectHouseOwner.setCollectHouseOwnerDevice((String) list.get(2));
//                    collectHouseOwner.setCollectHouseOwnerHow((String) list.get(1));
//                    collectHouseOwner.setCollectHouseOwnerInfo((String) list.get(3));
//                    collectHouseOwner.setCollectHouseOwnerLocation((String) list.get(4));
//                    collectHouseOwner.setCollectHouseOwnerName((String) list.get(9));
//                    collectHouseOwner.setCollectHouseOwnerPhone((String) list.get(10));
//                    collectHouseOwner.setCollectHouseOwnerTitle((String) list.get(0));
//                    collectHouseOwner.setCollectHouseOwnerPicUrl1((String) list.get(6));
//                    collectHouseOwner.setCollectHouseOwnerPicUrl2((String) list.get(7));
//                    collectHouseOwner.setCollectHouseOwnerPicUrl3((String) list.get(8));
//                    collectHouseOwner.setIsCollect(1);
//                    collectHouseOwner.save();
//                    if (collectHouseOwner.isSaved())
//                    {
//                        System.out.println("collectHouseOwner     litepal存储成功1");
//                    }
//                }else
//                {
//                    for (int i = 0; i< collectHouseOwnerList.size() ; i++){
//                        if (collectHouseOwnerList.get(i).getCollectHouseOwnerID()==Integer.parseInt(String.valueOf(list.get(11))))
//                        {
//                            System.out.println("collectHouseOwnerList.get(i).getCollectHouseOwnerID()"+collectHouseOwnerList.get(i).getCollectHouseOwnerID());
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (flag == false)
//                    {
//                        collectHouseOwner.setCollectHouseOwnerID(Integer.parseInt(String.valueOf(list.get(11))));
//                        collectHouseOwner.setCollectHouseOwnerContent((String) list.get(5));
//                        collectHouseOwner.setCollectHouseOwnerDevice((String) list.get(2));
//                        collectHouseOwner.setCollectHouseOwnerHow((String) list.get(1));
//                        collectHouseOwner.setCollectHouseOwnerInfo((String) list.get(3));
//                        collectHouseOwner.setCollectHouseOwnerLocation((String) list.get(4));
//                        collectHouseOwner.setCollectHouseOwnerName((String) list.get(9));
//                        collectHouseOwner.setCollectHouseOwnerPhone((String) list.get(10));
//                        collectHouseOwner.setCollectHouseOwnerTitle((String) list.get(0));
//                        collectHouseOwner.setCollectHouseOwnerPicUrl1((String) list.get(6));
//                        collectHouseOwner.setCollectHouseOwnerPicUrl2((String) list.get(7));
//                        collectHouseOwner.setCollectHouseOwnerPicUrl3((String) list.get(8));
//                        collectHouseOwner.setIsCollect(1);
//                        collectHouseOwner.save();
//                        if (collectHouseOwner.isSaved())
//                        {
//                            System.out.println("collectHouseOwner     litepal存储成功");
//                        }
//                    }
//                }
                Toast.makeText(SomeoneActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
//                CollectHouseOwner collectHouseOwner = new CollectHouseOwner();
//                DataSupport.deleteAll(CollectHouseOwner.class,"collectHouseOwnerID = ?", String.valueOf(list.get(11)));
                Toast.makeText(SomeoneActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
            }
        });

//        main_someone_title.setText((String) list.get(0));
//        main_someone_how.setText((String) list.get(1));
//        main_someone_device.setText((String) list.get(2));
//        main_someone_info.setText((String) list.get(3));
//        main_someone_location.setText((String) list.get(4));
//        main_someone_content.setText((String) list.get(5));
//        main_someone_name.setText((String) list.get(9));
//        String picurl1 = (String) list.get(6);
//        String picurl2 = (String) list.get(7);
//        String picurl3 = (String) list.get(8);
//        houseOwnerPhone = (String) list.get(10);
        mImgUrl = new String[]{picurl1, picurl2, picurl3};
        mySomeoneViewpager = (MySomeoneViewpager) findViewById(R.id.main_Someone_img);
        mySomeoneViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgUrl.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(SomeoneActivity.this);
                Picasso.with(SomeoneActivity.this).load(mImgUrl[position]).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mySomeoneViewpager.setObjectForPosition(imageView, position);
                return imageView;
            }
        });


        main_someone_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + houseOwnerPhone));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                //LitePal存储数据
//                List<PhoneHouseOwner> phoneHouseOwnerList = DataSupport.findAll(PhoneHouseOwner.class);
//                boolean flag = false;
//                if (phoneHouseOwnerList.size()==0)
//                {
//                    PhoneHouseOwner phoneHouseOwner = new PhoneHouseOwner();
//                    phoneHouseOwner.setPhoneHouseOwnerID(Integer.parseInt(String.valueOf(list.get(11))));
//                    phoneHouseOwner.setPhoneHouseOwnerContent((String) list.get(5));
//                    phoneHouseOwner.setPhoneHouseOwnerDevice((String) list.get(2));
//                    phoneHouseOwner.setPhoneHouseOwnerHow((String) list.get(1));
//                    phoneHouseOwner.setPhoneHouseOwnerInfo((String) list.get(3));
//                    phoneHouseOwner.setPhoneHouseOwnerLocation((String) list.get(4));
//                    phoneHouseOwner.setPhoneHouseOwnerName((String) list.get(9));
//                    phoneHouseOwner.setPhoneHouseOwnerPhone((String) list.get(10));
//                    phoneHouseOwner.setPhoneHouseOwnerTitle((String) list.get(0));
//                    phoneHouseOwner.setPhoneHouseOwnerPicUrl1((String) list.get(6));
//                    phoneHouseOwner.setPhoneHouseOwnerPicUrl2((String) list.get(7));
//                    phoneHouseOwner.setPhoneHouseOwnerPicUrl3((String) list.get(8));
//                    phoneHouseOwner.save();
//                    if (phoneHouseOwner.isSaved())
//                    {
//                        System.out.println("phoneHouseOwner     litepal存储成功1");
//                    }
//                }else
//                {
//                    for (int i = 0; i< phoneHouseOwnerList.size() ; i++){
//                        if (phoneHouseOwnerList.get(i).getPhoneHouseOwnerID()==Integer.parseInt(String.valueOf(list.get(11))))
//                        {
//                            System.out.println("phoneHouseOwnerList.get(i).getPhoneHouseOwnerID()"+phoneHouseOwnerList.get(i).getPhoneHouseOwnerID());
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (flag == false)
//                    {
//                        PhoneHouseOwner phoneHouseOwner = new PhoneHouseOwner();
//                        phoneHouseOwner.setPhoneHouseOwnerID(Integer.parseInt(String.valueOf(list.get(11))));
//                        phoneHouseOwner.setPhoneHouseOwnerContent((String) list.get(5));
//                        phoneHouseOwner.setPhoneHouseOwnerDevice((String) list.get(2));
//                        phoneHouseOwner.setPhoneHouseOwnerHow((String) list.get(1));
//                        phoneHouseOwner.setPhoneHouseOwnerInfo((String) list.get(3));
//                        phoneHouseOwner.setPhoneHouseOwnerLocation((String) list.get(4));
//                        phoneHouseOwner.setPhoneHouseOwnerName((String) list.get(9));
//                        phoneHouseOwner.setPhoneHouseOwnerPhone((String) list.get(10));
//                        phoneHouseOwner.setPhoneHouseOwnerTitle((String) list.get(0));
//                        phoneHouseOwner.setPhoneHouseOwnerPicUrl1((String) list.get(6));
//                        phoneHouseOwner.setPhoneHouseOwnerPicUrl2((String) list.get(7));
//                        phoneHouseOwner.setPhoneHouseOwnerPicUrl3((String) list.get(8));
//                        phoneHouseOwner.save();
//                        if (phoneHouseOwner.isSaved())
//                        {
//                            System.out.println("phoneHouseOwner     litepal存储成功");
//                        }
//                    }
//                }
                startActivity(intent);//开始意图(及拨打电话)
            }
        });
    }

    private void initViews() {
         main_someone_title = (TextView) findViewById(R.id.main_someone_title);
         main_someone_money = (TextView) findViewById(R.id.main_someone_content);
         main_someone_location = (TextView) findViewById(R.id.main_Someone_location);
         main_someone_how = (TextView) findViewById(R.id.main_someone_how);
         main_someone_device = (TextView) findViewById(R.id.main_someone_device);
         main_someone_info = (TextView) findViewById(R.id.main_someone_info);
         main_someone_name = (TextView) findViewById(R.id.main_someone_name);
         main_someone_phone = (View) findViewById(R.id.main_someone_phone);
         likeButton = (LikeButton)findViewById(R.id.star_button);
    }

    private void getData() {
        OkGo.get(Net.showHouseByIDIP)
                .tag(this)
                .params("house_id",house_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject obt = object.getJSONObject("data");
                            how = obt.getString("how");
                            picurl1 = obt.getString("picurl1");
                            picurl2 = obt.getString("picurl2");
                            picurl3 = obt.getString("picurl3");
                            money = obt.getString("money");
                            longitude = obt.getDouble("longitude");
                            latitude = obt.getDouble("latitude");
                            title = obt.getString("title");
                            device = obt.getString("device");
                            info = obt.getString("info");
                            isCollection = obt.getInt("isCollection");
                            main_someone_title.setText(title);
                            main_someone_money.setText(money);
                            main_someone_how.setText(how);
                            main_someone_device.setText(device);
                            main_someone_info.setText(info);
                            if (isCollection == 1){
                                likeButton.setLiked(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
