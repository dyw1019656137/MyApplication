package com.example.dyw.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.CollectFragment;

/**
 * Created by dyw on 2017/7/6.
 */

public class MyselfCollectActivity extends FragmentActivity {
    FragmentManager fragmentManager;
    CollectFragment collectFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself_activity);

        collectFragment = new CollectFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.myself_fragment,collectFragment);
        transaction.commit();
//        Log.d("---测试collect界面---","界面出现");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.myself_activity);

        collectFragment = new CollectFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.myself_fragment,collectFragment);
        transaction.commit();
    }
}
