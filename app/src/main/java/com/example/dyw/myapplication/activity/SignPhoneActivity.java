package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.User;
import com.example.dyw.myapplication.model.UserItem;
import com.example.dyw.myapplication.net.Net;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import okhttp3.Call;
import okhttp3.Response;

public class SignPhoneActivity extends AppCompatActivity {
    Context context;
    EditText sign_phone;
    EditText sign_code;
    Button sign_phone_code;
    Button sign_phone_verify;
    private List<UserItem> userItemList;
    private String key = "729dfe2bfa07b02322ebb039ad9c0e8e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_phone);

        context = getApplicationContext();
        BmobSMS.initialize(SignPhoneActivity.this,key);
        initViews();
    }

    private void initViews() {
        sign_phone = (EditText)findViewById(R.id.sign_phone);
        sign_code = (EditText)findViewById(R.id.sign_code);
        sign_phone_code = (Button)findViewById(R.id.sign_phone_code);
        sign_phone_verify = (Button)findViewById(R.id.sign_phone_verify);
        sign_phone_code.setOnClickListener(new MySignPhoneClickListener());
        sign_phone_verify.setOnClickListener(new MySignPhoneClickListener());
    }

    private class MySignPhoneClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.sign_phone_code:

                    OkGo.get(Net.showUser)
                            .tag(this)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    boolean flag = false;
                                    Gson gson = new Gson();
                                    User usermain = gson.fromJson(s,User.class);
                                    userItemList = usermain.getData();
                                    String phone = sign_phone.getText().toString();
                                    for (int i =0;i<userItemList.size();i++)
                                    {
                                        if (phone.equals(userItemList.get(i).getPhonenum()))
                                        {
//                                            System.out.println("SignPhoneActivity(phone):"+phone);
//                                            System.out.println("SignPhoneActivity(sign_phone_code):"+userItemList.get(i).getPhonenum());
//                                            System.out.println(phone.equals(userItemList.get(i).getPhonenum()));
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (flag){
                                        System.out.println("未发"+flag);
                                        toast("手机已被注册，请联系管理员");
                                    }else {
                                        System.out.println("已发"+flag);
                                        sign_phone_code.setEnabled(false);
                                        BmobSMS.requestSMSCode(SignPhoneActivity.this, sign_phone.getText().toString(), "House", new RequestSMSCodeListener() {
                                            @Override
                                            public void done(Integer integer, BmobException e) {
                                                if (e==null)
                                                {
                                                    toast("已发送"+integer);
                                                }else {
//                                                    toast(e.toString());
                                                    System.out.println("requestSMSCode:e.toString"+e.toString());
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                    break;
                case R.id.sign_phone_verify:
                    BmobSMS.verifySmsCode(SignPhoneActivity.this, sign_phone.getText().toString(), sign_code.getText().toString(), new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null)
                            {
                                toast("验证通过");
                                Intent intent_sign = new Intent(SignPhoneActivity.this,SignInActivity.class);
                                System.out.println("SignPhoneActivity:(sign_phone.getText().toString())"+sign_phone.getText().toString());
                                intent_sign.putExtra("phonenum",sign_phone.getText().toString());

                                startActivity(intent_sign);
                                SignPhoneActivity.this.finish();
                            }else {
//                                toast(e.toString());
                                System.out.println("verifySmsCode:e.toString"+e.toString());
                            }
                        }
                    });
                    break;
            }
        }
    }
    public void toast(String string){
        Toast.makeText(SignPhoneActivity.this, string, Toast.LENGTH_LONG).show();
    }
}
