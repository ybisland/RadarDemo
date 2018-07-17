package com.lkites.radardemov02;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
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

import java.util.Timer;
import java.util.TimerTask;

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
   Describe:

*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ConstraintLayout constraintLayout;
    ImageView im_carf;
    ImageView im_carl;
    ImageView im_carll1;
    ImageView im_carll2;
    ImageView im_carr;
    Timer timer;
    TimerTask timerTask;
    float x, y;
    float x2, y2;
    float x3, y3;
    float x4, y4;

    float x5, y5;
    int flagf, flagl, flagll1, flagll2, flagr;

    //runOnUiThread的参数
    Runnable r = new Runnable() {
        @Override
        public void run() {//在这里只写更新UI的代码
            //如果x或y没有值，就不改变x或y
            if (y != 0) {
                im_carf.setY(y);
            }
            if (x != 0) {
                im_carf.setX(x);
            }

            //car2
            if (y2 != 0) {
                im_carl.setY(y2);
            }
            if (x2 != 0) {
                im_carl.setX(x2);
            }


            if (y3 != 0) {
                im_carll1.setY(y3);
            }
            if (x3 != 0) {
                im_carll1.setX(x3);
            }
            if (y3 < 10) {
                im_carll1.setVisibility(View.INVISIBLE);
            }

            if (y4 != 0) {
                im_carll2.setY(y4);
            }
            if (x4 != 0) {
                im_carll2.setX(x4);
            }
            if (y4 < 10) {
                im_carll2.setVisibility(View.INVISIBLE);
            }

            if (y5 != 0) {
                im_carr.setY(y5);
            }
            if (x5 != 0) {
                im_carr.setX(x5);
            }
            if (y5 < 10) {
                im_carr.setVisibility(View.INVISIBLE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im_carf = findViewById(R.id.im_carf);
        im_carl = findViewById(R.id.im_carl);
        im_carll1 = findViewById(R.id.im_carll1);
        im_carll2 = findViewById(R.id.im_carll2);
        im_carr = findViewById(R.id.im_carr);

        constraintLayout = findViewById(R.id.constraintlayout);

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


        flagf = flagl = flagll1 = flagll2 = flagr = 0;

        //Timer定期更新UI
        timer = new Timer();
        //创建TimerTask对象
        timerTask = new TimerTask() {
            @Override
            public void run() {//设置xy的移动方式

                System.out.println("右" + im_carf.getY());
                System.out.println("左" + im_carl.getY());
                System.out.println("flagf" + flagf);
                System.out.println("flagl" + flagl);

                switch (flagf) {
                    case 0:
                        y = im_carf.getY() - 0.35f;
                        break;
                    case 1:
                        y = im_carf.getY() + 0.35f;
                        break;
                }
                if (im_carf.getY() < 120) {
                    flagf = 1;
                } else if (im_carf.getY() > 280) {
                    flagf = 0;
                }

                switch (flagl) {
                    case 0:
                        y2 = im_carl.getY() - 0.5f;
                        break;
                    case 1:
                        y2 = im_carl.getY() + 0.5f;
                        break;
                }
                if (im_carl.getY() < 110) {
                    flagl = 1;
                } else if (im_carl.getY() > 280) {
                    flagl = 0;
                }


                y3 = im_carll1.getY() + 1.2f;
                if (im_carll1.getY() > 300) {
                    y3 = 20;
                }


                y4 = im_carll2.getY() + 0.85f;
                if (im_carll2.getY() > 280) {
                    y4 = 30;
                }

                switch (flagr) {
                    case 0:
                        y5 = im_carr.getY() - 0.25f;
                        break;
                    case 1:
                        y5 = im_carr.getY() + 0.25f;
                        break;
                }
                if (im_carr.getY() < 110) {
                    flagr = 1;
                } else if (im_carl.getY() > 280) {
                    flagr = 0;
                }


                runOnUiThread(r);//用主线程去修改UI，r在上面具体写出了
            }
        };
        timer.schedule(timerTask, 1000, 50);


    }//onCreate结束


    //本类实现onNavigationItemSelectedListener的接口
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_main) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4);
        } else if (id == R.id.nav_connect) { //显示 隐藏
            if (im_carf.getVisibility() == View.VISIBLE) {
                im_carf.setVisibility(View.INVISIBLE);
                im_carl.setVisibility(View.INVISIBLE);
            } else {
                im_carf.setVisibility(View.VISIBLE);
                im_carl.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.nav_settings) {
            if (im_carf.getVisibility() == View.VISIBLE) {
                im_carf.setVisibility(View.INVISIBLE);
            } else {
                im_carf.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.nav_update) {
            if (im_carl.getVisibility() == View.VISIBLE) {
                im_carl.setVisibility(View.INVISIBLE);
            } else {
                im_carl.setVisibility(View.VISIBLE);
            }
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(MainActivity.this, "当前版本为最新版本", Toast.LENGTH_SHORT).show();
//                }
//            }, 1000);

        } else if (id == R.id.nav_question) {
            if (im_carll1.getVisibility() == View.VISIBLE) {
                im_carll1.setVisibility(View.INVISIBLE);
            } else {
                im_carll1.setVisibility(View.VISIBLE);
            }


        } else if (id == R.id.nav_about) {
//            Toast.makeText(MainActivity.this, "当前版本为0.3", Toast.LENGTH_SHORT).show();
            if (im_carll2.getVisibility() == View.VISIBLE) {
                im_carll2.setVisibility(View.INVISIBLE);
            } else {
                im_carll2.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.a11) {
            if (im_carr.getVisibility() == View.VISIBLE) {
                im_carr.setVisibility(View.INVISIBLE);
            } else {
                im_carr.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.a12) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4_1_1);
        } else if (id == R.id.a13) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4_1_2);
        } else if (id == R.id.a14) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4_1_3);
        } else if (id == R.id.a15) {
            constraintLayout.setBackgroundResource(R.drawable.radar_background4_1_4);

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
        timer.cancel();
        timerTask.cancel();
    }
}
