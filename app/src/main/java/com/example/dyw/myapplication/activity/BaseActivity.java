package com.example.dyw.myapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.example.dyw.myapplication.utils.NetUtil;
import com.example.dyw.myapplication.utils.ToastUtils;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BaseActivity extends Activity {

    public boolean isNet = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#24a69e"));
        }

       if (!NetUtil.isNetworkAvalible(getBaseContext())){
            ToastUtils.show(getBaseContext(),"暂无网络，请检查网络!");
           isNet = false;
        }

    }
}
