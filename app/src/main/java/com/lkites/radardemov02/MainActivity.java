package com.lkites.radardemov02;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/*
┌───┐   ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
│Esc│   │F1 │F2 │F3 │F4 │ │F5 │F6 │F7 │F8 │ │F9 │F10│F11│F12│ │P/S│S L│P/B│  ┌┐    ┌┐    ┌┐
└───┘   └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘  └┘    └┘    └┘
┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐ ┌───┬───┬───┐ ┌───┬───┬───┬───┐
│~ `│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp │ │Ins│Hom│PUp│ │N L│ / │ * │ - │
├───┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤ ├───┼───┼───┤ ├───┼───┼───┼───┤
│ Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ │ │Del│End│PDn│ │ 7 │ 8 │ 9 │   │
├─────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤ └───┴───┴───┘ ├───┼───┼───┤ + │
│ Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │               │ 4 │ 5 │ 6 │   │
├──────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤     ┌───┐     ├───┼───┼───┼───┤
│ Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │     │ ↑ │     │ 1 │ 2 │ 3 │   │
├─────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤ ┌───┼───┼───┐ ├───┴───┼───┤ E││
│ Ctrl│ Fn │Alt │         Space         │ Alt│ Fn │ Pn │Ctrl│ │ ← │ ↓ │ → │ │   0   │ . │←─┘│
└─────┴────┴────┴───────────────────────┴────┴────┴────┴────┘ └───┴───┴───┘ └───────┴───┴───┘

   Modification date:18-5-1
   Describe:完成雷达车辆显示的功能，还差WiFi没有实现

   先完成一个车辆的v1版本，之后再完成多个车辆的版本，那时将car_image改成new的形式来实现

*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout constraintLayout;

    private ImageView im_car1;
    private String str_receive, str_distance, str_angle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
//todo 检查WiFi是否开启以及是否连接到esp8266的WiFi

        //开启服务器
        MobileServer mobileServer = new MobileServer();
        mobileServer.setHandler(handler);
        new Thread(mobileServer).start();


    }//onCreate结束


    private void InitView() {

        constraintLayout = findViewById(R.id.constraintlayout);
        im_car1 = findViewById(R.id.im_car1);

        //设置侧滑导航的点击事件
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置FAB的点击事件
        FloatingActionButton fab = findViewById(R.id.fab_nav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

    }


    //本类实现onNavigationItemSelectedListener的接口
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_main) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4);
        } else if (id == R.id.nav_connect) {
//todo 检查WiFi是否开启以及是否连接到esp8266的WiFi
//
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                }
            }, 1000);

        } else if (id == R.id.nav_question) {
//todo 常见问题 使用说明

        } else if (id == R.id.nav_update) {
            Toast.makeText(MainActivity.this, "当前为最新版本", Toast.LENGTH_SHORT).show();
        } else {
            if (id == R.id.nav_about) {
//todo 关于
            Intent i = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);

            }
        }

        //点击item之后关闭drawer，
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //按下back键关闭drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {  //接收消息
            switch (msg.what) {
                case 1:
                    str_receive = (String) msg.obj;

                    //将接收的消息拆分成距离和角度的字符串
                    String[] str = str_receive.split(",");
                    str_distance = str[0];
                    str_angle = str[1];

                    //将距离和角度的字符串转换成int类型
                    int int_distance = Integer.valueOf(str_distance);
                    int int_angle = Integer.valueOf(str_angle);
                    Point p = new Point(int_distance,int_angle);
//todo 封装成一个函数
//todo 屏幕适配
                    im_car1.setX(p.x);
                    im_car1.setY(p.y);
                    System.out.println(p.x);
                    System.out.println(p.y);

            }
        }//这里会存在内存泄露的风险，所以代码块变黄了
    };
}
