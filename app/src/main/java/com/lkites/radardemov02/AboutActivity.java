package com.lkites.radardemov02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 22122 on 18-4-15.
 */

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //去掉标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}
