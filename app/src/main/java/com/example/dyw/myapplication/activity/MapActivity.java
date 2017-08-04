package com.example.dyw.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.MapFragment;

/**
 * Created by dyw on 2017/7/7.
 */

public class MapActivity extends FragmentActivity{
    FragmentManager fragmentManager;
    MapFragment mapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mapFragment = new MapFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.map_activity,mapFragment);
        transaction.commit();
//        Log.d("----测试mapactivity----","界面success");
    }
}
