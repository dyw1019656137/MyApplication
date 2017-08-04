package com.example.dyw.myapplication;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.logging.Level;

/**
 * Created by dyw on 2017/7/4.
 */

public class MyApplication extends LitePalApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        //初始化OkGo
        OkGo.init(this);
        //picasso
        try {
            OkGo.getInstance()
                    .debug("OkGo", Level.INFO, true)
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setRetryCount(3);
        } catch (Exception e)
        {

        }
    }
}
