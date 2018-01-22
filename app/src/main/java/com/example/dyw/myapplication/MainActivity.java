package com.example.dyw.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.activity.LoginActivity;
import com.example.dyw.myapplication.activity.MyselfBrowseActivity;
import com.example.dyw.myapplication.activity.MyselfCollectActivity;
import com.example.dyw.myapplication.activity.MyselfPhoneActivity;
import com.example.dyw.myapplication.activity.UpdateUserActivity;
import com.example.dyw.myapplication.loadimage.Utils;
import com.example.dyw.myapplication.model.HouseOwner;
import com.example.dyw.myapplication.model.HouseOwnerItem;
import com.example.dyw.myapplication.fragment.MainFragment;
import com.example.dyw.myapplication.fragment.MyselfFragment;
import com.example.dyw.myapplication.fragment.OtherFragment;
import com.example.dyw.myapplication.model.User;
import com.example.dyw.myapplication.model.UserLocal;
import com.example.dyw.myapplication.net.Net;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.meg7.widget.CircleImageView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;

public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;    //fragment 控制
    OtherFragment otherFragment;    //其他 fragment
    MyselfFragment myselfFragment;  //我的 fragment
    MainFragment mainFragment;      //主要 fragment
    private NavigationView navigationView;  //初始定义NavigationView
    private List<UserLocal> userLocalList;
    //LoginActivity传入MainActivity数据
    private String userid;
    private String username;
    private String password;
    private String userinfo;
    private String phonenum;
    private String headurl;
    //UpdateUserActivity 返回MainActivity的数据
    private String up_userid;

    //MainActivity 使用的数据
    private String main_userid;
    private String main_headurl;

    CircleImageView headPic;
    View headLayout;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = new MainFragment();
        otherFragment = new OtherFragment();
        myselfFragment = new MyselfFragment();

        //创建数据库LitePal
        SQLiteDatabase db = Connector.getDatabase();
        System.out.println(db);

        //up_userid 为null，表示无返回值，直接使用LoginActivity传的数值
        //否则    使用UpdateUserActivity返回的数值
        if (up_userid==null)
        {
            getLoginActivity();
            main_userid = userid;
        }else {
            main_userid = up_userid;
        }
        //头像的接口地址不变 URL:http://192.168.33.37:8080/House/houseimage/headurl_13151567786.jpg
        main_headurl = headurl;

        //通过NavigationView，找到控件CircleImageView
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //设置item选中监听，判断选中的item
        navigationView.setNavigationItemSelectedListener(this);
        headLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);

//        //头像的显示
//        headPic = (CircleImageView)headLayout.findViewById(R.id.headPic);
//
//        Picasso.with(this).load(main_headurl).placeholder(R.drawable.headimage).memoryPolicy(MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_STORE).fit().into(headPic);
//        //头像设置点击事件，进入UpdateUserAvtivity界面
//        headPic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_update = new Intent(MainActivity.this,UpdateUserActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("main_userid",main_userid);
//                intent_update.putExtras(bundle);
//                startActivityForResult(intent_update,200);
//            }
//        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //MainActivity底部，判断点击了哪个fragment（mainFragment、otherFragment、myselfFragment）
        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Log.d("-----测试tabId-----",tabId+"");
                switch (tabId){
                    //显示和隐藏fragment

                    case R.id.tab_home:
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction_main = fragmentManager.beginTransaction();
                        if (!mainFragment.isAdded())
                        {
                            transaction_main.add(R.id.fragment,mainFragment);
                        }else {
                            transaction_main.hide(otherFragment);
                            transaction_main.hide(myselfFragment);
                            transaction_main.show(mainFragment);
                        }

                        transaction_main.commit();
                        break;
                    case R.id.tab_myself:
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction_myself = fragmentManager.beginTransaction();
                        if (!myselfFragment.isAdded())
                        {
                            transaction_myself.add(R.id.fragment,myselfFragment);
                        }else {
                            transaction_myself.hide(otherFragment);
                            transaction_myself.hide(mainFragment);
                            transaction_myself.show(myselfFragment);
                        }
                        transaction_myself.commit();

                        break;
                    case R.id.tab_other:
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction_other = fragmentManager.beginTransaction();
                        if (!otherFragment.isAdded())
                        {
                            transaction_other.add(R.id.fragment,otherFragment);
                        }else {
                            transaction_other.hide(myselfFragment);
                            transaction_other.hide(mainFragment);
                            transaction_other.show(otherFragment);
                        }
                        transaction_other.commit();
                        break;
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //头像的显示
        headPic = (CircleImageView)headLayout.findViewById(R.id.headPic);

        Picasso.with(this).load(main_headurl)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(headPic);
        //头像设置点击事件，进入UpdateUserAvtivity界面
        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_update = new Intent(MainActivity.this,UpdateUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("main_userid",main_userid);
                intent_update.putExtras(bundle);
                startActivityForResult(intent_update,200);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 1:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String str=b.getString("location_gps");//str即为回传的值
                TextView location = (TextView)findViewById(R.id.location);
                location.setText(str);
//                System.out.println("location"+location);
                break;
            case 2:
                Bundle bundle = data.getExtras();
                up_userid = bundle.getString("up_userid");
                System.out.println("UpdateUserActivity传回来的up_userid："+up_userid);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    /*
    * 侧滑栏的选中
    * 1、我的收藏 跳转到 MyselfCollectActivity 页面
    * 2、最近浏览 跳转到 MyselfBrowseActivity 页面
    * 3、通话记录 跳转到 MyselfPhoneActivity 页面
    * 4、注销    跳转到 LoginActivity 页面   并删除LitePal里的用户数据   （未完成）
    * 5、切换账号 跳转到 LoginActivity 页面
    * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent intent_collect = new Intent(MainActivity.this, MyselfCollectActivity.class);
            startActivity(intent_collect);
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_sign) {
            Intent intent_browse = new Intent(MainActivity.this, MyselfBrowseActivity.class);
            startActivity(intent_browse);
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_phone) {
            Intent intent_phone = new Intent(MainActivity.this, MyselfPhoneActivity.class);
            startActivity(intent_phone);
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_cancel) {
            Intent intent_cancel = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent_cancel);

            DataSupport.deleteAll(UserLocal.class,"userid = ?",main_userid);
            System.out.println("用户数据已删除");

            MainActivity.this.finish();
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_change) {
            Intent intent_change = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent_change);
            MainActivity.this.finish();
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
        }

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * 获取到 LoginActivity 传过来的数据
    * 1、用户ID  String userid
    * 2、用户名  String username
    * 3、密码  String password
    * 4、个人信息  String userinfo
    * 5、用户手机号  String phonenum
    * 6、用户头像接口  String headurl
    * 7、boolean flag 判断Litepal中是否存在  true存在(修改用户数据)   false不存在（存储用户数据）
    * 8、userLocalList  LitePal中 userlocal表的数据集合
    * */
    public void getLoginActivity(){
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userid");
        username = bundle.getString("username");
        password = bundle.getString("password");
        userinfo = bundle.getString("userinfo");
        phonenum = bundle.getString("phonenum");
        headurl = bundle.getString("headurl");
        boolean flag=false;
        //查询Litepal 中userlocal表中的数据，判断是否存在
        userLocalList = DataSupport.findAll(UserLocal.class);
        for (int i = 0;i<userLocalList.size();i++)
        {
            if (userid.equals(userLocalList.get(i).getUserid()))
            {
                flag = true;
                break;
            }
        }
        if (flag==false)
        {
            //用户在LitePal中不存在，开始存入Litepal
            UserLocal userLocal = new UserLocal();
            userLocal.setUserid(userid);
            userLocal.setUsername(username);
            userLocal.setPassword(password);
            userLocal.setUserinfo(userinfo);
            userLocal.setPhonenum(phonenum);
            userLocal.setHeadurl(headurl);
            userLocal.save();
            if (userLocal.isSaved())
            {
                System.out.println("用户初次进入，存储数据成功");
            }
        }else {
            //用户存在，修改用户在数据
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password",password);
            values.put("userinfo",userinfo);
            values.put("phonenum",phonenum);
            values.put("headurl",headurl);
            DataSupport.updateAll(UserLocal.class, values, "userid = ?", userid);
        }
    }



}
