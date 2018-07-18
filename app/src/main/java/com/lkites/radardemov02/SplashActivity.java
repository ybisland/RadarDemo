package com.lkites.radardemov02;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //去掉标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        //设置进入主界面的延时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();//防止在主界面按返回按键回到该活动
            }
        }, 1000);

    }
}
