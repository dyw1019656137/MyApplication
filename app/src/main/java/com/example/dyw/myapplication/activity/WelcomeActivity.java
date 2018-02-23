package com.example.dyw.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.dyw.myapplication.R;

/**
 * Created by dyw on 2017/7/11.
 */

public class WelcomeActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_avtivity);
        ImageView welcome_img = (ImageView)findViewById(R.id.welcome_img);
        welcome_img.setImageDrawable(getResources().getDrawable(R.drawable.welcome));

        Handler handler = new Handler();
        handler.postDelayed(welcome,2000);
    }
    private Runnable welcome = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }
    };
}
