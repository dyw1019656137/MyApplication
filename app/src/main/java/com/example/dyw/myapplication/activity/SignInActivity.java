package com.example.dyw.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.User;
import com.example.dyw.myapplication.model.UserItem;
import com.example.dyw.myapplication.net.Net;
import com.example.dyw.myapplication.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {
    private static int RESULT_LOAD_HEADIMAGE = 1;
    public String pic;
    private String phonenum;
    private String username;
    private List<UserItem> userItemList;
    private List userList=new ArrayList();
    boolean flag = false;
    EditText sign_phoneSelf;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mContext = SignInActivity.this;

//        Intent intent = getIntent();
//        phonenum = intent.getStringExtra("phone");
        phonenum = Utils.getSharedString(mContext,Utils.STRING_PHONE);
//        System.out.println("SignInActivity(phone):"+phonenum);
        Button sign_subBtn = (Button)findViewById(R.id.sign_subBtn);
        View sign_headimg = (View)findViewById(R.id.sign_headimg);
        sign_phoneSelf = (EditText)findViewById(R.id.sign_phoneSelf);
        sign_phoneSelf.setText(phonenum);
        sign_phoneSelf.setFocusable(false);
        sign_subBtn.setOnClickListener(new MySignInClickListener());
        sign_headimg.setOnClickListener(new MySignInClickListener());
    }
    private class MySignInClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText sign_accountEt = (EditText)findViewById(R.id.sign_accountEt);
            EditText sign_pwdEt = (EditText)findViewById(R.id.sign_pwdEt);
            EditText sign_signSelf = (EditText)findViewById(R.id.sign_signSelf);

            username = sign_accountEt.getText().toString();

            switch (v.getId())
            {
                case R.id.sign_headimg:
                    Intent intent_headImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent_headImage, RESULT_LOAD_HEADIMAGE);
                    break;

                case R.id.sign_subBtn:

                    OkGo.get(Net.showUserIP)
                            .tag(this)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    User usermain = gson.fromJson(s,User.class);
                                    userItemList = usermain.getData();
                                    for (int i =0;i<userItemList.size();i++)
                                    {
                                        if (username.equals(userItemList.get(i).getName()))
                                        {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                            });
                    if (flag)
                    {
                        Toast.makeText(SignInActivity.this,"用户名重复，请重新命名",Toast.LENGTH_SHORT).show();
                    }else {
                        String picKey = UUID.randomUUID().toString();
                        OkGo.post(Net.saveUserIP)
                                .params("user.name",username)
                                .params("user.password",sign_pwdEt.getText().toString())
                                .params("user.phone",phonenum)
                                .params("user.info",sign_signSelf.getText().toString())
                                .params("user.headurl",Net.ip+"houseimage/headurl_"+phonenum+".jpg")
                                .params("picKey",phonenum)
                                .params("headpic",new File(pic))
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        Toast.makeText(SignInActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                        toMainActivity();
                                    }
                                });
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_HEADIMAGE) {
            Uri selectedImage = data.getData();
            String[] filePathColumn1 = { MediaStore.Images.Media.DATA };
//            System.out.println("filePathColumn1[0]"+filePathColumn1[0]);
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn1, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn1[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);
            pic = picturePath;
            cursor.close();
            ImageView headImage = (ImageView)findViewById(R.id.headImage);
            headImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("SignInActivity onDestroy");
    }
    public void toMainActivity(){
        OkGo.get(Net.showUserIP)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        User usermain = gson.fromJson(s,User.class);
                        userItemList = usermain.getData();
                        for (int i =0;i<userItemList.size();i++)
                        {
                            if (userItemList.get(i).getPhone().equals(phonenum))
                            {
                                userList.clear();
                                userList.add(userItemList.get(i).getUser_id());
                                System.out.println("userList.size()::"+userList.size());
                                break;
                            }
                        }
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("user_id", (Integer) userList.get(0));
                        Utils.setSharedString(mContext,Utils.STRING_USERID, String.valueOf(userList.get(0)));
//                        intent.putExtras(bundle);
                        startActivity(intent);
                        SignInActivity.this.finish();
                    }
                });


    }
}
