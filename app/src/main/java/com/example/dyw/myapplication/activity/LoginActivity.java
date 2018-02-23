package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Response;
public class LoginActivity extends BaseActivity {
    public String password;     //密码存入Litepal
    public String phone;     //用户手机号存入LitePal
    EditText accountEt;     //用户账号输入（手机号）
    EditText pwdEt;     //用户密码输入
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        accountEt = (EditText)findViewById(R.id.accountEt);
        pwdEt = (EditText)findViewById(R.id.pwdEt);
        accountEt.setText("13776552544");
        pwdEt.setText("123");
        Button subBtn = (Button)findViewById(R.id.subBtn);
        TextView sign = (TextView)findViewById(R.id.sign);
        TextView forget_password = (TextView)findViewById(R.id.forget_password);
        subBtn.setOnClickListener(new MyLoginClickListener());
        sign.setOnClickListener(new MyLoginClickListener());
        forget_password.setOnClickListener(new MyLoginClickListener());
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
                case R.id.forget_password:
                    Intent intent_forget = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                    startActivity(intent_forget);
                    LoginActivity.this.finish();
                    break;
            }
        }
    }

    /*
    * 登录    用户信息存入userList  并通过 bundle 传入 MainAvtivity
    * 1、用户ID(user_id)
    * */
    public void putToMainActivity(){
        phone = accountEt.getText().toString();
        password = pwdEt.getText().toString();
        OkGo.get(Net.loginUserIP)
                .tag(this)
                .params("user.phone",phone)
                .params("user.password",password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        int code = 0;
                        int user_id = 0;
                        try {
                            JSONObject obj = new JSONObject(s);
                            code = obj.getInt("code");
                            user_id = obj.getInt("user_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("code:"+code);
                        if (code == 400){
                            Toast.makeText(LoginActivity.this,"手机号码或密码错误",Toast.LENGTH_SHORT).show();
                        }else {
                            Utils.setSharedString(mContext,Utils.STRING_USERID, String.valueOf(user_id));
                            Utils.setSharedString(mContext,Utils.STRING_PHONE,phone);
//                            Utils.setSharedString(mContext,Utils.STRING_PASSWORD,password);
                            Intent intent_login = new Intent(LoginActivity.this, MainActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("user_id",user_id);
//                            intent_login.putExtras(bundle);
                            startActivity(intent_login);
                            LoginActivity.this.finish();
                        }
                    }
                });
    }
}
