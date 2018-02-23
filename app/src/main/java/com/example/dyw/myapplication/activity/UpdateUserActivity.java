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
import android.widget.Toast;

import com.example.dyw.myapplication.R;
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

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class UpdateUserActivity extends BaseActivity {

    private int RESULT_LOAD_HEADIMAGE = 1;
    public String pic;      //图片在本地的路径
    public long id;     //litepal id
    private int uid = 0;     //user  id
    private String phonenum;        //user  phonenum
    private String headurl;         //user  headurl
    private int step;       //判断是否更新头像  2：更新
    private List<UserLocal> userLocalList;

    CircleImageView update_headImage;
    View update_headimg;
    Button update_subBtn;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        mContext = UpdateUserActivity.this;
        getMainActivity();
//        getData();
        update_headimg = findViewById(R.id.update_headimg);
        update_subBtn = (Button) findViewById(R.id.update_subBtn);
        update_headImage = (CircleImageView) findViewById(R.id.update_headImage);
    }

    private void getData() {
        OkGo.post(Net.showUserAllByIdIP)
                .params("user.id", uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obt = new JSONObject(s);
                            JSONObject obt1 = obt.getJSONObject("data");
                            headurl = obt1.getString("headurl");
                            phonenum = obt1.getString("phone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_headimg.setOnClickListener(new MyUpdateUserClickListener());
        update_subBtn.setOnClickListener(new MyUpdateUserClickListener());
    }

    private class MyUpdateUserClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText update_accountEt = (EditText) findViewById(R.id.update_accountEt);
            EditText update_pwdEt = (EditText) findViewById(R.id.update_pwdEt);
            EditText update_signSelf = (EditText) findViewById(R.id.update_signSelf);
            switch (v.getId()) {
                case R.id.update_headimg:
                    Intent intent_headImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent_headImage, RESULT_LOAD_HEADIMAGE);
                    step = 2;
                    break;
                case R.id.update_subBtn:
                    if (step == 2) {
                        uploadHeadImage();
                    }
                    OkGo.post(Net.updateUser)
                            .params("step", 1)
                            .params("user.name", update_accountEt.getText().toString())
                            .params("user.password", update_pwdEt.getText().toString())
                            .params("user.info", update_signSelf.getText().toString())
                            .params("user.id", uid)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Toast.makeText(UpdateUserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
            String[] filePathColumn1 = {MediaStore.Images.Media.DATA};
//            System.out.println("filePathColumn1[0]"+filePathColumn1[0]);
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn1, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn1[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);
            pic = picturePath;
            cursor.close();
            update_headImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void uploadHeadImage() {
        OkGo.post(Net.updateUserIP)
                .params("step", 2)
                .params("picKey", phonenum)
                .params("headpic", new File(pic))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println("SUCCESS UPDATE IMG");
                        try {
                            JSONObject obt = new JSONObject(s);
                            int code = obt.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println(e.getStackTrace());
                    }
                });
    }

    public void getMainActivity() {
//        Intent intent = getIntent();
//        Bundle bundle = this.getIntent().getExtras();
//        uid = bundle.getInt("main_userid");
        uid = Integer.parseInt(Utils.getSharedString(mContext,Utils.STRING_USERID));
        OkGo.post(Net.showUserAllByIdIP)
                .tag(this)
                .params("user.id", uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obt = new JSONObject(s);
                            JSONObject obt1 = obt.getJSONObject("data");
                            headurl = obt1.getString("headurl");
                            phonenum = obt1.getString("phone");
                            EditText update_phoneSelf = (EditText) findViewById(R.id.update_phoneSelf);
                            update_phoneSelf.setText(phonenum);
                            update_phoneSelf.setFocusable(false);
                            Picasso.with(UpdateUserActivity.this).load(headurl)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .error(R.drawable.default_avatar)
                                    .into(update_headImage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /*
    * 返回 uid 给MainActivity
    * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("up_userid", uid);
//        setResult(2, intent);
        finish();
    }
}
