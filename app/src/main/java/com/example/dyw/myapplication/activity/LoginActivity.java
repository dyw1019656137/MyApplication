package com.example.dyw.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.MainActivity;
import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.User;
import com.example.dyw.myapplication.model.UserItem;
import com.example.dyw.myapplication.net.Net;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public String password;     //密码存入Litepal
    public String info;         //用户个人信息存入LitePal
    public String phonenum;     //用户手机号存入LitePal
    private List<UserItem> userItemList;
    private List userList=new ArrayList();      //页面跳转，Bundle传值
    EditText accountEt;     //用户账号输入（手机号）
    EditText pwdEt;     //用户密码输入
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEt = (EditText)findViewById(R.id.accountEt);
        pwdEt = (EditText)findViewById(R.id.pwdEt);

        Button subBtn = (Button)findViewById(R.id.subBtn);
        TextView sign = (TextView)findViewById(R.id.sign);
        subBtn.setOnClickListener(new MyLoginClickListener());
        sign.setOnClickListener(new MyLoginClickListener());
    }

    private class MyLoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            accountEt = (EditText)findViewById(R.id.accountEt);
            pwdEt = (EditText)findViewById(R.id.pwdEt);
            switch (v.getId())
            {
                case R.id.subBtn:
                    putToMainActivity();
                    break;
                case R.id.sign:
                    Intent intent_sign = new Intent(LoginActivity.this,SignPhoneActivity.class);
                    startActivity(intent_sign);
                    LoginActivity.this.finish();
                    break;
            }
        }
    }

    /*
    * 登录    用户信息存入userList  并通过 bundle 传入 MainAvtivity
    * 1、用户ID(userid)
    * 2、用户名（username）
    * 3、密码（password）
    * 4、个人信息(userinfo)
    * 5、用户手机号（phonenum）
    * 6、用户头像接口（headurl）
    * 7、boolean flag判断是否初次登录    false 登录失败  true  成功登录
    * */
    public void putToMainActivity(){
        OkGo.get(Net.showUser)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        User usermain = gson.fromJson(s,User.class);
                        userItemList = usermain.getData();

                        phonenum = accountEt.getText().toString();
                        password = pwdEt.getText().toString();

                        boolean flag = false;
                        for (int i =0;i<userItemList.size();i++)
                        {
                            //手机号和密码匹配
                            if (phonenum.equals(userItemList.get(i).getPhonenum())&&password.equals(userItemList.get(i).getPassword()))
                            {
                                userList.clear();
                                userList.add(userItemList.get(i).getId());          //user ID
                                userList.add(userItemList.get(i).getUsername());    //user  username
                                userList.add(userItemList.get(i).getPassword());    //user password
                                userList.add(userItemList.get(i).getInfo());        //user info
                                userList.add(userItemList.get(i).getHeadurl());     //user headurl
                                userList.add(userItemList.get(i).getPhonenum());    //user phonenum
                                flag = true;
                                break;
                            }
                        }
                        if (flag)
                        {
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //bundle 将userList里的值传入 MainActivity
                            Intent intent_login = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userid", (String)userList.get(0));
                            bundle.putString("username", (String) userList.get(1));
                            bundle.putString("password",(String)userList.get(2));
                            bundle.putString("userinfo",(String)userList.get(3));
                            bundle.putString("headurl",(String)userList.get(4));
                            bundle.putString("phonenum",(String)userList.get(5));
                            intent_login.putExtras(bundle);
                            startActivity(intent_login);

                            //销毁LoginActivity
                            LoginActivity.this.finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"手机号码或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
