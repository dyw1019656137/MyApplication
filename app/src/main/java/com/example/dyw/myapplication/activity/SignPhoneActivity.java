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
import com.example.dyw.myapplication.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import okhttp3.Call;
import okhttp3.Response;

public class SignPhoneActivity extends BaseActivity {
    Context context;
    EditText sign_phone;
    EditText sign_code;
    Button sign_phone_code;
    Button sign_phone_verify;
    private List<UserItem> userItemList;
    Timer timer = null;
    TimerTask mTimerTask = null;
    private int recLen = 0;//更新倒计时600秒 recLen = 601
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
                    OkGo.get(Net.showUserIP)
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
                                        if (phone.equals(userItemList.get(i).getPhone()))
                                        {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (flag){
                                        toast("手机已被注册，请联系管理员");
                                    }else {
                                        System.out.println("已发"+flag);
                                        BmobSMS.requestSMSCode(SignPhoneActivity.this, sign_phone.getText().toString(), "House", new RequestSMSCodeListener() {
                                            @Override
                                            public void done(Integer integer, BmobException e) {
                                                if (e==null)
                                                {
                                                    sign_phone_code.setEnabled(false);
                                                    startTimer();
                                                    toast("已发送"+integer);
                                                }else {
                                                    toast("发送失败"+integer);
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
//                                System.out.println("SignPhoneActivity:(sign_phone.getText().toString())"+sign_phone.getText().toString());
                                Utils.setSharedString(SignPhoneActivity.this,Utils.STRING_PHONE,sign_phone.getText().toString());
//                                intent_sign.putExtra("phone",sign_phone.getText().toString());
                                startActivity(intent_sign);
                                SignPhoneActivity.this.finish();
                            }else {
                                toast("验证码错误");
                                System.out.println("verifySmsCode:e.toString"+e.toString());
                            }
                        }
                    });
                    break;
            }
        }
    }
    private void startTimer(){
        if (timer == null){
            timer = new Timer();
        }
        if (mTimerTask == null){
            mTimerTask = new TimerTask() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recLen--;
                            sign_phone_code.setText("获取验证码"+"("+recLen+")");
                            if(recLen <= 0){
                                stopTimer();
                                sign_phone_code.setText("获取验证码");
                                sign_phone_code.setClickable(true);
                            }
                        }
                    });
                }
            };
        }
        if (timer != null && mTimerTask != null){
            timer.schedule(mTimerTask, 1000, 1000);
        }
    }

    private void stopTimer(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        if (mTimerTask != null){
            mTimerTask.cancel();
            mTimerTask = null;
        }
        recLen = 601;
    }
    public void toast(String string){
        Toast.makeText(SignPhoneActivity.this, string, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
