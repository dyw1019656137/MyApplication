package com.example.dyw.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.AboutFragment;

/**
 * Created by dyw on 2017/7/6.
 */

public class OtherAboutActivity extends FragmentActivity{
    FragmentManager fragmentManager;
    AboutFragment aboutFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_about_activity);

        aboutFragment = new AboutFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.other_about_activity,aboutFragment);
        transaction.commit();
//        Log.d("----测试about----","界面success");
    }
}
