package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import okhttp3.Call;
import okhttp3.Response;

public class ForgetPasswordActivity extends BaseActivity {
    Context context;
    EditText forget_phone;
    EditText forget_code;
    EditText forget_password;
    Button qrCode;
    Button update_forget;
    private List<UserItem> userItemList;
    Timer timer = null;
    TimerTask mTimerTask = null;
    private int recLen = 0;//更新倒计时600秒 recLen = 601
    private String key = "729dfe2bfa07b02322ebb039ad9c0e8e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        context = getApplicationContext();
        BmobSMS.initialize(ForgetPasswordActivity.this,key);
        initViews();
    }

    private void initViews() {
        forget_phone = (EditText)findViewById(R.id.forget_phone);
        forget_code = (EditText)findViewById(R.id.forget_code);
        forget_password = (EditText)findViewById(R.id.forget_password);
        qrCode = (Button)findViewById(R.id.qrCode);
        update_forget = (Button)findViewById(R.id.update_forget);
        qrCode.setOnClickListener(new ForgetPasswordListener());
        update_forget.setOnClickListener(new ForgetPasswordListener());
    }

    private class ForgetPasswordListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.qrCode:
                    getCode();
                    break;
                case R.id.update_forget:
                    updatePassword();
                    break;
            }
        }
    }

    private void getCode() {
        OkGo.get(Net.showUserIP)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        boolean flag = false;
                        Gson gson = new Gson();
                        User usermain = gson.fromJson(s,User.class);
                        userItemList = usermain.getData();
                        String phone = forget_phone.getText().toString();
                        for (int i =0;i<userItemList.size();i++)
                        {
                            if (phone.equals(userItemList.get(i).getPhone()))
                            {
                                flag = true;
                                break;
                            }
                        }
                        if (flag){
                            System.out.println("已发"+flag);
                            BmobSMS.requestSMSCode(ForgetPasswordActivity.this, forget_phone.getText().toString(), "House", new RequestSMSCodeListener() {
                                @Override
                                public void done(Integer integer, BmobException e) {
                                    if (e==null)
                                    {
                                        qrCode.setEnabled(false);
                                        startTimer();
                                        toast("已发送"+integer);
                                    }else {
                                        toast("发送失败"+integer);
                                        System.out.println("requestSMSCode:e.toString"+e.toString());
                                    }
                                }
                            });
                        }else {
                            toast("手机号不存在");
                        }
                    }
                });
    }

    private void updatePassword(){
        BmobSMS.verifySmsCode(ForgetPasswordActivity.this, forget_phone.getText().toString(), forget_code.getText().toString(), new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e==null)
                {
                    toast("验证通过");
                    if (forget_password.getText().toString() == null){
                        toast("密码不能为空");
                    }else {
                        OkGo.post(Net.forgetUserIP)
                                .params("user.phone",forget_phone.getText().toString())
                                .params("user.password",forget_password.getText().toString())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        int code = 0;
                                        try {
                                            JSONObject object = new JSONObject(s);
                                            code = object.getInt("code");
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                        if (code == 400){
                                            toast("修改失败");
                                        }else {
                                            Intent intent_login = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent_login);
                                            ForgetPasswordActivity.this.finish();
                                        }
                                    }
                                });
                    }
                }else {
                    toast("验证码错误");
                    System.out.println("verifySmsCode:e.toString"+e.toString());
                }
            }
        });
    }

    public void toast(String string){
        Toast.makeText(ForgetPasswordActivity.this, string, Toast.LENGTH_LONG).show();
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
                            qrCode.setText(recLen+"秒后重发");
                            if(recLen <= 0){
                                stopTimer();
                                qrCode.setText("发送验证码");
                                qrCode.setClickable(true);
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
}
