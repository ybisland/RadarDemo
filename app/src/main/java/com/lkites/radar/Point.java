package com.lkites.radar;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/*
 * Created by 22122 on 18-7-17.
 * Description：接收距离和角度，转化成直角坐标点，
 * 原点 （ 545，400）
 * 在原点时，图像是处于屏幕外显示不出来，所以也不需要设置visibility
 * cos接收的参数是弧度，wifi接收的是角度
 * 角度有正有负
 *
 * 屏幕宽度（像素）：1105
 * 屏幕高度（像素）：352
 * 屏幕密度（0.75 / 1.0 / 1.5）：1.0
 * 屏幕密度dpi（120 / 160 / 240）：160
 * 屏幕宽度（dp）：1105
 * 屏幕高度（dp）：352
 * 但是认为屏幕高度是400dp
 *
 */


public class Point {

    public static final double ORIGIN_X = 545.0;
    public static final double ORIGIN_Y = 400.0;
    public static final double PI = 3.1415926;


    private float x, y;

    Point(int d, int a) {   //cos接收的参数是弧度，wifi接收的是角度
        if (d == 0 && a == 0) {
            x = (float) ORIGIN_X;
            y = (float) ORIGIN_Y;
        } else if (d < 600 && d >= 0 && a > -45 && a < 45) { //确保数据正常，0=<d<600, -45<a<45
            double radian = (a / 180.0) * PI;
            x = (float) (ORIGIN_X + d * sin(radian) - 5);//-5是为了让图像在中间
            y = (float) (ORIGIN_Y - d * cos(radian) * 2);//屏幕高400dp,对应显示200米
        } else {
            x = (float) ORIGIN_X;
            y = (float) ORIGIN_Y;
        }
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

}
