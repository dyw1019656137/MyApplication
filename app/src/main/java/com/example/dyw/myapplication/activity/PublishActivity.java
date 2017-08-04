package com.example.dyw.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dyw.myapplication.MainActivity;
import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.net.Net;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.dyw.myapplication.R.id.publish_houseOwnerLocation;
import static com.example.dyw.myapplication.R.id.publish_submit;

public class PublishActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE1 = 1;
    private static int RESULT_LOAD_IMAGE2 = 2;
    private static int RESULT_LOAD_IMAGE3 = 3;
    public String picurl1,picurl2,picurl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        ImageView publish_HouseOwner_img1 = (ImageView) findViewById(R.id.publish_houseOwner_img1);     //房屋图片1
        ImageView publish_HouseOwner_img2 = (ImageView) findViewById(R.id.publish_houseOwner_img2);     //房屋图片2
        ImageView publish_HouseOwner_img3 = (ImageView) findViewById(R.id.publish_houseOwner_img3);     //房屋图片3
        Button publish_submit = (Button)findViewById(R.id.publish_submit);                              //发布提交
        publish_HouseOwner_img1.setOnClickListener(new MyPublishClick());
        publish_HouseOwner_img2.setOnClickListener(new MyPublishClick());
        publish_HouseOwner_img3.setOnClickListener(new MyPublishClick());
        publish_submit.setOnClickListener(new MyPublishClick());
    }
    class MyPublishClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.publish_houseOwner_img1:
                    Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, RESULT_LOAD_IMAGE1);
                    break;
                case R.id.publish_houseOwner_img2:
                    Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, RESULT_LOAD_IMAGE2);
                    break;
                case R.id.publish_houseOwner_img3:
                    Intent intent3 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent3, RESULT_LOAD_IMAGE3);
                    break;
                case publish_submit:
                    EditText publish_HouseOwnerName = (EditText)findViewById(R.id.publish_houseOwnerName);          //房东
                    EditText publish_HouseOwnerPhone = (EditText)findViewById(R.id.publish_houseOwnerPhone);        //联系方式
                    EditText publish_HouseOwnerTitle = (EditText)findViewById(R.id.publish_houseOwnerTitle);        //标题
                    EditText publish_HouseOwnerHow = (EditText)findViewById(R.id.publish_houseOwnerHow);            //出租方式(例：2室一厅（整租）)
                    EditText publish_HouseOwnerDevice = (EditText)findViewById(R.id.publish_houseOwnerDevice);      //房屋设备
                    EditText publish_HouseOwnerInfo = (EditText)findViewById(R.id.publish_houseOwnerInfo);          //房屋信息(例：普通住宅)
                    EditText publish_HouseOwnerLocation = (EditText)findViewById(R.id.publish_houseOwnerLocation);  //房屋地理位置(例：xx市xx小区)
                    EditText publish_HouseOwnerContent = (EditText)findViewById(R.id.publish_houseOwnerContent);    //租金
                    String key = UUID.randomUUID().toString();
                    System.out.println(new File(picurl1).exists());
                    System.out.println(picurl1);
                    OkGo.post(Net.saveHouseOwner)
                            .params("houseOwner.houseOwnerName",publish_HouseOwnerName.getText().toString())
                            .params("houseOwner.houseOwnerPhone",publish_HouseOwnerPhone.getText().toString())
                            .params("houseOwner.houseOwnerTitle",publish_HouseOwnerTitle.getText().toString())
                            .params("houseOwner.houseOwnerHow",publish_HouseOwnerHow.getText().toString())
                            .params("houseOwner.houseOwnerDevice",publish_HouseOwnerDevice.getText().toString())
                            .params("houseOwner.houseOwnerInfo",publish_HouseOwnerInfo.getText().toString())
                            .params("houseOwner.houseOwnerLocation",publish_HouseOwnerLocation.getText().toString())
                            .params("houseOwner.houseOwnerContent",publish_HouseOwnerContent.getText().toString()+"元/月")
                            .params("houseOwner.houseOwnerPicUrl1",Net.ip+"houseimage/houseOwnerPic1_"+key+".jpg")
                            .params("houseOwner.houseOwnerPicUrl2",Net.ip+"houseimage/houseOwnerPic2_"+key+".jpg")
                            .params("houseOwner.houseOwnerPicUrl3",Net.ip+"houseimage/houseOwnerPic3_"+key+".jpg")
                            .params("key",key)
                            .params("houseOwnerPicUrl1",new File(picurl1))
                            .params("houseOwnerPicUrl2",new File(picurl2))
                            .params("houseOwnerPicUrl3",new File(picurl3))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(PublishActivity.this,MainActivity.class);
//                                    startActivity(intent);
//                                    PublishActivity.this.finish();
                                }
                            });
                    break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn1 = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn1, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn1[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);
            picurl1 = picturePath;
            cursor.close();
            ImageView publish_HouseOwner_img1 = (ImageView) findViewById(R.id.publish_houseOwner_img1);
            publish_HouseOwner_img1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        if (requestCode == RESULT_LOAD_IMAGE2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn2 = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn2, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn2[0]);
            String picturePath = cursor.getString(columnIndex);
            picurl2 = picturePath;
            cursor.close();
            ImageView publish_HouseOwner_img2 = (ImageView) findViewById(R.id.publish_houseOwner_img2);
            publish_HouseOwner_img2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        if (requestCode == RESULT_LOAD_IMAGE3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn3 = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn3, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn3[0]);
            String picturePath = cursor.getString(columnIndex);
            picurl3 = picturePath;
            cursor.close();
            ImageView publish_HouseOwner_img3 = (ImageView) findViewById(R.id.publish_houseOwner_img3);
            publish_HouseOwner_img3.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
