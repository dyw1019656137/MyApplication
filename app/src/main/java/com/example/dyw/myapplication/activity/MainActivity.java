package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.MainFragment;
import com.example.dyw.myapplication.fragment.MyselfFragment;
import com.example.dyw.myapplication.fragment.OtherFragment;
import com.example.dyw.myapplication.model.UserLocal;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.meg7.widget.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    OtherFragment otherFragment;    //其他 fragment
    MyselfFragment myselfFragment;  //我的 fragment
    MainFragment mainFragment;      //主要 fragment
    private NavigationView navigationView;  //初始定义NavigationView
    private List<UserLocal> userLocalList;
    //LoginActivity传入MainActivity数据
    private int userid;
    //UpdateUserActivity 返回MainActivity的数据
//    private int up_userid=0;
    //MainActivity 使用的数据
//    private int main_userid;
    private String main_headurl;

    CircleImageView headPic;
    View headLayout;
    DrawerLayout drawer;
    RelativeLayout house_main_tab_bottom;
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;

    FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //创建数据库LitePal
//        SQLiteDatabase db = Connector.getDatabase();

        //up_userid 为0，表示无返回值，直接使用LoginActivity传的数值
        //否则    使用UpdateUserActivity返回的数值

        //头像的接口地址不变 URL:http://192.168.33.37:8080/House/houseimage/headurl_13151567786.jpg
//        main_headurl = headurl;

        //通过NavigationView，找到控件CircleImageView
        final View decorView = this.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                decorView.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
                int screenHeight = decorView.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    house_main_tab_bottom.setVisibility(View.GONE);
                } else {
                    house_main_tab_bottom.setVisibility(View.VISIBLE);
                }
            }
        });
        //MainActivity底部，判断点击了哪个fragment（mainFragment、otherFragment、myselfFragment）
//        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                Log.d("-----测试tabId-----",tabId+"");
//                switch (tabId){
//                    //显示和隐藏fragment
//                    case R.id.tab_home:
//                        fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction transaction_main = fragmentManager.beginTransaction();
//                        if (!mainFragment.isAdded())
//                        {
//                            transaction_main.add(R.id.fragment,mainFragment);
//                        }else {
//                            transaction_main.hide(otherFragment);
//                            transaction_main.hide(myselfFragment);
//                            transaction_main.show(mainFragment);
//                        }
//
//                        transaction_main.commit();
//                        break;
//                    case R.id.tab_myself:
//                        fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction transaction_myself = fragmentManager.beginTransaction();
//                        if (!myselfFragment.isAdded())
//                        {
//                            transaction_myself.add(R.id.fragment,myselfFragment);
//                        }else {
//                            transaction_myself.hide(otherFragment);
//                            transaction_myself.hide(mainFragment);
//                            transaction_myself.show(myselfFragment);
//                        }
//                        transaction_myself.commit();
//
//                        break;
//                    case R.id.tab_other:
//                        fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction transaction_other = fragmentManager.beginTransaction();
//                        if (!otherFragment.isAdded())
//                        {
//                            transaction_other.add(R.id.fragment,otherFragment);
//                        }else {
//                            transaction_other.hide(myselfFragment);
//                            transaction_other.hide(mainFragment);
//                            transaction_other.show(otherFragment);
//                        }
//                        transaction_other.commit();
//                        break;
//                }
//
//            }
//        });
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void initViews() {
        mContext = MainActivity.this;
        userid = Integer.parseInt(Utils.getSharedString(mContext,Utils.STRING_USERID));
        house_main_tab_bottom = (RelativeLayout) findViewById(R.id.house_main_tab_bottom);
        mTab1 = (LinearLayout) findViewById(R.id.id_tab1_bottom);
        mTab2 = (LinearLayout) findViewById(R.id.id_tab2_bottom);
        mTab3 = (LinearLayout) findViewById(R.id.id_tab3_bottom);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置item选中监听，判断选中的item
        navigationView.setNavigationItemSelectedListener(this);
        headLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (up_userid==0)
//        {
//            getLoginActivity();
//            main_userid = userid;
//        }else {
//            main_userid = up_userid;
//        }
        OkGo.post(Net.showUserAllByIdIP)
                .params("user.id",userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obt = new JSONObject(s);
                            JSONObject obt1 = obt.getJSONObject("data");
                            main_headurl = obt1.getString("headurl");
                            //头像的显示
                            System.out.println(main_headurl);
                            headPic = (CircleImageView)headLayout.findViewById(R.id.headPic);
                            Picasso.with(MainActivity.this).load(main_headurl)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .error(R.drawable.default_avatar)
                                    .into(headPic);
                            //头像设置点击事件，进入UpdateUserAvtivity界面
                            headPic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent_update = new Intent(MainActivity.this,UpdateUserActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("main_userid",userid);
//                                    intent_update.putExtras(bundle);
//                                    startActivityForResult(intent_update,200);
                                    startActivity(intent_update);
                                    drawer.closeDrawer(GravityCompat.START);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                break;
//            case 2:
//                Bundle bundle = data.getExtras();
//                up_userid = bundle.getInt("up_userid");
//                System.out.println("UpdateUserActivity传回来的up_userid："+up_userid);
//                break;
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
//            DataSupport.deleteAll(UserLocal.class,"userid = ?",main_userid);
//            System.out.println("用户数据已删除");

            MainActivity.this.finish();
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_change) {
            Intent intent_change = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent_change);
            MainActivity.this.finish();
            Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getLoginActivity(){
//        Bundle bundle = getIntent().getExtras();
//        userid = bundle.getInt("user_id");
        userid = Integer.parseInt(Utils.getSharedString(mContext,Utils.STRING_USERID));
        boolean flag=false;
        //查询Litepal 中userlocal表中的数据，判断是否存在
        userLocalList = DataSupport.findAll(UserLocal.class);
        for (int i = 0;i<userLocalList.size();i++)
        {
            if (userid == userLocalList.get(i).getUserid())
            {
                flag = true;
                break;
            }
        }
        if (!flag)
        {
            //用户在LitePal中不存在，开始存入Litepal
            UserLocal userLocal = new UserLocal();
            userLocal.setUserid(userid);
            userLocal.save();
            if (userLocal.isSaved())
            {
                System.out.println("用户初次进入，存储数据成功");
            }
        }else {
            //用户存在，修改用户在数据
//            ContentValues values = new ContentValues();
//            values.put("userid", userid);
//            DataSupport.updateAll(UserLocal.class, values, "userid = ?", userid);
            System.out.println("用户存在");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tab1_bottom:
                setTabSelection(0);
                break;
            case R.id.id_tab2_bottom:
                setTabSelection(1);
                break;
            case R.id.id_tab3_bottom:
                setTabSelection(2);
                break;
        }
    }
    private void setTabSelection(int index) {
        resetBtn();
        transaction = fragmentManager.beginTransaction();
        //hideFragments(transaction);
        switch (index) {
            case 0:
                ((ImageButton) mTab1.findViewById(R.id.btn_tab1_bottom))
                        .setImageResource(R.drawable.icon_main_selected);
                ((TextView) mTab1.findViewById(R.id.text_tab1_bottom))
                        .setTextColor(getResources().getColor(R.color.select_color));
                if(mainFragment == null)
                {
                    mainFragment = new MainFragment();
                }
                if(!mainFragment.isAdded())
                {
                    transaction.add(R.id.fragment, mainFragment);
                    if(myselfFragment != null) {
                        transaction.hide(myselfFragment);
                    }
                    if(otherFragment != null){
                        transaction.hide(otherFragment);
                    }
                }else{
                    if(myselfFragment != null) {
                        transaction.hide(myselfFragment);
                    }
                    if(otherFragment != null){
                        transaction.hide(otherFragment);
                    }
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                ((ImageButton) mTab2.findViewById(R.id.btn_tab2_bottom))
                        .setImageResource(R.drawable.icon_browse_selected);
                ((TextView) mTab2.findViewById(R.id.text_tab2_bottom))
                        .setTextColor(getResources().getColor(R.color.select_color));
                if(myselfFragment == null)
                {
                    myselfFragment = new MyselfFragment();
                }
                if(!myselfFragment.isAdded())
                {
                    transaction.add(R.id.fragment, myselfFragment);
                    if(mainFragment != null) {
                        transaction.hide(mainFragment);
                    }
                    if(otherFragment != null){
                        transaction.hide(otherFragment);
                    }
                }else{
                    if(mainFragment != null) {
                        transaction.hide(mainFragment);
                    }
                    if(otherFragment != null){
                        transaction.hide(otherFragment);
                    }
                    transaction.show(myselfFragment);
                }
                break;
            case 2:
                ((ImageButton) mTab3.findViewById(R.id.btn_tab3_bottom))
                        .setImageResource(R.drawable.icon_my_selected);
                ((TextView) mTab3.findViewById(R.id.text_tab3_bottom))
                        .setTextColor(getResources().getColor(R.color.select_color));
                if(otherFragment == null)
                {
                    otherFragment = new OtherFragment();
                }
                if(!otherFragment.isAdded())
                {
                    transaction.add(R.id.fragment, otherFragment);
                    if(mainFragment != null) {
                        transaction.hide(mainFragment);
                    }
                    if(myselfFragment != null){
                        transaction.hide(myselfFragment);
                    }
                }else{
                    if(mainFragment != null) {
                        transaction.hide(mainFragment);
                    }
                    if(myselfFragment != null){
                        transaction.hide(myselfFragment);
                    }
                    transaction.show(otherFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void resetBtn() {
        ((ImageButton) mTab1.findViewById(R.id.btn_tab1_bottom))
                .setImageResource(R.drawable.icon_main_unselected);
        ((TextView) mTab1.findViewById(R.id.text_tab1_bottom))
                .setTextColor(getResources().getColor(R.color.un_select_color));
        ((ImageButton) mTab2.findViewById(R.id.btn_tab2_bottom))
                .setImageResource(R.drawable.icon_browse_unselected);
        ((TextView) mTab2.findViewById(R.id.text_tab2_bottom))
                .setTextColor(getResources().getColor(R.color.un_select_color));
        ((ImageButton) mTab3.findViewById(R.id.btn_tab3_bottom))
                .setImageResource(R.drawable.icon_my_unselected);
        ((TextView) mTab3.findViewById(R.id.text_tab3_bottom))
                .setTextColor(getResources().getColor(R.color.un_select_color));
    }
}
