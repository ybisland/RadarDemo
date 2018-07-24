package com.lkites.radar;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

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

写代码的时候总是很佩服自己，我真TM是个人才，竟然能写出这样迷一般的代码

*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout constraintLayout;
    private ImageView im_car1, im_car2, im_car3, im_car4, im_car5;
    private WifiAdmin mWifiAdmin;
    private MyHandler myHandler;
    private ArrayList<ImageView> list_car;
    private ArrayList<Point> list_point;


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
        im_car2 = findViewById(R.id.im_car2);
        im_car3 = findViewById(R.id.im_car3);
        im_car4 = findViewById(R.id.im_car4);
        im_car5 = findViewById(R.id.im_car5);
        //im_car的存储数组
        list_car = new ArrayList<>(5);
        list_car.add(im_car1);
        list_car.add(im_car2);
        list_car.add(im_car3);
        list_car.add(im_car4);
        list_car.add(im_car5);
        //im_car的坐标存储数组,初始化坐标都是原点
        list_point = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            list_point.add(new Point((int) Point.ORIGIN_X, (int) Point.ORIGIN_Y));
        }

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
                Toast.makeText(MainActivity.this, "雷达已经连接成功！", Toast.LENGTH_LONG).show();
            } else {
                //环形进度条
                showProgressDialog(2900);
                //开启wifi连接并检测连接的wifi是否正确
                checkWifiConnection();
            }

        }  else if (id == R.id.nav_update) {

            Toast.makeText(MainActivity.this, "当前为最新版本", Toast.LENGTH_SHORT).show();

        } else {
            if (id == R.id.nav_about) {
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
                    String str_receive;
                    String str_distance, str_angle;
                    int int_distance, int_angle;

                    str_receive = (String) msg.obj;

                    //将接收的消息拆分成距离和角度的字符串,并以int类型放入list_point中
                    //下标偶数为距离，奇数为角度
                    String[] str = str_receive.split(",");
//                    Log.i("111", " " + str.length);

                    for (int i = 0; i < str.length - 1; i += 2) {
                        str_distance = str[i];
                        str_angle = str[i + 1];
                        try {//字符串容易出异常
                            int_distance = Integer.valueOf(str_distance);
                            int_angle = Integer.valueOf(str_angle);
                        } catch (Exception e) {
                            int_distance = -1;
                            int_angle = -1;
                        }

                        list_point.set(i / 2, new Point(int_distance, int_angle));
//                        Log.i("str[" + i + "]", " " + int_distance);
//                        Log.i("str[" + i + 1 + "]", "" + int_angle);
                    }

                    /*
                    for (ImageView i : list_car) {//我是谁？我在哪？我在干什么？这迷一样的代码是我写的？？
                        i.setY(list_point.get(list_car.indexOf(i)).getY());
                        i.setX(list_point.get(list_car.indexOf(i)).getX());
                    }
                    */
//                    Log.i("list_car",""+list_car.size());
                    for (int i = 0; i < list_car.size(); i++) {//反正都是更新im_car的坐标,哪种能看懂就看哪种吧(；´д｀)ゞ
                        list_car.get(i).setX(list_point.get(i).getX());
                        list_car.get(i).setY(list_point.get(i).getY());
//                        Log.i("car" + i + ".x", " " + list_point.get(i).getX());
//                        Log.i("car" + i + ".y", " " + list_point.get(i).getY());
                    }
            }
        }
    }
}