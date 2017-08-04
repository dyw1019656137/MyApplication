package com.example.dyw.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.net.Net;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dyw on 2017/7/6.
 */

public class OtherFeedbackActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_feedback_fragment);

        final EditText other_feedback_advice = (EditText)findViewById(R.id.other_feedback_advice);
        final EditText other_feedback_phonenum = (EditText)findViewById(R.id.other_feedback_phone);
        final EditText other_feedback_address = (EditText)findViewById(R.id.other_feedback_address);
        Button other_feedback_submit = (Button)findViewById(R.id.other_feedback_submit);
        other_feedback_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post(Net.saveFeedbackIP)
                        .params("feedback.advice",other_feedback_advice.getText().toString())
                        .params("feedback.phonenum",other_feedback_phonenum.getText().toString())
                        .params("feedback.address",other_feedback_address.getText().toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(OtherFeedbackActivity.this,"意见已发送",Toast.LENGTH_SHORT).show();
                                OtherFeedbackActivity.this.finish();
                            }
                        });
            }
        });
    }
}
