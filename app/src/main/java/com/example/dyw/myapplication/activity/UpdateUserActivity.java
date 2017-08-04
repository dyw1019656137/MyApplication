package com.example.dyw.myapplication.activity;

import android.content.ContentValues;
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

import com.example.dyw.myapplication.MainActivity;
import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.model.UserLocal;
import com.example.dyw.myapplication.net.Net;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.meg7.widget.CircleImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

public class UpdateUserActivity extends AppCompatActivity {

    private int RESULT_LOAD_HEADIMAGE = 1;
    public String pic;      //图片在本地的路径
    public long id;     //litepal id
    private String uid;     //user  id
    private String phonenum;        //user  phonenum
    private String username;        //user  username
    private String info;            //user  info
    private String password;        //user  password
    private String headurl;         //user  headurl
    private int step;       //判断是否更新头像  2：更新
    private String picKey;      //上传图片命名
    private List<UserLocal> userLocalList;

    CircleImageView update_headImage;
    View update_headimg;
    Button update_subBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        getMainActivity();

        EditText update_phoneSelf = (EditText)findViewById(R.id.update_phoneSelf);
        update_phoneSelf.setText(phonenum);
        update_phoneSelf.setFocusable(false);

        update_headimg = findViewById(R.id.update_headimg);
        update_subBtn = (Button)findViewById(R.id.update_subBtn);


        System.out.println("UpdateUserActivity:headurl:"+headurl);

    }

    @Override
    protected void onResume() {
        super.onResume();
        update_headImage = (CircleImageView)findViewById(R.id.update_headImage);
        Picasso.with(this).load(headurl)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(update_headImage);

        update_headimg.setOnClickListener(new MyUpdateUserClickListener());
        update_subBtn.setOnClickListener(new MyUpdateUserClickListener());


    }

    private class MyUpdateUserClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText update_accountEt = (EditText)findViewById(R.id.update_accountEt);
            EditText update_pwdEt = (EditText)findViewById(R.id.update_pwdEt);
            EditText update_signSelf = (EditText)findViewById(R.id.update_signSelf);

            switch (v.getId())
            {
                case R.id.update_headimg:
                    Intent intent_headImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent_headImage, RESULT_LOAD_HEADIMAGE);
                    step = 2 ;
                    break;
                case R.id.update_subBtn:
                    if (step ==2 )
                    {
                        uploadHeadImage();
                    }
                    OkGo.post(Net.updateUser)
                            .params("step",1)
                            .params("usernameKey",update_accountEt.getText().toString())
                            .params("passwordKey",update_pwdEt.getText().toString())
                            .params("infoKey",update_signSelf.getText().toString())
                            .params("idKey",uid)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Toast.makeText(UpdateUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                    updateUser();
//                                    UpdateUserActivity.this.finish();
                                }
                            });
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
            CircleImageView headImage = (CircleImageView)findViewById(R.id.update_headImage);
            headImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    public void uploadHeadImage(){
        picKey = UUID.randomUUID().toString();
        OkGo.post(Net.updateUser)
                .params("step",2)
                .params("user.headurl",Net.ip+"houseimage/headurl_"+phonenum+".jpg")
                .params("picKey",phonenum)
                .params("headpic",new File(pic))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("SUCCESS UPDATE IMG");
                    }
                });
    }

    public void updateUser(){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("userinfo",info);
        DataSupport.updateAll(UserLocal.class,values,"userid = ?",uid);

        System.out.println("updateUserActivity litepal 修改成功");
    }

    /*
    * 获取MainActivity 传过来的main_userid,并根据main_userid查找Litepal中的数据
    * 1、用户ID  uid  = main_userid
    * 2、用户名 username
    * 3、密码 password
    * 4、用户信息 info
    * 5、用户电话 phonenum
    * 6、用户头像接口 headurl
    * */
    public void getMainActivity(){
        Intent intent = getIntent();
        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getString("main_userid");
        userLocalList = DataSupport.findAll(UserLocal.class);
        for (int i = 0;i<userLocalList.size();i++)
        {
            if (uid.equals(userLocalList.get(i).getUserid()))
            {
                phonenum = userLocalList.get(i).getPhonenum();
                headurl = userLocalList.get(i).getHeadurl();
                break;
            }
        }

    }

    /*
    * 返回 uid 给MainActivity
    * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("up_userid", uid);
        System.out.println("传入的值："+uid);
        setResult(2,intent);
        finish();
    }
}
