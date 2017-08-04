package com.example.dyw.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.dyw.myapplication.MainActivity;
import com.example.dyw.myapplication.R;
import com.example.dyw.myapplication.fragment.BrowseFragment;
import com.example.dyw.myapplication.fragment.MyselfFragment;
import com.example.dyw.myapplication.model.RecentHouseOwner;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dyw on 2017/7/6.
 */

public class MyselfBrowseActivity extends FragmentActivity{
    FragmentManager fragmentManager;
    BrowseFragment browseFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself_browse_activity);

        browseFragment = new BrowseFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.myself_browse_activity,browseFragment);
        transaction.commit();
//        Log.d("----测试Browse界面----","界面success");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
