package com.lkites.radardemov02;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

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
*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout constraintLayout;
    private ImageView im_car1;
    private WifiAdmin mWifiAdmin;
    private String str_receive, str_distance, str_angle;
    private MyHandler myHandler;
    private List<ImageView> list_car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitView();
        checkWifiConnection();


        //开启服务器
        MobileServer mobileServer = new MobileServer();
        mobileServer.setHandler(myHandler);
        new Thread(mobileServer).start();


    }//onCreate结束


    private void InitView() {

        constraintLayout = findViewById(R.id.constraintlayout);
        im_car1 = findViewById(R.id.im_car1);

        mWifiAdmin = new WifiAdmin(this);

        myHandler = new MyHandler();

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

        } else if (id == R.id.nav_connect) {     //显示环形进度条并检查wifi连接

            if ((mWifiAdmin.checkState() == 3) && (mWifiAdmin.getSSID().equals("\"ATK_ESP8266\""))) {
                //如果已经连接了esp8266，就不再显示进度条和执行连接的操作
                Toast.makeText(MainActivity.this, "雷达已经连接成功！", Toast.LENGTH_SHORT).show();
            } else {
                //环形进度条
                showProgressDialog(2900);
                //开启wifi连接并检测连接的wifi是否正确
                checkWifiConnection();
            }

        } else if (id == R.id.nav_question) {
//todo 常见问题 使用说明

        } else if (id == R.id.nav_update) {

            Toast.makeText(MainActivity.this, "当前为最新版本", Toast.LENGTH_SHORT).show();

        } else {

            if (id == R.id.nav_about) {
//todo 关于
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
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

    /**
     * 检查wifi是否开启并是否连接esp8266
     */
    private void checkWifiConnection() {
        //开启wifi功能
        mWifiAdmin.openWifi();

        //wifi开启后自动连接至esp8266需要时间
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWifiAdmin.getSSID().equals("\"ATK_ESP8266\"")) { //getSSID()返回的字符串包含了“”
                    Toast.makeText(MainActivity.this, "雷达自动连接成功！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(MainActivity.this, "自动连接失败，请手动连接WiFi: ATK_ESP8266", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }, 2500);
    }

    /**
     * 显示环形进度条
     */
    private void showProgressDialog(final int time) {
        final ProgressDialog proDia = new ProgressDialog(MainActivity.this);
        proDia.setTitle("连接雷达");
        proDia.setMessage("雷达连接中，请耐心等待……");
        proDia.onStart();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {

                } finally {         //匿名内部类要访问类中的数据，该数据必须为final
                    proDia.dismiss();       //隐藏对话框
                }
            }
        }.start();
        proDia.show();
    }


    private class MyHandler extends Handler {
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
                    Point p = new Point(int_distance, int_angle);
//todo 用list来放多个car
//todo 屏幕适配
                    

                    im_car1.setX(p.x);
                    im_car1.setY(p.y);
                    Log.i("car1.x:", new Float(p.x).toString());
                    Log.i("car1.y:", new Float(p.y).toString());

            }
        }
    }

}
