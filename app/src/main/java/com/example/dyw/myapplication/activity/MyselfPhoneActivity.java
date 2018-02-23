package com.example.dyw.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.PhoneFragment;

/**
 * Created by dyw on 2017/7/6.
 */

public class MyselfPhoneActivity extends FragmentActivity{
    FragmentManager fragmentManager;
    PhoneFragment phoneFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself_phone_activity);

        phoneFragment = new PhoneFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.myself_phone_activity,phoneFragment);
        transaction.commit();
//        Log.d("----测试phone界面----","界面success");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
